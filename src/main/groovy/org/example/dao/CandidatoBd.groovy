package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.example.usuarios.Candidato;

class CandidatoBd {

    static Scanner teclado = new Scanner(System.in);

    static void listarCandidato() {
        String BUSCAR_TODOS = "SELECT c.id, c.nome, c.sobrenome, c.data_nascimento, c.email, c.cpf, c.cep, p.nome AS nome_pais\n" +
                "FROM candidatos AS c, pais AS p\n" +
                "WHERE c.id_pais = p.id;";

        try {
            Connection conn = Coneccao.conectar();
            PreparedStatement candidatos = conn.prepareStatement(
                    BUSCAR_TODOS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet res = candidatos.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                System.out.println("Listando candidatos ------");
                System.out.println("------------------------");
                while (res.next()) {
                    System.out.println("ID: " + res.getInt(1));
                    System.out.println("Nome: " + res.getString(2));
                    System.out.println("Sobrenome: " + res.getString(3));
                    System.out.println("Data de nascimento: " + res.getDate(4));
                    System.out.println("Email: " + res.getString(5));
                    System.out.println("CPF: " + res.getString(6));
                    System.out.println("CEP: " + res.getString(7));
                    System.out.println("Pais: " + res.getString(8));
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("não existe candidatos cadastrados");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar toda a lista");
            System.exit(-42);
        }
    }

    static void inserirCandidato() {
        List<Candidato> candidatos = new ArrayList<>();

        adicionarCandidatos(candidatos, teclado);

        try {
            Connection conn = Coneccao.conectar();

            for (Candidato candidato : candidatos) {
                int idPais = verificarPais(conn, candidato.getPais());

                if (idPais == -1) {
                    idPais = inserirPais(conn, candidato.getPais());
                }

                int idCandidato = inserirCandidato(conn, candidato, idPais);

                boolean continuar = true;

                while (continuar) {
                    System.out.println("Deseja adicionar competência? (S/N): ");
                    String resposta = teclado.nextLine();

                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.println("Informe o nome da competência: ");
                        String nomeCompetencia = teclado.nextLine();

                        int idCompetencias = verificarCompetencia(conn, nomeCompetencia);

                        if (idCompetencias == -1) {
                            idCompetencias = inserirCompetencia(conn, nomeCompetencia);
                        }

                        inserirHabilidadeCandidato(conn, idCandidato, idCompetencias);

                        System.out.println("Competência adicionada com sucesso!");
                    } else if (resposta.equalsIgnoreCase("N")) {
                        continuar = false;
                    } else {
                        System.out.println("Opção inválida. Digite 'S' para adicionar competência ou 'N' para finalizar.");
                    }
                }

                Coneccao.desconectar(conn);

                System.out.println("O candidato " + candidato.getNome() + " foi inserido com sucesso!");
            }

            Coneccao.desconectar(conn);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao inserir candidato");
            System.exit(-42);
        }
    }

    static int verificarPais(Connection conn, String nomePais) throws SQLException {
        String verificarPaisSQL = "SELECT id FROM pais WHERE nome = ?";
        PreparedStatement verificarPaisStmt = conn.prepareStatement(verificarPaisSQL);
        verificarPaisStmt.setString(1, nomePais);
        ResultSet paisResultSet = verificarPaisStmt.executeQuery();

        if (paisResultSet.next()) {
            return paisResultSet.getInt("id");
        }

        return -1;
    }

    static int inserirPais(Connection conn, String nomePais) throws SQLException {
        String inserirPaisSQL = "INSERT INTO pais (nome) VALUES (?) RETURNING id";
        PreparedStatement inserirPaisStmt = conn.prepareStatement(inserirPaisSQL);
        inserirPaisStmt.setString(1, nomePais);
        ResultSet novoPaisResultSet = inserirPaisStmt.executeQuery();

        if (novoPaisResultSet.next()) {
            return novoPaisResultSet.getInt("id");
        }

        return -1;
    }

    static int inserirCandidato(Connection conn, Candidato candidato, int idPais) throws SQLException {
        String inserirCandidatoSQL = "INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, cep, id_pais) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        PreparedStatement inserirCandidatoStmt = conn.prepareStatement(inserirCandidatoSQL,
        PreparedStatement.RETURN_GENERATED_KEYS);
        inserirCandidatoStmt.setString(1, candidato.getNome());
        inserirCandidatoStmt.setString(2, candidato.getSobrenome());
        inserirCandidatoStmt.setDate(3, new java.sql.Date(candidato.getDataNascimento().getTime()));
        inserirCandidatoStmt.setString(4, candidato.getEmail());
        inserirCandidatoStmt.setLong(5, candidato.getCpf());
        inserirCandidatoStmt.setInt(6, candidato.getCep());
        inserirCandidatoStmt.setInt(7, idPais);

        inserirCandidatoStmt.executeUpdate();

        ResultSet generatedKeys = inserirCandidatoStmt.getGeneratedKeys();
        int idCandidato = -1;
        if (generatedKeys.next()) {
            idCandidato = generatedKeys.getInt(1);
        }

        inserirCandidatoStmt.close();

        return idCandidato;
    }

    static int verificarCompetencia(Connection conn, String nomeCompetencia) throws SQLException {
        String verificarCompetenciaSQL = "SELECT id FROM competencias WHERE nome = ?";
        PreparedStatement verificarCompetenciaStmt = conn.prepareStatement(verificarCompetenciaSQL);
        verificarCompetenciaStmt.setString(1, nomeCompetencia);
        ResultSet competenciaResultSet = verificarCompetenciaStmt.executeQuery();

        if (competenciaResultSet.next()) {
            return competenciaResultSet.getInt("id");
        }

        return -1;
    }

    static int inserirCompetencia(Connection conn, String nomeCompetencia) throws SQLException {
        String inserirCompetenciaSQL = "INSERT INTO competencias (nome) VALUES (?) RETURNING id";
        PreparedStatement inserirCompetenciaStmt = conn.prepareStatement(inserirCompetenciaSQL);
        inserirCompetenciaStmt.setString(1, nomeCompetencia);
        ResultSet novaCompetenciaResultSet = inserirCompetenciaStmt.executeQuery();

        if (novaCompetenciaResultSet.next()) {
            return novaCompetenciaResultSet.getInt("id");
        }

        return -1;
    }

    static void inserirHabilidadeCandidato(Connection conn, int idCandidato, int idCompetencia) throws SQLException {
        String inserirHabilidadeSQL = "INSERT INTO usuario_skills (id_candidatos, id_competencias) VALUES (?, ?)";
        PreparedStatement inserirHabilidadeStmt = conn.prepareStatement(inserirHabilidadeSQL);
        inserirHabilidadeStmt.setInt(1, idCandidato);
        inserirHabilidadeStmt.setInt(2, idCompetencia);

        inserirHabilidadeStmt.executeUpdate();
        inserirHabilidadeStmt.close();
    }

    static void deletarCandidato() {

        String BUSCAR_POR_NOME = "SELECT id FROM candidatos WHERE nome=?";
        String DELETAR_CANDIDATO = "DELETE FROM candidatos WHERE id=?";
        String DELETAR_COMPETENCIAS = "DELETE FROM usuario_skills WHERE id_candidatos=?";

        System.out.println("Informe o nome do candidato: ");
        String nomeCandidato = teclado.nextLine();

        try {
            Connection conn = Coneccao.conectar();
            PreparedStatement buscaCandidato = conn.prepareStatement(
                    BUSCAR_POR_NOME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            buscaCandidato.setString(1, nomeCandidato);
            ResultSet res = buscaCandidato.executeQuery();

            if (res.next()) {
                int idCandidato = res.getInt("id");

                PreparedStatement deletarCompetencias = conn.prepareStatement(DELETAR_COMPETENCIAS);
                deletarCompetencias.setInt(1, idCandidato);
                deletarCompetencias.executeUpdate();
                deletarCompetencias.close();

                PreparedStatement deletarCandidato = conn.prepareStatement(DELETAR_CANDIDATO);
                deletarCandidato.setInt(1, idCandidato);
                deletarCandidato.executeUpdate();
                deletarCandidato.close();

                Coneccao.desconectar(conn);
                System.out.println("Candidato e suas competências foram deletados com sucesso!!");
            } else {
                System.out.println("Não existe candidato com o nome informado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Não foi possível deletar o candidato e suas competências");
            System.exit(-42);
        }
    }


    static void adicionarCandidatos(List<Candidato> candidatos, Scanner scanner) {
        System.out.println("Quantos candidatos deseja adicionar?");
        int quantidade = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < quantidade; i++) {
            System.out.println("Candidato " + (i + 1) + ":");
            Candidato.adicionarCandidato(candidatos, scanner);
        }
    }
}

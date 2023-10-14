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

        System.out.println("Adicionar Candidato:");
        Candidato.adicionarCandidato(candidatos, teclado);

        try {
            Connection conn = Coneccao.conectar();

            for (Candidato candidato : candidatos) {
                int idPais = Pais.verificarEInserir(conn, candidato.getPais(), "pais");
                int idCandidato = inserirCandidato(conn, candidato, idPais);

                boolean continuar = true;

                while (continuar) {
                    continuar = Competencias.adicionarCompetencia(teclado, conn, idCandidato, "candidato");
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

    static deletarCandidato() {

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
                return true;
            } else {
                System.out.println("Não existe candidato com o nome informado.");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Não foi possível deletar o candidato e suas competências");
            System.exit(-42);
            return false;
        }
    }

}

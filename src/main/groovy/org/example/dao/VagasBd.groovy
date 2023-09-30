package org.example.dao

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class VagasBd {

    static Scanner teclado = new Scanner(System.in);

    static void listarVagas() {
        String BUSCAR_TODOS = "SELECT v.id, v.nome, v.descricao, v.salario, e.nome AS nome\n" +
                "FROM vagas AS v, empresas AS e\n" +
                "WHERE v.id_empresa = e.id;";

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
                System.out.println("Listando vagas ------");
                System.out.println("------------------------");
                while (res.next()) {
                    System.out.println("ID: " + res.getInt(1));
                    System.out.println("Nome: " + res.getString(2));
                    System.out.println("Descrição: " + res.getString(3));
                    System.out.println("Salário: " + res.getDouble(4));
                    System.out.println("Empresa: " + res.getString(5));
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("não existe vagas cadastradas");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar toda a lista");
            System.exit(-42);
        }
    }

    static void inserirVaga() {

        System.out.println("Informe o nome da vaga: ");
        String nomeVaga = teclado.nextLine();

        System.out.println("Informe a descrição da vaga: ");
        String descricao = teclado.nextLine();

        System.out.println("Informe o salário: ");
        double salario = teclado.nextDouble();
        teclado.nextLine();

        System.out.println("Informe nome da empresa: ");
        String nomeEmpresa = teclado.nextLine();


        String consultarIdEmpresa = 'SELECT id FROM empresas WHERE nome = ?'
        int idEmpresa = consultarIdEmpresa(nomeEmpresa);

        if (idEmpresa != -1) {
            String INSERIR_VAGA = "INSERT INTO vagas (nome, descricao, salario, id_empresa) VALUES (?, ?, ?, ?) RETURNING id";

            try {
                Connection conn = Coneccao.conectar();
                PreparedStatement salvarVaga = conn.prepareStatement(INSERIR_VAGA, PreparedStatement.RETURN_GENERATED_KEYS);

                salvarVaga.setString(1, nomeVaga);
                salvarVaga.setString(2, descricao);
                salvarVaga.setDouble(3, salario);
                salvarVaga.setInt(4, idEmpresa);

                salvarVaga.executeUpdate();

                ResultSet generatedKeys = salvarVaga.getGeneratedKeys();
                int idVaga = -1;
                if (generatedKeys.next()) {
                    idVaga = generatedKeys.getInt(1);
                }

                salvarVaga.close();

                boolean continuar = true;

                while (continuar) {
                    System.out.println("Deseja adicionar competência à vaga? (S/N): ");
                    String resposta = teclado.nextLine();

                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.println("Informe o nome da competência: ");
                        String nomeCompetencia = teclado.nextLine();

                        int idCompetencia = verificarCompetencia(conn, nomeCompetencia);

                        if (idCompetencia == -1) {
                            idCompetencia = inserirCompetencia(conn, nomeCompetencia);
                        }

                        inserirHabilidadeVaga(conn, idVaga, idCompetencia);

                        System.out.println("Competência adicionada à vaga com sucesso!");
                    } else if (resposta.equalsIgnoreCase("N")) {
                        continuar = false;
                    } else {
                        System.out.println("Opção inválida. Digite 'S' para adicionar competência ou 'N' para finalizar.");
                    }
                }

                Coneccao.desconectar(conn);
                System.out.println("A vaga " + nomeVaga + " foi inserida com sucesso!");

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erro ao inserir vaga");
                System.exit(-42);
            }
        } else {
            System.err.println("Empresa não encontrada. Certifique-se de que o nome da empresa está correto.");
        }
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

    static void inserirHabilidadeVaga(Connection conn, int idVaga, int idCompetencia) throws SQLException {
        String inserirHabilidadeSQL = "INSERT INTO vagas_skills (id_vaga, id_competencia) VALUES (?, ?)";
        PreparedStatement inserirHabilidadeStmt = conn.prepareStatement(inserirHabilidadeSQL);
        inserirHabilidadeStmt.setInt(1, idVaga);
        inserirHabilidadeStmt.setInt(2, idCompetencia);

        inserirHabilidadeStmt.executeUpdate();
        inserirHabilidadeStmt.close();
    }


    static void deletarVaga() {

        String BUSCAR_POR_NOME_VAGA = "SELECT id FROM vagas WHERE nome=?";
        String DELETAR_VAGA = "DELETE FROM vagas WHERE id=?";
        String DELETAR_COMPETENCIAS_VAGA = "DELETE FROM vagas_skills WHERE id_vagas=?";

        System.out.println("Informe o nome da vaga: ");
        String nomeVaga = teclado.nextLine();

        try {
            Connection conn = Coneccao.conectar();
            PreparedStatement buscaVaga = conn.prepareStatement(
                    BUSCAR_POR_NOME_VAGA,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            buscaVaga.setString(1, nomeVaga);
            ResultSet res = buscaVaga.executeQuery();

            if (res.next()) {
                int idVaga = res.getInt("id");

                PreparedStatement deletarCompetenciasVaga = conn.prepareStatement(DELETAR_COMPETENCIAS_VAGA);
                deletarCompetenciasVaga.setInt(1, idVaga);
                deletarCompetenciasVaga.executeUpdate();
                deletarCompetenciasVaga.close();

                PreparedStatement deletarVaga = conn.prepareStatement(DELETAR_VAGA);
                deletarVaga.setInt(1, idVaga);
                deletarVaga.executeUpdate();
                deletarVaga.close();

                Coneccao.desconectar(conn);
                System.out.println("Vaga e suas competências foram deletadas com sucesso!!");
            } else {
                System.out.println("Não existe vaga com o nome informado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Não foi possível deletar a vaga e suas competências");
            System.exit(-42);
        }
    }

}

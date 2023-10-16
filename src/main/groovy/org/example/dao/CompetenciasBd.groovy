package org.example.dao

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CompetenciasBd {

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

    static void inserirHabilidadeEmpresa(Connection conn, int idEmpresa, int idCompetencia) throws SQLException {
        String inserirHabilidadeSQL = "INSERT INTO empresa_skills (id_empresa, id_competencias) VALUES (?, ?)";
        PreparedStatement inserirHabilidadeStmt = conn.prepareStatement(inserirHabilidadeSQL);
        inserirHabilidadeStmt.setInt(1, idEmpresa);
        inserirHabilidadeStmt.setInt(2, idCompetencia);

        inserirHabilidadeStmt.executeUpdate();
        inserirHabilidadeStmt.close();
    }

    static void inserirHabilidadeVaga(Connection conn, int idVaga, int idCompetencia) throws SQLException {
        String inserirHabilidadeSQL = "INSERT INTO vagas_skills (id_vagas, id_competencias) VALUES (?, ?)";
        PreparedStatement inserirHabilidadeStmt = conn.prepareStatement(inserirHabilidadeSQL);
        inserirHabilidadeStmt.setInt(1, idVaga);
        inserirHabilidadeStmt.setInt(2, idCompetencia);

        inserirHabilidadeStmt.executeUpdate();
        inserirHabilidadeStmt.close();
    }

    static boolean adicionarCompetencia(Scanner teclado, Connection conn, int id, String tipo) {
        System.out.println("Deseja adicionar competência? (S/N): ");
        String resposta = teclado.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            System.out.println("Informe o nome da competência: ");
            String nomeCompetencia = teclado.nextLine();

            int idCompetencia = -1;

            if ("candidato".equals(tipo)) {
                idCompetencia = verificarCompetencia(conn, nomeCompetencia);
                if (idCompetencia == -1) {
                    idCompetencia = inserirCompetencia(conn, nomeCompetencia);
                }
                inserirHabilidadeCandidato(conn, id, idCompetencia);
            } else if ("empresa".equals(tipo)) {
                idCompetencia = verificarCompetencia(conn, nomeCompetencia);
                if (idCompetencia == -1) {
                    idCompetencia = inserirCompetencia(conn, nomeCompetencia);
                }
                inserirHabilidadeEmpresa(conn, id, idCompetencia);
            } else if ("vaga".equals(tipo)) {
                idCompetencia = verificarCompetencia(conn, nomeCompetencia);
                if (idCompetencia == -1) {
                    idCompetencia = inserirCompetencia(conn, nomeCompetencia);
                }
                inserirHabilidadeVaga(conn, id, idCompetencia);
            } else {
                System.out.println("Tipo inválido. Digite um nome válido para adicionar competência.");
            }

            System.out.println("Competência adicionada com sucesso!");
            return true;
        } else if (resposta.equalsIgnoreCase("N")) {
            return false;
        } else {
            System.out.println("Opção inválida. Digite 'S' para adicionar competência ou 'N' para finalizar.");
            return adicionarCompetencia(teclado, conn, id, tipo);
        }
    }



}

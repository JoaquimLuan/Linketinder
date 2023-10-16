package org.example.dao

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class OperacoesBd {

    static PreparedStatement criarPreparedStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    static void deletaCandidato(Connection conn, String sqlQuery, int idCandidato) throws SQLException {
        try (PreparedStatement deletarCandidato = conn.prepareStatement(sqlQuery)) {
            deletarCandidato.setInt(1, idCandidato);
            deletarCandidato.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao executar a consulta de exclusão de candidato");
            throw e;
        }
    }

    static void deletaEmpresa(Connection conn, String sqlQuery, int idEmpresa) throws SQLException {
        try (PreparedStatement deletarEmpresa = conn.prepareStatement(sqlQuery)) {
            deletarEmpresa.setInt(1, idEmpresa);
            deletarEmpresa.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao executar a consulta de exclusão de empresa");
            throw e;
        }
    }

    static void deletaVaga(Connection conn, String sqlQuery, int idVaga) throws SQLException {
        try (PreparedStatement deletarVaga = conn.prepareStatement(sqlQuery)) {
            deletarVaga.setInt(1, idVaga);
            deletarVaga.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao executar a consulta de exclusão de vaga");
            throw e;
        }
    }
}

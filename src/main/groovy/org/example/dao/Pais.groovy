package org.example.dao

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class Pais {

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

    static int vinculaPais(Connection conn, String nome, String tipo) {

        int id = -1;

        if ("pais".equals(tipo)) {
            id = verificarPais(conn, nome);
            if (id == -1) {
                id = inserirPais(conn, nome);
            }
        } else if ("empresa".equals(tipo)) {
            id = EmpresaBd.inserirEmpresa(conn, nome);
        } else if ("candidato".equals(tipo)) {
            id = CandidatoBd.inserirCandidato(conn, nome);
        } else {
            System.out.println("Tipo inválido. Escolha um tipo valido.");
        }

        return id;
    }

}

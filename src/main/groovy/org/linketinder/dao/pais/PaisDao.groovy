package org.linketinder.dao.pais

import org.linketinder.dao.candidato.CandidatoDao
import org.linketinder.dao.empresa.EmpresaDao
import org.linketinder.db.factory.PostgreConeccaoDb

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class PaisDao {

    EmpresaDao empresaBd = new EmpresaDao()
    CandidatoDao candidatoBd = new CandidatoDao()

    static PaisDao instance
    Connection conn

    private PaisDao() {
        conn = PostgreConeccaoDb.getInstance().conectar()
    }

    static PaisDao getInstance() {
        if (instance == null) {
            instance = new PaisDao()
        }
        return instance
    }

    static void close() {
        if (instance != null && instance.conn != null) {
            PostgreConeccaoDb.getInstance().desconectar(instance.conn)
            instance = null
        }
    }

    int verificarPais(String nomePais) throws SQLException {
        String verificarPaisSQL = "SELECT id FROM pais WHERE nome = ?"
        PreparedStatement verificarPaisStmt = conn.prepareStatement(verificarPaisSQL)
        verificarPaisStmt.setString(1, nomePais)
        ResultSet paisResultSet = verificarPaisStmt.executeQuery()

        if (paisResultSet.next()) {
            return paisResultSet.getInt("id")
        } else {
            return -1
        }
    }

    int inserirPais(String nomePais) throws SQLException {
        String inserirPaisSQL = "INSERT INTO pais (nome) VALUES (?) RETURNING id"
        PreparedStatement inserirPaisStmt = conn.prepareStatement(inserirPaisSQL)
        inserirPaisStmt.setString(1, nomePais)
        ResultSet novoPaisResultSet = inserirPaisStmt.executeQuery()

        if (novoPaisResultSet.next()) {
            return novoPaisResultSet.getInt("id")
        } else {
            return -1
        }
    }

    int vinculaPais(String nome, String tipo) {
        int id = -1
        if ("pais".equals(tipo)) {
            id = verificarPais(nome)
            if (id == -1) {
                id = inserirPais(nome)
            }
        } else if ("empresa".equals(tipo)) {
            id = empresaBd.inserirEmpresa(nome)
        } else if ("candidato".equals(tipo)) {
            id = candidatoBd.inserirCandidato(nome)
        }
        return id
    }
}

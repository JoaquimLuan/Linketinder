package org.linketinder.dao.competencia

import org.linketinder.db.factory.PostgreConeccaoDb

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CompetenciasDao {

    static CompetenciasDao instance
    Connection conn

    private CompetenciasDao() {
        conn = PostgreConeccaoDb.getInstance().conectar()
    }

    static CompetenciasDao getInstance() {
        if (instance == null) {
            instance = new CompetenciasDao()
        }
        return instance
    }

    static void close() {
        if (instance != null && instance.conn != null) {
            PostgreConeccaoDb.getInstance().desconectar(instance.conn)
            instance = null
        }
    }

    int verificarCompetencia(String nomeCompetencia) throws SQLException {
        try {
            String verificarCompetenciaSQL = "SELECT id FROM competencias WHERE nome = ?"
            PreparedStatement verificarCompetenciaStmt = conn.prepareStatement(verificarCompetenciaSQL)
            verificarCompetenciaStmt.setString(1, nomeCompetencia)
            ResultSet competenciaResultSet = verificarCompetenciaStmt.executeQuery()

            if (competenciaResultSet.next()) {
                return competenciaResultSet.getInt("id")
            }
            return -1
        } finally {
        }
    }

    int inserirCompetencia(String nomeCompetencia) throws SQLException {
        try {
            String inserirCompetenciaSQL = "INSERT INTO competencias (nome) VALUES (?) RETURNING id"
            PreparedStatement inserirCompetenciaStmt = conn.prepareStatement(inserirCompetenciaSQL)
            inserirCompetenciaStmt.setString(1, nomeCompetencia)
            ResultSet novaCompetenciaResultSet = inserirCompetenciaStmt.executeQuery()

            if (novaCompetenciaResultSet.next()) {
                return novaCompetenciaResultSet.getInt("id")
            }
            return -1
        } finally {
        }
    }

    void inserirHabilidadeCandidato(int idCandidato, int idCompetencia) throws SQLException {
        try {
            String inserirHabilidadeSQL = "INSERT INTO usuario_skills (id_candidatos, id_competencias) VALUES (?, ?)"
            PreparedStatement inserirHabilidadeStmt = conn.prepareStatement(inserirHabilidadeSQL)
            inserirHabilidadeStmt.setInt(1, idCandidato)
            inserirHabilidadeStmt.setInt(2, idCompetencia)
            inserirHabilidadeStmt.executeUpdate()
        } finally {
        }
    }

    void inserirHabilidadeEmpresa(int idEmpresa, int idCompetencia) throws SQLException {
        try {
            String inserirHabilidadeSQL = "INSERT INTO empresa_skills (id_empresa, id_competencias) VALUES (?, ?)"
            PreparedStatement inserirHabilidadeStmt = conn.prepareStatement(inserirHabilidadeSQL)
            inserirHabilidadeStmt.setInt(1, idEmpresa)
            inserirHabilidadeStmt.setInt(2, idCompetencia)
            inserirHabilidadeStmt.executeUpdate()
        } finally {
        }
    }

    void inserirHabilidadeVaga(int idVaga, int idCompetencia) throws SQLException {
        try {
            String inserirHabilidadeSQL = "INSERT INTO vagas_skills (id_vagas, id_competencias) VALUES (?, ?)"
            PreparedStatement inserirHabilidadeStmt = conn.prepareStatement(inserirHabilidadeSQL)
            inserirHabilidadeStmt.setInt(1, idVaga)
            inserirHabilidadeStmt.setInt(2, idCompetencia)
            inserirHabilidadeStmt.executeUpdate()
        } finally {
        }
    }
}

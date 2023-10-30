package org.linketinder.dao.candidato

import org.linketinder.Model.CandidatoModel
import org.linketinder.db.factory.PostgreConeccaoDb
import org.linketinder.db.factory.PostgreConectFactory

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CandidatoDao implements ICandidatoDao{

    @Override
    List listarCandidato() {

        String BUSCAR_TODOS = "SELECT c.id, c.nome, c.sobrenome, c.data_nascimento, c.email, c.cpf, c.cep, p.nome AS nome_pais\n" +
                "FROM candidatos AS c, pais AS p\n" +
                "WHERE c.id_pais = p.id;"
        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            PreparedStatement candidatos = new PostgreConectFactory().criarPreparedStatement(conn, BUSCAR_TODOS)
            ResultSet res = candidatos.executeQuery()

            res.last()
            res.beforeFirst()

            List listCandidatos = []
            while (res.next()) {
                LinkedHashMap candidato = [:]
                candidato.id = res.getInt(1)
                candidato.nome = res.getString(2)
                candidato.sobrenome = res.getString(3)
                candidato.dataNascimento = res.getDate(4)
                candidato.email = res.getString(5)
                candidato.cpf = res.getString(6)
                candidato.cep = res.getString(7)
                candidato.pais = res.getString(8)
                listCandidatos << candidato
            }
            PostgreConeccaoDb.getInstance().desconectar(conn)
            return listCandidatos

        } catch (Exception e) {
            e.printStackTrace()
            throw new Exception("Erro ao listar Candidatos, contate o suporte!")
        }
    }

    @Override
    int inserirCandidato(CandidatoModel candidato, int idPais) throws SQLException {

        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            String inserirCandidatoSQL = "INSERT INTO candidatos (nome, sobrenome, data_nascimento, email, cpf, cep, id_pais) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id"
            PreparedStatement inserirCandidatoStmt = conn.prepareStatement(inserirCandidatoSQL,PreparedStatement.RETURN_GENERATED_KEYS)
            inserirCandidatoStmt.setString(1, candidato.getNome())
            inserirCandidatoStmt.setString(2, candidato.getSobrenome())
            inserirCandidatoStmt.setDate(3, new java.sql.Date(candidato.getDataNascimento().getTime()))
            inserirCandidatoStmt.setString(4, candidato.getEmail())
            inserirCandidatoStmt.setString(5, candidato.getCpf())
            inserirCandidatoStmt.setString(6, candidato.getCep())
            inserirCandidatoStmt.setInt(7, idPais)
            inserirCandidatoStmt.executeUpdate()

            ResultSet generatedKeys = inserirCandidatoStmt.getGeneratedKeys()
            int idCandidato = -1
            if (generatedKeys.next()) {
                idCandidato = generatedKeys.getInt(1)
            }

            inserirCandidatoStmt.close()
            PostgreConeccaoDb.getInstance().desconectar(conn)
            return idCandidato
        } catch (Exception e) {
            e.printStackTrace()
            throw new Exception("Erro ao inserir Candidato!!!")
        }
    }

    @Override
    boolean deletarCandidato(String nomeCandidato) {
        String BUSCAR_POR_NOME = "SELECT id FROM candidatos WHERE nome=?"
        String DELETAR_CANDIDATO = "DELETE FROM candidatos WHERE nome=?"

        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()) {
            PreparedStatement buscaCandidato = new PostgreConectFactory().criarPreparedStatement(conn, BUSCAR_POR_NOME)
            buscaCandidato.setString(1, nomeCandidato)
            ResultSet res = buscaCandidato.executeQuery()

            if (res.next()) {
                PreparedStatement deletarCandidatoStmt = new PostgreConectFactory().criarPreparedStatement(conn, DELETAR_CANDIDATO)
                deletarCandidatoStmt.setString(1, nomeCandidato)
                deletarCandidatoStmt.executeUpdate()
                PostgreConeccaoDb.getInstance().desconectar(conn)
                return true
            } else {
                return false
            }
        } catch (Exception e) {
            e.printStackTrace()
            throw new Exception("Erro ao deletar Candidato!!!")
        }
    }

}

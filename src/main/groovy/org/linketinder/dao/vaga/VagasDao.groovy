package org.linketinder.dao.vaga

import org.linketinder.Model.VagaModel
import org.linketinder.db.factory.PostgreConeccaoDb
import org.linketinder.db.factory.PostgreConectFactory

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class VagasDao implements IVagaDao{


    @Override
    List listarVagas() {
        String BUSCAR_TODOS = "SELECT v.id, v.nome, v.descricao, v.salario, e.nome AS nome\n" +
                "FROM vagas AS v, empresas AS e\n" +
                "WHERE v.id_empresa = e.id;"

        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            PreparedStatement vagas = new PostgreConectFactory().criarPreparedStatement(conn, BUSCAR_TODOS)
            ResultSet res = vagas.executeQuery()

            res.last()
            res.beforeFirst()

            List listVagas = []
            while (res.next()) {
                LinkedHashMap vaga = [:]
                vaga.id = res.getInt(1)
                vaga.nome = res.getString(2)
                vaga.descricao = res.getString(3)
                vaga.salario = res.getString(4)
                vaga.empresa = res.getString(5)
                listVagas << vaga
            }

            PostgreConeccaoDb.getInstance().desconectar(conn)
            return listVagas

        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    @Override
    int inserirVaga(VagaModel vaga) throws SQLException {
        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            String inserirVagaSQL = "INSERT INTO vagas (nome, descricao, salario, id_empresa) VALUES (?, ?, ?, ?) RETURNING id"
            PreparedStatement inserirVagaStmt = new PostgreConectFactory().criarPreparedStatement(conn, inserirVagaSQL)
            inserirVagaStmt.setString(1, vaga.getNome())
            inserirVagaStmt.setString(2, vaga.getDescricao())
            inserirVagaStmt.setDouble(3, vaga.getSalario())
            inserirVagaStmt.setInt(4, consultarIdEmpresa(vaga.getEmpresa()))

            boolean isResultSet = inserirVagaStmt.execute()

            if (isResultSet) {
                ResultSet generatedKeys = inserirVagaStmt.getResultSet()
                int idVaga = -1
                if (generatedKeys.next()) {
                    idVaga = generatedKeys.getInt(1)
                }
                inserirVagaStmt.close()
                PostgreConeccaoDb.getInstance().desconectar(conn)
                return idVaga
            } else {
                inserirVagaStmt.close()
                PostgreConeccaoDb.getInstance().desconectar(conn)
                return -1
            }
        } catch (Exception e) {
            e.printStackTrace()
            return -1
        }
    }


    @Override
    boolean deletarVaga(String nomeVaga) {
        String BUSCAR_POR_NOME_VAGA = "SELECT id FROM vagas WHERE nome=?"
        String DELETAR_VAGA = "DELETE FROM vagas WHERE nome=?"

        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            PreparedStatement buscaVaga = new PostgreConectFactory().criarPreparedStatement(conn, BUSCAR_POR_NOME_VAGA)
            buscaVaga.setString(1, nomeVaga)
            ResultSet res = buscaVaga.executeQuery()

            if (res.next()) {
                PreparedStatement deletarVaga = new PostgreConectFactory().criarPreparedStatement(conn, DELETAR_VAGA)
                deletarVaga.setString(1, nomeVaga)

                int rowsDeleted = deletarVaga.executeUpdate()

                if (rowsDeleted > 0) {
                    PostgreConeccaoDb.getInstance().desconectar(conn)
                    return true
                } else {
                    PostgreConeccaoDb.getInstance().desconectar(conn)
                    throw ("Não houve linhas afetadas, a exclusão falhou")
                }
            } else {
                PostgreConeccaoDb.getInstance().desconectar(conn)
                throw ("A vaga não foi encontrada")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return e
        }
    }

    static int consultarIdEmpresa(String nomeEmpresa) {

        String CONSULTAR_ID_EMPRESA = "SELECT id FROM empresas WHERE nome = ?"

        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            PreparedStatement consulta = new PostgreConectFactory().criarPreparedStatement(conn, CONSULTAR_ID_EMPRESA)
            consulta.setString(1, nomeEmpresa)
            ResultSet resultado = consulta.executeQuery()
            PostgreConeccaoDb.getInstance().desconectar(conn)

            if (resultado.next()) {
                return resultado.getInt("id")
            } else {
                return -1
            }
        } catch (Exception e) {
            e.printStackTrace()
            return -1
        }
    }
}

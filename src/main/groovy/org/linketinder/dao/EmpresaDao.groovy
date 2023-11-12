package org.linketinder.dao

import org.linketinder.Model.EmpresaModel
import org.linketinder.db.factory.PostgreConectFactory
import org.linketinder.db.factory.PostgreConeccaoDb

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException

class EmpresaDao implements IUsuarioDao<EmpresaModel>{

    private String BUSCAR_TODOS = "SELECT e.id, e.nome, e.cnpj, e.email, e.cep, p.nome AS nome_pais FROM empresas AS e, pais AS p WHERE e.id_pais = p.id;"
    private String BUSCAR_POR_NOME = "SELECT id FROM empresas WHERE nome=?"
    private String DELETAR_EMPRESA = "DELETE FROM empresas WHERE nome=?"

    @Override
    List listar() {

        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            PreparedStatement empresas = new PostgreConectFactory().criarPreparedStatement(conn, BUSCAR_TODOS)
            ResultSet res = empresas.executeQuery()

            res.last()
            res.beforeFirst()

            List listEmpresas = []
            while (res.next()) {
                LinkedHashMap empresa = [:]
                empresa.id = res.getInt(1)
                empresa.nome = res.getString(2)
                empresa.cnpj = res.getString(3)
                empresa.email = res.getString(4)
                empresa.cep = res.getString(5)
                empresa.pais = res.getString(6)
                listEmpresas << empresa
            }
            PostgreConeccaoDb.getInstance().desconectar(conn)
            return listEmpresas

        } catch (Exception e) {
            e.printStackTrace()
            throw new Exception("Erro ao listar Empresas, contate o suporte!")
        }
    }

    @Override
    int inserir(EmpresaModel empresa) throws SQLException {
        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            String inserirEmpresaSQL = "INSERT INTO empresas (nome, cnpj, email, cep, id_pais) VALUES (?, ?, ?, ?, ?) RETURNING id"
            PreparedStatement inserirEmpresaStmt = conn.prepareStatement(inserirEmpresaSQL, PreparedStatement.RETURN_GENERATED_KEYS)
            inserirEmpresaStmt.setString(1, empresa.getNome())
            inserirEmpresaStmt.setString(2, empresa.getCnpj())
            inserirEmpresaStmt.setString(3, empresa.getEmail())
            inserirEmpresaStmt.setString(4, empresa.getCep())
            inserirEmpresaStmt.setInt(5, empresa.getPais())

            inserirEmpresaStmt.executeUpdate()
            ResultSet generatedKeys = inserirEmpresaStmt.getGeneratedKeys()

            int idEmpresa = -1
            if (generatedKeys.next()) {
                idEmpresa = generatedKeys.getInt(1)
            }
            inserirEmpresaStmt.close()
            PostgreConeccaoDb.getInstance().desconectar(conn)

            return idEmpresa
        }catch (Exception e) {
            e.printStackTrace()
            return e
        }
    }

    @Override
    boolean deletar(String nomeEmpresa) {

        try (Connection conn = PostgreConeccaoDb.getInstance().conectar()){
            PreparedStatement buscaEmpresa = new PostgreConectFactory().criarPreparedStatement(conn, BUSCAR_POR_NOME)
            buscaEmpresa.setString(1, nomeEmpresa)
            ResultSet res = buscaEmpresa.executeQuery()

            if (res.next()) {
                PreparedStatement deletarEmpresa = new PostgreConectFactory().criarPreparedStatement(conn, DELETAR_EMPRESA)
                deletarEmpresa.setString(1, nomeEmpresa)
                int rowsDeleted = deletarEmpresa.executeUpdate()

                if (rowsDeleted > 0) {
                    PostgreConeccaoDb.getInstance().desconectar(conn)
                    return true
                } else {
                    PostgreConeccaoDb.getInstance().desconectar(conn)
                    throw ("Não houve linhas afetadas, a exclusão falhou")
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
            return e
        }
    }
}

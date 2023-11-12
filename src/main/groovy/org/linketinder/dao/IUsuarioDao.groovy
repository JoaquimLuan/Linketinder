package org.linketinder.dao

import java.sql.SQLException

interface IUsuarioDao<T> {
    List listar()
    int inserir(T objeto) throws SQLException
    boolean deletar(String nomeObjeto)
}
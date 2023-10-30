package org.linketinder.db.factory

import org.linketinder.db.IDataBaseConect

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

interface IDataBaseConectFactory {
    IDataBaseConect createConnection()
    PreparedStatement criarPreparedStatement(Connection conn, String sql) throws SQLException
}

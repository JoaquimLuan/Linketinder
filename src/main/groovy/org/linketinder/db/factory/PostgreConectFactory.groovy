package org.linketinder.db.factory

import org.linketinder.db.IDataBaseConect

import java.sql.ResultSet
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class PostgreConectFactory implements IDataBaseConectFactory{

    @Override
    IDataBaseConect createConnection() {
        return new PostgreConeccaoDb()
    }

    @Override
    PreparedStatement criarPreparedStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
    }

}

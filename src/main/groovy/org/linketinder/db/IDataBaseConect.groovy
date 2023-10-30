package org.linketinder.db

import java.sql.Connection
import java.sql.SQLException

interface IDataBaseConect {

    Connection conectar() throws SQLException;
    void desconectar(Connection conn) throws SQLException;

}
package org.linketinder.db.factory

import org.linketinder.db.IDataBaseConect

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class PostgreConeccaoDb implements IDataBaseConect{

    private static PostgreConeccaoDb instance;

    static PostgreConeccaoDb getInstance() {
        if (instance == null) {
            instance = new PostgreConeccaoDb();
        }
        return instance;
    }

    Connection conectar() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "joaquimborges");
        props.setProperty("password", "joaquim");
        props.setProperty("ssl", "false");
        String URL_SERVIDOR = "jdbc:postgresql://localhost:5432/linketinder";

        try {
            return DriverManager.getConnection(URL_SERVIDOR, props);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ClassNotFoundException) {
                throw new Exception("Verifique o driver de conexão");
            } else {
                throw new Exception("Verifique se o servidor está ativo");
            }
        }
    }

    void desconectar(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
}


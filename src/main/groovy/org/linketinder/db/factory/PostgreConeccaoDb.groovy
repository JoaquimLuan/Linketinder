package org.linketinder.db.factory

import org.linketinder.db.IDataBaseConect

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class PostgreConeccaoDb implements IDataBaseConect{

    private static PostgreConeccaoDb instance;
    private static final String url = "jdbc:postgresql://localhost:5432/linketinder"
    private static final String user = "joaquimborges"
    private static final String passWord = "joaquim"


    static PostgreConeccaoDb getInstance() {
        if (instance == null) {
            instance = new PostgreConeccaoDb();
        }
        return instance;
    }

    Connection conectar() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver")
            return DriverManager.getConnection(url, user, passWord);
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


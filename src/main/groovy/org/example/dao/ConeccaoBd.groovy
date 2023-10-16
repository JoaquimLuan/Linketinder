package org.example.dao

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException


class ConeccaoBd {

    static Connection conectar() {
        Properties props = new Properties();
        props.setProperty("user", "joaquimborges");
        props.setProperty("password", "joaquim");
        props.setProperty("ssl", "false");
        String URL_SERVIDOR = "jdbc:postgresql://localhost:5432/linketinder";

        try {
            return DriverManager.getConnection(URL_SERVIDOR, props);

        }catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ClassNotFoundException) {
                System.err.println("Verifique o drive de conecção");
            } else {
                System.out.println("Verifique se o servidor esta ativo");
            }
            System.exit(-42);
            return null;
        }
    }

    static void desconectar(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

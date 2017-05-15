package com.kostkowski.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Grzegorz Kostkowski on 2017-03-11.
 */
public class DbConnector {

    private DbConnector() {}

    private static Properties connectionProps = new Properties();
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/";
    private static final String user ="poadmin";
    private static final String password ="haslo";

    public static Connection getConnection() throws SQLException {
        if (connection == null)
            setConnection(user, password);
        return connection;
    }
    public static Connection getConnection(String user, String password) throws SQLException {
        if (connection == null)
            setConnection(user, password);
        return connection;
    }

    private static void setConnection(String user, String password) throws SQLException {
        connectionProps.put("user", user);
        connectionProps.put("password", password);
        connection = DriverManager.getConnection(url, connectionProps);
    }

    public static void closeConnection() throws SQLException {
        if(connection != null && !connection.isClosed())
            connection.close();
        connection = null;
    }
}

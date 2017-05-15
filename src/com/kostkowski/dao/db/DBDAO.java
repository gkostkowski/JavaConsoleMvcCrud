package com.kostkowski.dao.db;


import com.kostkowski.dao.db.DbConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Grzegorz Kostkowski on 2017-03-10.
 */
public abstract class DBDAO {

    private static final String CREATE_TBL_STMT = "CREATE TABLE IF NOT EXISTS ";
    private static final String CREATE_TBL_SHELF = "Shelves(" +
            "shelf_id SMALLINT," +
            "capacity SMALLINT ," +
            "maxCapacity SMALLINT," +
            "category VARCHAR(40)," +
            "type character default 'h'," +
            "CONSTRAINT pk_shelf PRIMARY KEY (shelf_id) );  ";
    private static final String CREATE_TBL_PROD= "PRODUCTS(" +
            "prod_id SMALLINT, " +
            "shelf_id SMALLINT NOT NULL, " +
            "price NUMERIC(5, 2)," +
            "name VARCHAR(40)," +
            "CONSTRAINT pk_prod PRIMARY KEY (prod_id, shelf_id),"+
            "CONSTRAINT fk_prod_shelf FOREIGN KEY (shelf_id) REFERENCES SHELVES(shelf_id) );  ";
    protected static final String ESCAPE_CLAUSE =" ESCAPE '!'";

    protected Connection connection;
    private static final String dbName = "shop";
    private static final String CHOOSE_DB = "USE " + dbName + ";";


    public DBDAO(String user, String password) throws SQLException {
        connection = DbConnector.getConnection(user,password);
        initializeDb();
    }
    public DBDAO() throws SQLException {
        connection = DbConnector.getConnection();
        initializeDb();
    }

    private void initializeDb () throws SQLException {
        Statement s = connection.createStatement();
        s.execute(CHOOSE_DB);
        s.executeUpdate(CREATE_TBL_STMT+CREATE_TBL_SHELF);
        s.executeUpdate(CREATE_TBL_STMT+CREATE_TBL_PROD);
    }

    protected String sanitize(String input) {
        return input
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
    }

    protected boolean finish(){
        try {
            DbConnector.closeConnection();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

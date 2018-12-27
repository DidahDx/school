package sample.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

     private static connectionProperties dataConnect = new connectionProperties();

     //this method is used to connect to the database and returns the connection
    public static Connection getConnection(){
        Connection connection = null;
        try {
             connection= DriverManager.getConnection(dataConnect.getDatabaseUrl(), dataConnect.getUser(),dataConnect.getPassword());
        } catch (SQLException e) {
            System.out.println("connection failed");
        }

        return connection;
    }
}
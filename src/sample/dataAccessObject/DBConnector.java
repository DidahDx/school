package sample.database;

import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *   THIS CLASS IS USED TO CONNECT TO THE DATABASE
 *
 * */
public class DBConnector {

     private static connectionProperties dataConnect = new connectionProperties();

     //this method is used to connect to the database and returns the connection
    public static Connection getConnection(){
        Connection connection = null;
        try {
             connection= DriverManager.getConnection(dataConnect.getDatabaseUrl(),
                     dataConnect.getUser(),dataConnect.getPassword());
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Failed");
            alert.setHeaderText(null);
            alert.setContentText("Check connection and try again");
            alert.showAndWait();

            System.out.println("connection failed");
        }

        return connection;
    }
}
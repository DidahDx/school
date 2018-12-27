package sample.database;

import sample.model.admission.schoolDetailsGenerator;

import java.sql.*;
/**
 * THIS IS FOR TESTING DATABASE QUERIES
 * SHOULD BE REMOVED
 * */
public class databaseTester {
    public static void main(String[] args) {

        Connection connect;
        String table= "`users_details`";

        try {

            DBConnector dbConnector=new DBConnector();
            connect = dbConnector.getConnection();

            Statement mystate = connect.createStatement();
            ResultSet myresult = mystate.executeQuery("select max(admission_number) from finance");
            while (myresult.next()) {
                int ivalue =myresult.getInt("max(admission_number)");
                // String value= Integer.toString(ivalue);
                System.out.println(ivalue);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            System.out.println("Server Offline");
            e.printStackTrace();
        }
        schoolDetailsGenerator sdg=new schoolDetailsGenerator();

        System.out.println( sdg.getAdmissionNumber()+"  " + sdg.getStream()+" "+ sdg.getDorm());

    }
}

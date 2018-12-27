package sample.database;

import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * THIS CLASS IS USED TO DO CALLS TO THE DATABASE:
 *  1.TO INSERT VALUES INTO THE DATABASE
 *  2.GETS THE LAST ADMISSION NUMBER FROM THE DATABASE
 *  3.SEARCH THE DATABASE USING ADMISSION NUMBER
 *  4.
 *
 **/

public class DataAccessObject {
    Connection connection=DBConnector.getConnection();

    //this method is used to insert student names into the database
    public void AdmittingNewStudents(String firstName,String secondName,String lastName){
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String sql="INSERT into students_details(first_name,second_name,last_name) VALUES (?,?,?)";

        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,secondName);
            preparedStatement.setString(3,lastName);

            int rowCount=preparedStatement.executeUpdate();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Details Entered succefully");
            alert.showAndWait();
        } catch (SQLException e) {
          Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
          alert.setContentText("Error entering details");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    //This method gets the last admission number from database
    public int getLastAdmissionNumber() throws SQLException {
        int value = -1;
        Statement myState = connection.createStatement();
        ResultSet myResult = myState.executeQuery("select max(admission_number) from students_details ");
        while (myResult.next()) {
            value = myResult.getInt("max(admission_number)");
        }
        return value;
    }

    //this method queries the database for all the student details
    public ResultSet loadTable() throws SQLException {
        Statement myState=connection.createStatement();
        ResultSet rs= myState.executeQuery("SELECT * FROM students_details ");
        return rs;
     }

    //this method queries the database for all the student details
    public ResultSet loadsForms(int form) throws SQLException {
       String sql="SELECT * FROM students_details WHERE form=? ;";
       PreparedStatement preparedStatement=connection.prepareStatement(sql);
       preparedStatement.setInt(1, form);
        ResultSet rs= preparedStatement.executeQuery();
        return rs;
    }

  //used for searching the database with admission number
    public ResultSet searchTable(int admissionNumber) throws SQLException {
        String sql="SELECT * FROM students_details where admission_number=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,admissionNumber);
        ResultSet rs= preparedStatement.executeQuery();
        return rs;
    }
}
package sample.database.admission;

import javafx.scene.control.Alert;
import sample.database.DBConnector;

import java.security.Guard;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * THIS CLASS IS USED TO DO CALLS TO THE DATABASE:
 *        1.TO INSERT VALUES INTO THE DATABASE
 *        2.GETS THE LAST ADMISSION NUMBER FROM THE DATABASE
 *        3.SEARCH THE DATABASE USING ADMISSION NUMBER
 *        4.QUERIES THE DATABASE FOR STUDENT DETAILS AND GUARDIAN DETAILS
 *
 **/

public class DataAccessObject {
    private Connection connection= DBConnector.getConnection();


    public DataAccessObject(){

    }

    //this method is used to insert student names into the database
    public void SaveStudentsNames(String firstName, String secondName, String lastName, String County, String Gender,
                                  LocalDate birthDate, int admissionNumber, LocalDate dateOfAdmission, String StudentType,
                                  int Form, String Dorm, LocalTime time,String stream){
        PreparedStatement preparedStatement;
       String sql="INSERT INTO students_details(first_name,second_name,last_name,county_of_resident,gender,date_of_birth,admission_number," +
               "date_of_admission,student_type,form,dorm,time_of_admission,stream) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        try {

            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,secondName);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,County);
            preparedStatement.setString(5,Gender);
            preparedStatement.setDate(6, Date.valueOf(birthDate));
            preparedStatement.setInt(7,admissionNumber);
            preparedStatement.setDate(8, Date.valueOf(dateOfAdmission));
            preparedStatement.setString(9,StudentType);
            preparedStatement.setInt(10,Form);
            preparedStatement.setString(11,Dorm);
            preparedStatement.setTime(12, Time.valueOf(time));
           preparedStatement.setString(13,stream);

              int a= preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //this alert displays an error message
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error entering details"); //the error message
            alert.showAndWait();
            e.printStackTrace();
        }
    }






    //This method gets the last admission number from database
    public int getLastAdmissionNumber() throws SQLException {
        int value = 0;
        Statement myState = connection.createStatement();
        ResultSet myResult = myState.executeQuery("select max(admission_number) from students_details ");
        while (myResult.next()) {
            value = myResult.getInt("max(admission_number)");
        }
        return value;
    }

    //this method queries the database for all the student details
    public ResultSet loadStudentTable() throws SQLException {
        Statement myState=connection.createStatement();
        ResultSet rs= myState.executeQuery("SELECT * FROM students_details ");
        return rs;
     }

    //this method queries the database for all the student details for a specific form
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

    //this method queries the database for all the guardian detail according to form
    public ResultSet loadStudentsName(int admissionNumber) throws SQLException{
        String sql="SELECT guardian_details.guardian_first_name,guardian_details.guardian_id,guardian_details.guardian_last_name," +
                "guardian_details.guardian_phone_number,students_details.first_name,students_details.second_name,students_details.last_name," +
                "guardian_details.guardian_email,guardian_details.admission_number" +
                " FROM guardian_details INNER JOIN students_details ON " +
                "guardian_details.admission_number=students_details.admission_number " +
                "WHERE students_details.admission_number=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,admissionNumber);
        ResultSet rs=preparedStatement.executeQuery();
        return rs;
    }

}
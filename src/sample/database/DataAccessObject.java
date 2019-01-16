package sample.database;

import javafx.scene.control.Alert;

import java.security.Guard;
import java.sql.*;
import java.time.LocalDate;

/**
 * THIS CLASS IS USED TO DO CALLS TO THE DATABASE:
 *        1.TO INSERT VALUES INTO THE DATABASE
 *        2.GETS THE LAST ADMISSION NUMBER FROM THE DATABASE
 *        3.SEARCH THE DATABASE USING ADMISSION NUMBER
 *        4.QUERIES THE DATABASE FOR STUDENT DETAILS AND GUARDIAN DETAILS
 *
 **/

public class DataAccessObject {
    private Connection connection=DBConnector.getConnection();

    //this alert displays a confirmation message
    Alert alert=new Alert(Alert.AlertType.INFORMATION);

    String message;
    String GuardianFirstName;

    public DataAccessObject(){
        message="";
    }

    //this method is used to insert student names into the database
    public void SaveStudentsNames(String firstName, String secondName, String lastName, String County, String Gender, LocalDate birthDate,int admissionNumber){
        PreparedStatement preparedStatement;
       String sql="INSERT INTO students_details(first_name,second_name,last_name,county_of_resident,gender,date_of_birth,admission_number) VALUES (?,?,?,?,?,?,?) ";
        try {

            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,secondName);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,County);
            preparedStatement.setString(5,Gender);
            preparedStatement.setDate(6, Date.valueOf(birthDate));
            preparedStatement.setInt(7,admissionNumber);


            showsDetails(message);
//            preparedStatement.executeUpdate();
//            preparedStatement.close();
//            connection.close();

        } catch (SQLException e) {
            //this alert displays an error message
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error entering details"); //the error message
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    //this method save guardian details to the database
    public void SaveGuardianDetails(String GuardianFirstName,String GuardianLastName,String GuardianEmail, int phoneNumber,int admissionNumber)
    {

        String sql="INSERT INTO guardian_details(guardian_first_name,guardian_last_name,guardian_phone_number,guardian_email,admission_number) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,GuardianFirstName);
            preparedStatement.setString(2,GuardianLastName);
            preparedStatement.setInt(3,phoneNumber);
            preparedStatement.setString(4, GuardianEmail);
            preparedStatement.setInt(5,admissionNumber);

            if(!(GuardianFirstName==null||GuardianFirstName=="")){
                message+= "\n\n" +"\n========================================"+
                        "\n\t\t Guardian Details" +
                        "\n Students Admission Number:\t\t" + admissionNumber+
                        " \n Guardian First Name:\t\t" + GuardianFirstName +
                        "\n Guardian Last Name:\t\t" + GuardianLastName +
                        "\n Guardian Email:\t\t" + GuardianEmail
               + "\n Guardian Phone Number:\t\t"+phoneNumber;

            }

            showsDetails(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
//
//public void SaveSchoolDetails(){
//        String sql="";
//    try {
//        PreparedStatement preparedStatement=connection.prepareStatement(sql);
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//}


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

    //this method queries the database for all the guardian details
    public ResultSet loadGuardianTable() throws SQLException {
        String sql="SELECT * FROM guardian_details;";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
       ResultSet rs= preparedStatement.executeQuery();
       return rs;
    }

    //this method queries the database for all the guardian detail according to form
     public ResultSet loadGuardianForms(int form) throws SQLException{
        String sql="SELECT guardian_details.guardian_first_name,guardian_details.guardian_last_name," +
                "guardian_details.guardian_phone_number,guardian_details.guardian_email,guardian_details.admission_number" +
                " FROM guardian_details INNER JOIN students_details ON " +
                "guardian_details.admission_number=students_details.admission_number " +
                "WHERE students_details.form=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
      preparedStatement.setInt(1,form);
      ResultSet rs=preparedStatement.executeQuery();
      return rs;
    }

    //used for searching the database with admission number
    public ResultSet searchGuardianTable(int admissionNumber) throws SQLException {
        String sql="SELECT * FROM guardian_details where admission_number=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,admissionNumber);
        ResultSet rs= preparedStatement.executeQuery();
        return rs;
    }

    public void showsDetails(String  string){

        alert.setTitle("CONFIRMATION");
        alert.setHeaderText(null);
        alert.setContentText(string);  //the message

        alert.showAndWait();


    }

}
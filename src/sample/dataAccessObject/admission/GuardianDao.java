package sample.dataAccessObject.admission;

import javafx.scene.control.Alert;
import sample.dataAccessObject.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuardianDao {


    //this method save guardian details to the database
    public void AddGuardianDetails(String GuardianFirstName, String GuardianLastName, String GuardianEmail,
                                   String phoneNumber, int admissionNumber,Connection connection) throws SQLException {

        String sql="INSERT INTO guardian_details(guardian_first_name,guardian_last_name,guardian_phone_number,guardian_email,admission_number) VALUES (?,?,?,?,?) ";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,GuardianFirstName);
            ps.setString(2,GuardianLastName);
            ps.setString(4, GuardianEmail);
            ps.setInt(5,admissionNumber);
            ps.setString(3,phoneNumber);

            ps.executeUpdate();
    }

    //this is used to check if an email address exist
    public ResultSet checkGuardianEmail(String email,Connection connection) throws SQLException {

        String sql="select guardian_id from guardian_details where guardian_email=?";
        ResultSet rs = null;

            PreparedStatement preSt=connection.prepareStatement(sql);
            preSt.setString(1,email);
            rs =preSt.executeQuery();


        return rs;
    }

    //this method is used for updating guardian details
    public void UpdateGuardianDetails(String guardianFirstName, String GuardianLastName, String GuardianEmail,
                                      String phoneNumber,int guardianId,Connection connection) throws SQLException {

        String sql="UPDATE `guardian_details` SET `guardian_first_name`=?, `guardian_last_name`=? ," +
                " `guardian_phone_number`=?, `guardian_email` =?" +
                "WHERE `guardian_details`.`guardian_id` = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,guardianFirstName);
            ps.setString(2,GuardianLastName);
            ps.setString(3,phoneNumber);
            ps.setString(4, GuardianEmail);
            ps.setInt(5,guardianId);
            ps.executeUpdate();


    }

    //this method queries the database for all the guardian details
    public ResultSet loadGuardianTable(Connection connection) throws SQLException {
        String sql="SELECT * FROM guardian_details;";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ResultSet rs= preparedStatement.executeQuery();
        return rs;
    }

    //this method queries the database for all the guardian detail according to form
    public ResultSet loadGuardianForms(int form,Connection connection) throws SQLException{
        String sql="SELECT guardian_details.guardian_first_name,guardian_details.guardian_last_name," +
                "guardian_details.guardian_phone_number," +
                "guardian_details.guardian_email,guardian_details.admission_number" +
                " FROM guardian_details INNER JOIN students_details ON " +
                "guardian_details.admission_number=students_details.admission_number " +
                "WHERE students_details.form=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,form);
        ResultSet rs=preparedStatement.executeQuery();
        return rs;
    }

    public ResultSet loadGuardianStream(int form,String stream,Connection connection) throws SQLException{
        String sql="SELECT guardian_details.guardian_first_name,guardian_details.guardian_last_name," +
                "guardian_details.guardian_phone_number,guardian_details.guardian_email,guardian_details.admission_number" +
                " FROM guardian_details INNER JOIN students_details ON " +
                "guardian_details.admission_number=students_details.admission_number " +
                "WHERE students_details.form=? AND students_details.stream=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,form);
        preparedStatement.setString(2,stream);
        ResultSet rs=preparedStatement.executeQuery();
        return rs;
    }

    //used for searching the database with admission number
    public ResultSet searchGuardianTable(int admissionNumber,Connection connection) throws SQLException {
        String sql="SELECT * FROM guardian_details where admission_number=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,admissionNumber);
        ResultSet rs= preparedStatement.executeQuery();
        return rs;
    }

    //this method is used to delete details of the guardian
    public void deleteGuardian(int id,Connection connection ) throws SQLException {
        String sql="DELETE FROM `guardian_details` WHERE `guardian_details`.`guardian_id` = ?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
    }
}
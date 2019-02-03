package sample.dataAccessObject.admission;

import javafx.scene.control.Alert;
import sample.dataAccessObject.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuardianDao {
    private Connection connection= DBConnector.getConnection();

    //this method save guardian details to the database
    public void AddGuardianDetails(String GuardianFirstName, String GuardianLastName, String GuardianEmail, int phoneNumber, int admissionNumber)
    {

        String sql="INSERT INTO guardian_details(guardian_first_name,guardian_last_name,guardian_phone_number,guardian_email,admission_number) VALUES (?,?,?,?,?) ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,GuardianFirstName);
            ps.setString(2,GuardianLastName);
            ps.setString(4, GuardianEmail);
            ps.setInt(5,admissionNumber);
            ps.setInt(3,phoneNumber);

            ps.executeUpdate();

        } catch (SQLException e)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(" Erorr entering guardian details\n"+e.getSQLState());
            alert.show();
            e.printStackTrace();
        }
    }

    //this is used to check if an email address exist
    public ResultSet checkGuardianEmail(String email){
        String sql="select guardian_id from guardian_details where guardian_email=?";
        ResultSet rs = null;
        try {
            PreparedStatement preSt=connection.prepareStatement(sql);
            preSt.setString(1,email);
            rs =preSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //this method is used for updating guardian details
    public void UpdateGuardianDetails(String guardianFirstName, String GuardianLastName, String GuardianEmail, int phoneNumber,int guardianId){
        String sql="UPDATE `guardian_details` SET `guardian_first_name`=?, `guardian_last_name`=? ," +
                " `guardian_phone_number`=?, `guardian_email` =?" +
                "WHERE `guardian_details`.`guardian_id` = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,guardianFirstName);
            ps.setString(2,GuardianLastName);
            ps.setInt(3,phoneNumber);
            ps.setString(4, GuardianEmail);
            ps.setInt(5,guardianId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    //used for searching the database with admission number
    public ResultSet searchGuardianTable(int admissionNumber) throws SQLException {
        String sql="SELECT * FROM guardian_details where admission_number=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,admissionNumber);
        ResultSet rs= preparedStatement.executeQuery();
        return rs;
    }

    //this method is used to delete details of the guardian
    public void deleteGuardian(int id ) throws SQLException {
        String sql="DELETE FROM `guardian_details` WHERE `guardian_details`.`guardian_id` = ?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
    }
}

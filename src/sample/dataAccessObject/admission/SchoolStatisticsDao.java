package sample.dataAccessObject.admission;

import sample.dataAccessObject.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * THIS CLASS CONTAINS THE QUERIES FOR SCHOOL STATISTICS
 *
 * */

public class SchoolStatisticsDao {
    Connection con=DBConnector.getConnection();

    //this method is used to get the total number pupils in the school
    public ResultSet SchoolStatistics() throws SQLException {
        ResultSet rs = null;
        String sql="SELECT COUNT(admission_number) FROM students_details";
        PreparedStatement ps=con.prepareStatement(sql);
        rs=ps.executeQuery();
        return rs;
    }

    //this method is used to get the total number boys or girls students in the whole school
    public ResultSet GenderSchoolStatistics(String gender) throws SQLException {
        ResultSet rs = null;
        String sql="SELECT COUNT(admission_number) FROM students_details WHERE gender=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,gender);
        rs=ps.executeQuery();
        return rs;
    }

    //this method is used to get the total number students in the whole school for a particular stream
    public ResultSet StreamSchoolStatistics(String stream) throws SQLException {
        ResultSet rs = null;
        String sql="SELECT COUNT(admission_number) FROM students_details WHERE stream=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,stream);
        rs=ps.executeQuery();
        return rs;
    }


    //this method is used to get the total number boys or girls students in the whole school for a particular stream
    public ResultSet StreamGenderSchoolStatistics(String stream,String gender) throws SQLException {
        ResultSet rs = null;
        String sql="SELECT COUNT(admission_number) FROM students_details WHERE stream=? and gender=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1,stream);
        ps.setString(2,gender);
        rs=ps.executeQuery();
        return rs;
    }

    //this method is used to get the total number pupils in a particular form
    public ResultSet FormStatistics(int form) throws SQLException {
        ResultSet rs = null;
        String sql="SELECT COUNT(admission_number) FROM students_details where form=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setInt(1,form);
        rs=ps.executeQuery();
        return rs;
    }

    //this method is used to get the total  number of boys or girls in a particular form
    public ResultSet GenderFormStatistics(int form,String gender) throws SQLException {
        ResultSet rs = null;
        String sql="SELECT COUNT(admission_number) FROM students_details where form=? and gender=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setInt(1,form);
        ps.setString(2,gender);
        rs=ps.executeQuery();
        return rs;
    }

    //this method is used to get the total number pupils in a particular stream
    public ResultSet StreamFormStatistics(int form,String stream) throws SQLException {
        ResultSet rs = null;
        String sql="SELECT COUNT(admission_number) FROM students_details where form=? and stream=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(2,stream);
        ps.setInt(1,form);
        rs=ps.executeQuery();
        return rs;
    }

    //this method is used to get the total number boys or girls in a particular stream
    public ResultSet StreamGenderFormStatistics(int form,String gender,String stream) throws SQLException {
        ResultSet rs = null;
        String sql="SELECT COUNT(admission_number) FROM students_details where form=? and gender=? and stream=?";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setInt(1,form);
        ps.setString(2,gender);
        ps.setString(3,stream);
        rs=ps.executeQuery();
        return rs;
    }
}
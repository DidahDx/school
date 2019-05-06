package sample.dataAccessObject.finance;

import sample.dataAccessObject.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CREATE
 * READ
 * UPDATE
 *
 * */

public class SetFeesDao {

    //this method inserts fees to the database
    public void setFees(int form,double termOne,double termTwo,double termThree,Connection connection) throws SQLException {
        String sql="INSERT INTO expected_fees(form,term_one,term_three,term_two) VALUES (?,?,?,?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,form);
        ps.setDouble(2,termOne);
        ps.setDouble(4,termTwo);
        ps.setDouble(3,termThree);
        ps.executeUpdate();
    }

    //this method inserts fees to the database
    public void setBoarderFees(int form,double termOne,double termTwo,double termThree,Connection connection) throws SQLException {
        String sql="INSERT INTO expected_fees(form,boarder_term_one,boarder_term_two,boarder_term_three) VALUES (?,?,?,?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,form);
        ps.setDouble(2,termOne);
        ps.setDouble(3,termTwo);
        ps.setDouble(4,termThree);
        ps.executeUpdate();
    }

    //this method updates the expected fees
    public void UpdateFees(double term1,double term2,double term3,int form,Connection connection) throws SQLException {
        String sql="UPDATE expected_fees SET term_one=?,term_two=?,term_three=? where form=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setDouble(1,term1);
        ps.setDouble(2,term2);
        ps.setDouble(3,term3);
        ps.setInt(4,form);
        ps.executeUpdate();
    }

    //this method updates the expected fees
    public void UpdateBoarderFees(double term1,double term2,double term3,int form,Connection connection) throws SQLException {
        String sql="UPDATE expected_fees SET boarder_term_one=?,boarder_term_three=?,boarder_term_two=? where form=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setDouble(1,term1);
        ps.setDouble(3,term2);
        ps.setDouble(2,term3);
        ps.setInt(4,form);
        ps.executeUpdate();
    }

    //this method reads the values from database
    public ResultSet ViewFees(int form,Connection connection) throws SQLException{
        String sql="SELECT * FROM expected_fees where form=?";
        ResultSet rs = null;
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,form);
        rs=ps.executeQuery();
        return rs;
    }

}

package sample.dataAccessObject.finance;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import javafx.scene.control.Alert;
import sample.dataAccessObject.DBConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * THIS CLASS IS USED TO MAKE CALLS TO THE DATABASE TO THE FINANCE TABLES TO :
 *   1.INSERT
 *   2.READ
 *   3.CREATE
 *   4.UPDATE
 * */
public class FeeDao {

    //this method is used to get all the data from the finance table
    public ResultSet loadTable(Connection connection) throws SQLException {
        String sql="SELECT * FROM finance  ORDER BY `finance`.`finance_id` ASC";
        PreparedStatement ps=connection.prepareStatement(sql);
       return ps.executeQuery();
    }

    //this method is used to add paid fees to the database
    public void AddFee(int admission, double feeExpected, double feePaid, LocalDate dateOfPayment, double balance,
                       String bankTransactionId, int term, LocalTime timeOfPayment,int form,String BankName,Connection connection) throws SQLException {
        String sql="INSERT INTO finance(admission_number,fee_expected,fee_paid,date_of_payment" +
                ",balance,bank_transaction_id,term,time_of_payment,form,bank_name) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,admission);
        ps.setDouble(2,feeExpected);
        ps.setDouble(3,feePaid);
        ps.setDate(4, Date.valueOf(dateOfPayment));
        ps.setDouble(5,balance);
        ps.setString(6,bankTransactionId);
        ps.setInt(7,term);
        ps.setTime(8, Time.valueOf(timeOfPayment));
        ps.setInt(9,form);
        ps.setString(10,BankName);

        ps.executeUpdate();
    }

    //this method is used to Update
    @Deprecated
    public void UpdateFee(int admission, double feeExpected, double feePaid, LocalDate dateOfPayment, double balance,
                       String bankTransactionId, int term, LocalTime timeOfPayment,int financeId,String BankName,Connection connection) throws SQLException {
        String sql="UPDATE finance SET admission_number=?,fee_expected=?,fee_paid=?,date_of_payment=?" +
                ",balance=?,bank_transaction_id=?,term=?,time_of_payment=?,bank_name=? where finance_id=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,admission);
        ps.setDouble(2,feeExpected);
        ps.setDate(4, Date.valueOf(dateOfPayment));
        ps.setDouble(3,feePaid);
        ps.setDouble(5,balance);
        ps.setInt(7,term);
        ps.setString(6,bankTransactionId);
        ps.setTime(8, Time.valueOf(timeOfPayment));
        ps.setString(9,BankName);
        ps.setInt(10,financeId);
        ps.executeUpdate();
    }

    //this method is used to delete from the finance table
    public void deleteFees(int financeId,Connection connection) throws SQLException {
        String sql="DELETE FROM finance WHERE finance_id=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,financeId);
        ps.executeUpdate();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Deleted successfully");
        alert.showAndWait();
    }

    //this method is used to search the finance table using admission number
    public ResultSet search(int admission,Connection connection) throws SQLException {
        String sql="SELECT * FROM finance where admission_number=? ORDER BY `finance`.`finance_id` ASC";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,admission);
        return ps.executeQuery();
    }

    //this method is used to search the finance table using form number
    public ResultSet searchForm(int form,Connection connection) throws SQLException {
        String sql="SELECT finance.admission_number,finance.fee_expected,finance.fee_paid,finance.date_of_payment" +
        ",finance.balance,finance.bank_transaction_id,finance.bank_name,finance.term,finance.time_of_payment,finance.form,finance.finance_id,finance.form" +
                " from finance inner join " +
                "students_details on finance.admission_number=students_details.admission_number WHERE" +
                " students_details.form=? ";

        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,form);
        return ps.executeQuery();
    }

    //this method is used to check if any fee is paid for the current term and form from the finance table
    public ResultSet searchForCheckingFees(int admission,int form,int term,Connection connection) throws SQLException {
        String sql="SELECT * FROM finance where admission_number=? and form=? and term=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,admission);
        ps.setInt(2,form);
        ps.setInt(3,term);
        return ps.executeQuery();
    }

    //this method is used to get the current balance from the finance table
    public ResultSet getBalance(int admission,Connection connection) throws SQLException {
        String sql="SELECT * FROM finance where admission_number=? and" +
                " finance_id=(select max(finance_id) from finance where admission_number=?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,admission);
        ps.setInt(2,admission);
        return ps.executeQuery();
    }

}
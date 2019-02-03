package sample.dataAccessObject.examination;

import javafx.scene.control.Alert;
import sample.dataAccessObject.DBConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AverageMarksDao {

    Connection connection= DBConnector.getConnection();

    //this is used to save average_marks into the database
    public void InsertAverageMarks(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                               int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                               int ComputerStudies, int agriculture, int form, int term, LocalDate date, LocalTime time,int total) throws SQLException {

        String sql="INSERT INTO average_marks (admission_number,maths,english,kiswahili,biology,chemistry,physics,cre,history," +
                "geography,agriculture,business_studies,computer_studies,term,form,date,time,total) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,admissionNumber);
        ps.setInt(2,Maths);
        ps.setInt(3,English);
        ps.setInt(4,Kiswahili);
        ps.setInt(5,Biology);
        ps.setInt(6,Chemistry);
        ps.setInt(7,Physics);
        ps.setInt(8,Cre);
        ps.setInt(9,History);
        ps.setInt(10,Geography);
        ps.setInt(11,agriculture);
        ps.setInt(12,BusinessStudies);
        ps.setInt(13,ComputerStudies);
        ps.setInt(14,term);
        ps.setInt(15,form);
        ps.setDate(16, Date.valueOf(date));
        ps.setTime(17, Time.valueOf(time));
        ps.setInt(18,total);

        ps.executeUpdate();

        Alert aler=new Alert(Alert.AlertType.INFORMATION);
        aler.setContentText("added");
        aler.showAndWait();
    }


    //this is used to Update the average_marks into the database
    public void UpdateAverageMarks(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                               int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                               int ComputerStudies, int agriculture, int form, int term, LocalDate date, LocalTime time,int total) throws SQLException {

        String sql="UPDATE average_marks SET admission_number=?,maths=?,english=?,kiswahili=?,biology=?,chemistry=?,physics=?,cre=?" +
                ",history=?,geography=?,agriculture=?,business_studies=?,computer_studies=?,term=?,form=? ,date=?,time=? total=? WHERE  form=? and term=? and admission_number=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(2,Maths);
        ps.setInt(1,admissionNumber);
        ps.setInt(4,Kiswahili);
        ps.setInt(3,English);
        ps.setInt(6,Chemistry);
        ps.setInt(7,Physics);
        ps.setInt(5,Biology);
        ps.setInt(8,Cre);
        ps.setInt(10,Geography);
        ps.setInt(9,History);
        ps.setInt(14,term);
        ps.setInt(11,agriculture);
        ps.setInt(12,BusinessStudies);
        ps.setInt(13,ComputerStudies);
        ps.setInt(15,form);
        ps.setInt(19,form);
        ps.setDate(16, Date.valueOf(date));
        ps.setTime(17, Time.valueOf(time));
        ps.setInt(20,term);
        ps.setInt(21,admissionNumber);
        ps.setInt(18,total);
        ps.executeUpdate();

        Alert aler=new Alert(Alert.AlertType.INFORMATION);
        aler.setContentText("Updated");
        aler.showAndWait();
    }

    //this is used to delete the average marks from the database
    public void  DeleteAvearageMarks(int averageId) throws SQLException {
        String sql="DELETE FROM average_marks WHERE average_id=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,averageId);
        ps.executeUpdate();
    }

    //this is used to read all the average marks from the database
    public ResultSet ReadAverageMarks() throws SQLException {
        String sql="SELECT * FROM average_marks";
        PreparedStatement pS=connection.prepareStatement(sql);

        return pS.executeQuery();
    }

    //this used to search the database for average marks with admission number
    public ResultSet Search(int admissionNumber) throws SQLException {
        String sql="SELECT * FROM average_marks WHERE admission_number=?";
        PreparedStatement pS=connection.prepareStatement(sql);
        pS.setInt(1,admissionNumber);

        return pS.executeQuery();
    }

    //this method is used to search the database for average marks for a particular form
    public ResultSet SearchForm(int form) throws SQLException {
        String sql="SELECT * FROM average_marks INNER JOIN students_details ON average_marks.admission_number=" +
                "students_details.admission_number WHERE students_details.form = ?";

        PreparedStatement PS=connection.prepareStatement(sql);
        PS.setInt(1,form);
        return PS.executeQuery();
    }

    //this is used to read all the for Aversge marks for a particular student from the database
    public ResultSet getCurrentStudentAveragemMarks(int form,int term,int admissionNumber) throws SQLException {
        String sql="SELECT * FROM average_marks WHERE form=? and term=? and admission_number=?";
        PreparedStatement pS=connection.prepareStatement(sql);
        pS.setInt(2,term);
        pS.setInt(1,form);
        pS.setInt(3,admissionNumber);

        return pS.executeQuery();
    }

}

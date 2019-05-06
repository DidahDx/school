package sample.dataAccessObject.examination;

import javafx.scene.control.Alert;
import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.HikariConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AverageMarksDao {

    private ResultSet rs=null;

    //this is used to save average_marks into the database
    public void InsertAverageMarks(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                               int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                               int ComputerStudies, int agriculture, int form, int term, LocalDate date,
                                   LocalTime time,int total,String stream,Connection connection) throws SQLException {
        PreparedStatement ps=null;
        String sql="INSERT INTO average_marks (admission_number,maths,english,kiswahili,biology,chemistry,physics,cre,history," +
                "geography,agriculture,business_studies,computer_studies,term,form,date,time,total,stream) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

         ps=connection.prepareStatement(sql);

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
        ps.setString(19,stream);

        ps.executeUpdate();

    }


    //this is used to Update the average_marks into the database
    public void UpdateAverageMarks(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                               int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                               int ComputerStudies, int agriculture, int form, int term, LocalDate date,
                                   LocalTime time,int total,String stream,Connection connection) throws SQLException {

        PreparedStatement ps=null;
        String sql="UPDATE average_marks SET admission_number=?,maths=?,english=?,kiswahili=?,biology=?,chemistry=?,physics=?,cre=?" +
                ",history=?,geography=?,agriculture=?,business_studies=?,computer_studies=?,term=?,form=? ,date=?,time=? ,total=? ,stream=? WHERE  form=? and term=? and admission_number=?";


        ps=connection.prepareStatement(sql);
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
        ps.setInt(20,form);
        ps.setDate(16, Date.valueOf(date));
        ps.setTime(17, Time.valueOf(time));
        ps.setInt(21,term);
        ps.setInt(22,admissionNumber);
        ps.setInt(18,total);
        ps.setString(19,stream);
        ps.executeUpdate();

    }

    //this is used to delete the average marks from the database
    public void  DeleteAvearageMarks(int averageId,Connection connection) throws SQLException {
        String sql="DELETE FROM average_marks WHERE average_marks_id=?";
        PreparedStatement ps=null;

        ps=connection.prepareStatement(sql);
        ps.setInt(1,averageId);
        ps.executeUpdate();

    }

    //this is used to read all the average marks from the database
    public ResultSet ReadAverageMarks(Connection connection) throws SQLException {
        String sql="SELECT * FROM average_marks";
         rs=null;
        PreparedStatement ps=null;

            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

        return rs;
    }

    //this used to search the database for average marks with admission number
    public ResultSet Search(int admissionNumber,Connection connection) throws SQLException {
        String sql="SELECT * FROM average_marks WHERE admission_number=?";
        PreparedStatement ps=null;
         rs=null;

        ps=connection.prepareStatement(sql);
        ps.setInt(1,admissionNumber);
        rs=ps.executeQuery();

        return rs;
    }

    //this method is used to search the database for average marks for a particular form
    public ResultSet SearchForm(int form,Connection connection) throws SQLException {
        String sql="SELECT * FROM average_marks INNER JOIN students_details ON average_marks.admission_number=" +
                "students_details.admission_number WHERE students_details.form = ?";
         rs=null;
        PreparedStatement ps=null;

            ps=connection.prepareStatement(sql);
        ps.setInt(1,form);
        rs= ps.executeQuery();

        return rs;
    }

    //this is used to read all the for Average marks for a particular student from the database
    public ResultSet getCurrentStudentAverageMarks(int form, int term, int admissionNumber, Connection connection) throws SQLException {
        String sql="SELECT * FROM average_marks WHERE form=? and term=? and admission_number=?";
        rs=null;
        PreparedStatement ps=null;

        ps=connection.prepareStatement(sql);
        ps.setInt(2,term);
        ps.setInt(1,form);
        ps.setInt(3,admissionNumber);
         rs=ps.executeQuery();

        return  rs;
    }

   // this is used to read all the students total marks from the database for a certain stream
    public ResultSet getStudentsTotalMarks(int form,int term,String stream,Connection connection) throws SQLException {
        String sql="SELECT * FROM average_marks WHERE form=? and term=? and stream=? ORDER BY total DESC";
        return getResultSet(form, term, stream, sql,connection);
    }

    // this is used to read all the students total marks from the database for a certain stream with the highest stream position
    public ResultSet getStudentsTotalMarksSetTie(int form,int term,String stream,Connection connection) throws SQLException {
        String sql = "SELECT * FROM average_marks WHERE form=? and term=? and stream=? ORDER BY stream_position ASC";
        return getResultSet(form, term, stream, sql,connection);
    }

    private ResultSet getResultSet(int form, int term, String stream, String sql,Connection connection) throws SQLException {
         rs=null;
        PreparedStatement ps=null;

        ps= connection.prepareStatement(sql);
        ps.setInt(1, form);
        ps.setInt(2, term);
        ps.setString(3, stream);
        rs = ps.executeQuery();

        return rs;
    }

    //this queries inserts the stream Position
    public void setStreamPosition(int averageId,int streamPosition,int total,Connection connection) throws SQLException {

        String sql="UPDATE average_marks SET stream_position=? where average_marks_id=? and total=? ";

        PreparedStatement ps=null;

        ps=connection.prepareStatement(sql);
        ps.setInt(1,streamPosition);
        ps.setInt(2,averageId);
        ps.setInt(3,total);
            ps.executeUpdate();

    }

    // this is used to read all the students total marks from the database for a certain form
    public ResultSet getTotalMarks(int form,int term,Connection connection) throws SQLException {
        String sql="SELECT * FROM average_marks WHERE form=? and term=? ORDER BY total DESC";
         rs=null;
        return getResultSet(form, term, sql, rs,connection);
    }

    private ResultSet getResultSet(int form, int term, String sql, ResultSet rs,Connection connection) throws SQLException {
        PreparedStatement ps=null;

            ps=connection.prepareStatement(sql);
        ps.setInt(1,form);
        ps.setInt(2,term);
        rs=ps.executeQuery();

        return  rs;
    }

    // this is used to read all the students total marks from the database for a certain form with the highest stream position
    public ResultSet getTotalMarksSetTies(int form,int term,Connection connection) throws SQLException {
        String sql="SELECT * FROM average_marks WHERE form=? and term=? ORDER BY overall_position ASC";
        rs=null;
        return getResultSet(form, term, sql, rs,connection);
    }


    //this queries inserts the overall Position
    public void setOverallPosition(int averageId,int OverallPosition,int total,Connection connection) throws SQLException {

        String sql="UPDATE average_marks SET overall_position=? where average_marks_id=? and total=? ";

        PreparedStatement ps=null;

            ps=connection.prepareStatement(sql);
        ps.setInt(1,OverallPosition);
        ps.setInt(3,total);
        ps.setInt(2,averageId);
            ps.executeUpdate();
    }

}
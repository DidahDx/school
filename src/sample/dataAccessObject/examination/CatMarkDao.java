package sample.dataAccessObject.examination;

import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.HikariConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CatMarkDao {


    //this is used to save cat marks into the database
    public void InsertCatMarks(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                               int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                               int ComputerStudies, int agriculture, int form, int term, LocalDate date,
                               LocalTime time,String stream,Connection connection) throws SQLException {

        String sql="INSERT INTO cat_marks(admission_number,maths,english,kiswahili,biology,chemistry,physics,cre,history," +
                "geography,agriculture,business_studies,computer_studies,term,form,date,time,stream) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=null;

        ps=connection.prepareStatement(sql);
        ps.setInt(1,admissionNumber);
        ps.setInt(3,English);
        ps.setInt(2,Maths);
        ps.setInt(4,Kiswahili);
        ps.setInt(5,Biology);
        ps.setInt(7,Physics);
        ps.setInt(6,Chemistry);
        ps.setInt(8,Cre);
        ps.setInt(9,History);
        ps.setInt(11,agriculture);
        ps.setInt(10,Geography);
        ps.setInt(12,BusinessStudies);
        ps.setInt(13,ComputerStudies);
        ps.setInt(14,term);
        ps.setInt(15,form);
        ps.setTime(17, Time.valueOf(time));
        ps.setDate(16, Date.valueOf(date));
        ps.setString(18,stream);
        ps.executeUpdate();

    }


    //this is used to Update the cat marks into the database
    public void  UpdateCatMarks(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                               int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                               int ComputerStudies, int agriculture, int form, int term,int catId,LocalTime time,
                               LocalDate edate,String stream,Connection connection) throws SQLException {

        String sql="UPDATE cat_marks SET admission_number=?,maths=?,english=?,kiswahili=?,biology=?,chemistry=?,physics=?,cre=?" +
                ",history=?,geography=?,agriculture=?,business_studies=?,computer_studies=?,term=?,form=?,time=?,date=?,stream=? WHERE  cat_id=?";

        PreparedStatement ps=null;

         ps=connection.prepareStatement(sql);
        ps.setInt(2,Maths);
        ps.setInt(1,admissionNumber);
        ps.setInt(3,English);
        ps.setInt(4,Kiswahili);
        ps.setInt(6,Chemistry);
        ps.setInt(5,Biology);
        ps.setInt(7,Physics);
        ps.setInt(8,Cre);
        ps.setInt(9,History);
        ps.setInt(10,Geography);
        ps.setInt(12,BusinessStudies);
        ps.setInt(11,agriculture);
        ps.setInt(13,ComputerStudies);
        ps.setInt(14,term);
        ps.setInt(15,form);
        ps.setString(18,stream);
        ps.setInt(19,catId);
        ps.setTime(16, Time.valueOf(time));
        ps.setDate(17, Date.valueOf(edate));
        ps.executeUpdate();

    }

    //this is used to delete the cat marks from the database
    public void  DeleteCatMarks(int catId,Connection connection) throws SQLException {
        ResultSet rs=null;
        String sql="DELETE FROM cat_marks WHERE cat_id=?";
        PreparedStatement ps=null;
        ps=connection.prepareStatement(sql);
        ps.setInt(1,catId);
        ps.executeUpdate();

    }

    //this is used to read all the cat marks from the database
    public ResultSet ReadCatMarks(Connection connection) throws SQLException {
        String sql="SELECT * FROM cat_marks";
        ResultSet rs=null;
        PreparedStatement ps=null;

        ps=connection.prepareStatement(sql);
         rs=ps.executeQuery();

        return rs;
    }

    //this used to search the database for cat marks with admission number
    public ResultSet Search(int admissionNumber,Connection connection) throws SQLException {
        ResultSet rs=null;
        String sql="SELECT * FROM cat_marks WHERE admission_number=?";
        PreparedStatement ps=null;

        ps=connection.prepareStatement(sql);
        ps.setInt(1,admissionNumber);
        rs= ps.executeQuery();

        return rs;
    }

    //this method is used to search the database for Cat marks for a particular form
    public ResultSet SearchForm(int form,Connection connection) throws SQLException {
        String sql="SELECT * FROM cat_marks INNER JOIN students_details ON cat_marks.admission_number=" +
                "students_details.admission_number WHERE students_details.form = ?";
        ResultSet rs=null;
        PreparedStatement ps=null;

        ps=connection.prepareStatement(sql);
        ps.setInt(1,form);
        rs=ps.executeQuery();

        return rs;
    }

    //this is used to read all the for cat marks for a particular student from the database
    public ResultSet getCurrentStudentMarks(int form,int term,int admissionNumber,Connection connection) throws SQLException {
        String sql="SELECT * FROM cat_marks WHERE form=? and term=? and admission_number=?";
        ResultSet rs=null;
        PreparedStatement ps=null;

        ps=connection.prepareStatement(sql);
        ps.setInt(2,term);
        ps.setInt(1,form);
        ps.setInt(3,admissionNumber);
        rs=ps.executeQuery();


        return rs;
    }

}
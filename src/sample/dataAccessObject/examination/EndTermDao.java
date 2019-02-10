package sample.dataAccessObject.examination;

import sample.dataAccessObject.DBConnector;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class EndTermDao {

    Connection connection= DBConnector.getConnection();

    //this method is used to save End Term marks into the database
    public void InsertEndTermMarks(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                                   int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                                   int ComputerStudies, int agriculture, int form, int term, LocalDate date, LocalTime time,String stream) throws SQLException {


        String sql="INSERT INTO end_term_marks(admission_number,maths,english,kiswahili,biology,chemistry,physics,cre,history," +
                "geography,agriculture,business_studies,computer_studies,term,form,date,time,stream) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,admissionNumber);
        ps.setInt(2,Maths);
        ps.setInt(4,Kiswahili);
        ps.setInt(3,English);
        ps.setInt(6,Chemistry);
        ps.setInt(5,Biology);
        ps.setInt(7,Physics);
        ps.setInt(9,History);
        ps.setInt(8,Cre);
        ps.setInt(10,Geography);
        ps.setInt(11,agriculture);
        ps.setInt(12,BusinessStudies);
        ps.setInt(14,term);
        ps.setInt(13,ComputerStudies);
        ps.setDate(16, Date.valueOf(date));
        ps.setInt(15,form);
        ps.setTime(17, Time.valueOf(time));
        ps.setString(18,stream);
        ps.executeUpdate();

    }

    //this method is used to Update the End term marks into the database
    public void UpdateEndTermMarks(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                               int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                               int ComputerStudies, int agriculture, int form, int term,int endTermId,LocalTime time,LocalDate date,String stream) throws SQLException {


        String sql="UPDATE end_term_marks SET admission_number=?,maths=?,english=?,kiswahili=?,biology=?,chemistry=?,physics=?,cre=?" +
                ",history=?,geography=?,agriculture=?,business_studies=?,computer_studies=?,term=?,form=? ,time=?,date=?,stream=? " +
                "WHERE  end_term_id=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(2,Maths);
        ps.setInt(1,admissionNumber);
        ps.setInt(4,Kiswahili);
        ps.setInt(6,Chemistry);
        ps.setInt(3,English);
        ps.setInt(5,Biology);
        ps.setInt(7,Physics);
        ps.setInt(8,Cre);
        ps.setInt(10,Geography);
        ps.setInt(9,History);
        ps.setInt(12,BusinessStudies);
        ps.setInt(11,agriculture);
        ps.setInt(13,ComputerStudies);
        ps.setInt(15,form);
        ps.setInt(19,endTermId);
        ps.setInt(14,term);
        ps.setDate(17, Date.valueOf(date));
        ps.setTime(16, Time.valueOf(time));
        ps.setString(18,stream);

        ps.executeUpdate();

    }

    //this method is used to delete the End Term Marks from the database
    public void  DeleteEndTermMarks(int EndTermId) throws SQLException {
        String sql="DELETE FROM end_term_marks WHERE end_term_id=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,EndTermId);
        ps.executeUpdate();
    }

    //this method is used to read all the End Term Marks from the database
    public ResultSet ReadEndTermMarks() throws SQLException {

        String sql="SELECT * FROM end_term_marks";
        PreparedStatement pS=connection.prepareStatement(sql);
        ResultSet rs=pS.executeQuery();

        return rs;
    }

    //this method used to search the database for End Term marks with admission number
    public ResultSet Search(int admissionNumber) throws SQLException {
        String sql="SELECT * FROM end_term_marks WHERE admission_number=?";
        PreparedStatement pS=connection.prepareStatement(sql);
        pS.setInt(1,admissionNumber);

        return pS.executeQuery();
    }

    //this method is used to search the database for End Term marks for a particular form
    public ResultSet SearchForm(int form) throws SQLException {
        String sql="SELECT * FROM end_term_marks INNER JOIN students_details ON end_term_marks.admission_number=" +
                "students_details.admission_number WHERE students_details.form = ?";

        PreparedStatement PS=connection.prepareStatement(sql);
        PS.setInt(1,form);
       return PS.executeQuery();
    }

    //this is used to read all the for End Term marks for a particular student from the database
    public ResultSet getCurrentStudentEndTermMarks(int form,int term,int admissionNumber) throws SQLException {
        String sql="SELECT * FROM end_term_marks WHERE form=? and term=? and admission_number=?";
        PreparedStatement pS=connection.prepareStatement(sql);
        pS.setInt(2,term);
        pS.setInt(3,admissionNumber);
        pS.setInt(1,form);

        return pS.executeQuery();
    }

}
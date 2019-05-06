package sample.dataAccessObject.admission;

import javafx.scene.control.Alert;
import sample.dataAccessObject.DBConnector;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * THIS CLASS IS USED TO DO CALLS TO THE DATABASE:
 *        1.TO INSERT VALUES INTO THE DATABASE
 *        2.GETS THE LAST ADMISSION NUMBER FROM THE DATABASE
 *        3.SEARCH THE DATABASE USING ADMISSION NUMBER
 *        4.QUERIES THE DATABASE FOR STUDENT DETAILS AND GUARDIAN DETAILS
 *
 **/

public class StudentDao {

    //this method is used to insert student names into the database
    public void SaveStudentsNames(String firstName, String secondName, String lastName, String County, String Gender,
                                  LocalDate birthDate, int admissionNumber, LocalDate dateOfAdmission, String StudentType,
                                  int Form, String Dorm, LocalTime time,String stream,int term,Connection connection) throws SQLException {
        PreparedStatement preparedStatement;
       String sql="INSERT INTO students_details(first_name,second_name,last_name,county_of_resident,gender,date_of_birth,admission_number," +
               "date_of_admission,student_type,form,dorm,time_of_admission,stream,term) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,secondName);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,County);
            preparedStatement.setString(5,Gender);
            preparedStatement.setDate(6, Date.valueOf(birthDate));
            preparedStatement.setInt(7,admissionNumber);
            preparedStatement.setDate(8, Date.valueOf(dateOfAdmission));
            preparedStatement.setString(9,StudentType);
            preparedStatement.setInt(10,Form);
            preparedStatement.setString(11,Dorm);
            preparedStatement.setTime(12, Time.valueOf(time));
           preparedStatement.setString(13,stream);
           preparedStatement.setInt(14,term);

              int a= preparedStatement.executeUpdate();

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Details entered successfully"); //message
            alert.showAndWait();

    }

    //This method gets the last admission number from database
    public int getLastAdmissionNumber(Connection connection) throws SQLException {
        int value = 0;
        Statement myState = connection.createStatement();
        ResultSet myResult = myState.executeQuery("select max(admission_number) from students_details ");
        while (myResult.next()) {
            value = myResult.getInt("max(admission_number)");
        }
        return value;
    }

    //this method queries the database for all the student details
    public ResultSet getAllStudentDetails(Connection connection) throws SQLException {
        Statement myState=connection.createStatement();
        ResultSet rs= myState.executeQuery("SELECT * FROM students_details ");
        return rs;
     }

    //this method queries the database for all the student details for a specific form
    public ResultSet loadsForms(int form,Connection connection) throws SQLException {
       String sql="SELECT * FROM students_details WHERE form=? ;";

       PreparedStatement preparedStatement=connection.prepareStatement(sql);
       preparedStatement.setInt(1, form);
        ResultSet rs= preparedStatement.executeQuery();
        return rs;
    }

    //this method queries the database for all the students details for a specific stream
    public ResultSet loadsStream(int form,String stream,Connection connection) throws SQLException {
        String sql="SELECT * FROM students_details WHERE form=? AND stream=?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,form);
        ps.setString(2,stream);
        return ps.executeQuery();
    }

  //used for searching the database with admission number
    public ResultSet searchTable(int admissionNumber,Connection connection) throws SQLException {
        String sql="SELECT * FROM students_details where admission_number=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,admissionNumber);
        ResultSet rs= preparedStatement.executeQuery();
        return rs;
    }

    //this method queries the database for all the guardian detail according to form
    public ResultSet loadStudentsName(int admissionNumber,Connection connection) throws SQLException{
        String sql="SELECT guardian_details.guardian_first_name,guardian_details.guardian_id,guardian_details.guardian_last_name," +
                "guardian_details.guardian_phone_number,students_details.first_name,students_details.second_name,students_details.last_name," +
                "guardian_details.guardian_email,guardian_details.admission_number" +
                " FROM guardian_details INNER JOIN students_details ON " +
                "guardian_details.admission_number=students_details.admission_number " +
                "WHERE students_details.admission_number=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1,admissionNumber);
        ResultSet rs=preparedStatement.executeQuery();
        return rs;
    }

    //this method is used to delete student records from database
    public void deleteStudentRecord(int admissionNumber,Connection connection) throws SQLException {
        String sql="DELETE students_details , guardian_details  FROM students_details  INNER JOIN guardian_details " +
                "WHERE students_details.admission_number= guardian_details.admission_number and students_details.admission_number = ?";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,admissionNumber);
        ps.executeUpdate();
    }

     //this method is used to save  student details
    public void UpdateStudentDetails(int admission,String firstName,String SecondName,String LastName
                    ,String county,String gender,LocalDate BirthDate,LocalDate AdmissionDate,String StudentType
                     ,int form,String Dorm,LocalTime AdmissionTime,String Stream,int term)
    {
        String sql="UPDATE students_details SET first_name=?,second_name=?,last_name=?,county_of_resident=?," +
                "gender=?,date_of_birth=?,admission_number=?,date_of_admission=?," +
                "student_type=?,form=?,dorm=?,time_of_admission=?,stream=?,term=? WHERE students_details.admission_number=?";
        PreparedStatement ps= null;
        Connection connection=DBConnector.getConnection();
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,firstName);
            ps.setString(2,SecondName);
            ps.setString(4,county);
            ps.setString(5,gender);
            ps.setString(3,LastName);
            ps.setDate(6,Date.valueOf(BirthDate));
            ps.setInt(10,form);
            ps.setString(11,Dorm);
            ps.setTime(12,Time.valueOf(AdmissionTime));
            ps.setInt(7,admission);
            ps.setDate(8,Date.valueOf(AdmissionDate));
            ps.setString(9,StudentType);
            ps.setString(13,Stream);
            ps.setInt(14,term);
            ps.setInt(15,admission);
      
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
           if (connection!=null){
              try {
                 connection.close();
              } catch (SQLException e) {
                 e.printStackTrace();
              }
           }
        }
        if(ps!=null){
           try {
              ps.close();
           } catch (SQLException e) {
              e.printStackTrace();
           }
        }

    }

    //this method is used to get admission number for a particular stream and form
   public ResultSet getAlladmissionNumber(int form ,String stream,Connection connection) throws SQLException {

        ResultSet rs = null;
        String sql="SELECT admission_number FROM students_details where form=? and stream=?";
        PreparedStatement ps=connection.prepareStatement(sql);
       ps.setString(2,stream);
       ps.setInt(1,form);
        rs=ps.executeQuery();

        return rs;
   }

   //this method is used get the  term
   public ResultSet getTerm(Connection connection) throws SQLException {
        String sql="SELECT current_term FROM current_school_details";
        PreparedStatement ps1=connection.prepareStatement(sql);
      return ps1.executeQuery();
   }

   //this method is used to update the term
    public void setTerm(int term,Connection connection) throws SQLException {
        String sql="UPDATE current_school_details SET current_term=? WHERE id=1 ";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setInt(1,term);
        ps.executeUpdate();
    }


    //this method is used to get the year
    public ResultSet getYear(Connection connection) throws SQLException{
        String sql="SELECT current_year FROM  current_school_details";
        PreparedStatement ps=connection.prepareStatement(sql);
        return ps.executeQuery();
    }

    //this method is used to set the year
   public void setYear(int year,Connection connection) throws SQLException {
       String sql="UPDATE current_school_details SET current_year=? WHERE id=1";
       PreparedStatement ps=connection.prepareStatement(sql);
       ps.setInt(1,year);
       ps.executeUpdate();
   }

   //this method is used to update the students forms
   public void UpdateStudentsForm(int form,int admissionNumber,Connection connection) throws SQLException{
       String sql="UPDATE students_details SET form=? WHERE admission_number=?";
       PreparedStatement ps=connection.prepareStatement(sql);
       ps.setInt(1,form);
       ps.setInt(2,admissionNumber);
       ps.executeUpdate();
   }

   //this method is used to update the students term
   public void UpdateStudentsTerm(int term ,int admissionNo,Connection connection) throws SQLException{
    String sql="UPDATE students_details SET term=? WHERE admission_number=?";
    PreparedStatement ps=connection.prepareStatement(sql);
    ps.setInt(1,term);
    ps.setInt(2,admissionNo);
    ps.executeUpdate();
   }

}
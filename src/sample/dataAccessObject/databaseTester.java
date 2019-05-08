package sample.dataAccessObject;

import sample.dataAccessObject.admission.StudentDao;
import sample.dataAccessObject.examination.AverageMarksDao;
import sample.model.admission.ChangeTermAndYear;
import sample.model.admission.SchoolDetailsGenerator;
import sample.model.examination.CalculateAverageMarks;
import sample.model.examination.SetStudentPosition;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * THIS IS FOR TESTING DATABASE QUERIES
 * SHOULD BE REMOVED
 * */
public class databaseTester {
   public static void main(String[] args) {

      Connection connect = null;
      String table= "`users_details`";

      try {

//            DBConnector dbConnector = new DBConnector();
//            connect = dbConnector.getConnection();
//

         HikariConnector hikariConnector=new HikariConnector();
         connect=hikariConnector.getConnection();


         LocalDate ldate = LocalDate.now();
         LocalDate test = ldate.minusYears(4);
         String mtest=test.toString();
         Statement mystate = connect.createStatement();
         ResultSet myresult = mystate.executeQuery("SELECT admission_number from students_details");
         while (myresult.next()) {
            int ivalue =myresult.getInt("admission_number");
            String value= Integer.toString(ivalue);
            System.out.println(ivalue);
            System.out.println(value);
         }
      } catch (SQLException e) {
         System.out.println("Connection failed");
         System.out.println("Server Offline");
         e.printStackTrace();
      }finally {
         if(connect!=null){
            try {
               connect.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
      SchoolDetailsGenerator sdg = new SchoolDetailsGenerator();
      StudentDao dao = new StudentDao();
      System.out.println(sdg.getAdmissionNumber() + "  " + sdg.getStream() + " " + sdg.getDorm("female"));

      LocalDate date = LocalDate.now();
      DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      String formatted = date.format(DateFormat);
      System.out.println("date "+formatted);


      CalculateAverageMarks xc = new CalculateAverageMarks();
      System.out.println(xc.checkIfRecordsExist(1, 100, 1));
      AverageMarksDao averageMarksDao = new AverageMarksDao();
      try {
         connect=DBConnector.getConnection();
         ResultSet rs = averageMarksDao.getCurrentStudentAverageMarks(1, 1, 100,connect);
         while (rs.next()) {
            int form = rs.getInt("form");
            int term = rs.getInt("term");

         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      SetStudentPosition setStudentPosition=new SetStudentPosition();


      System.out.println("april something "+ Calendar.getInstance().get(Calendar.APRIL));
      LocalDate today=LocalDate.now();
      //  System.out.println("month\tjh\t"++ Calendar.getInstance().get(Calendar.MONTH)+1);

      ChangeTermAndYear ch=new ChangeTermAndYear();

      Date td=new Date();

      System.out.println( today.getYear());
      System.out.println( today.getMonth());
      System.out.println( td.getTime());

      ch.SetTerm();

      // System.out.println( "Term "+ch.CheckTerm());
      System.out.println();
      ch.setYear();

   }
}

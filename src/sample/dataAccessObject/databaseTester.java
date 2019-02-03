package sample.dataAccessObject;

import sample.dataAccessObject.admission.StudentDao;
import sample.model.admission.SchoolDetailsGenerator;
import sample.model.examination.CalculateAverageMarks;
import sample.model.finance.CalculateFees;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * THIS IS FOR TESTING DATABASE QUERIES
 * SHOULD BE REMOVED
 * */
public class databaseTester {
    public static void main(String[] args) {

        Connection connect;
        String table= "`users_details`";

        try {

            DBConnector dbConnector=new DBConnector();
            connect = dbConnector.getConnection();

            LocalDate ldate=LocalDate.now();
           LocalDate test= ldate.minusYears(4);
            Statement mystate = connect.createStatement();
            ResultSet myresult = mystate.executeQuery("SELECT admission_number from students_details where date_of_birth >= test;");
            while (myresult.next()) {
                int ivalue =myresult.getInt("admission_number");
                // String value= Integer.toString(ivalue);
                System.out.println(ivalue);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            System.out.println("Server Offline");
            e.printStackTrace();
        }
        SchoolDetailsGenerator sdg=new SchoolDetailsGenerator();
        StudentDao dao=new StudentDao();
        System.out.println( sdg.getAdmissionNumber()+"  " + sdg.getStream()+" "+ sdg.getDorm("female") );

        LocalDate date=LocalDate.now();
        DateTimeFormatter DateFormat =DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formatted= date.format(DateFormat);
  System.out.println(formatted);

//        CalculateFees calculateFeesBalance=new CalculateFees();
//        System.out.println(calculateFeesBalance.getExpectedFees(104));
//        System.out.println(calculateFeesBalance.setExpectedFees(104));
//        System.out.println(calculateFeesBalance.CalculateBalance(5000,104));
//        System.out.println(date.minusYears(4));
        CalculateAverageMarks xc=new CalculateAverageMarks();
       System.out.println(xc.checkIfRecordsExist(1,100,1));
    }
}

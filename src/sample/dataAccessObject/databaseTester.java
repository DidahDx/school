package sample.dataAccessObject;

import sample.dataAccessObject.admission.StudentDao;
import sample.dataAccessObject.examination.AverageMarksDao;
import sample.model.admission.SchoolDetailsGenerator;
import sample.model.examination.CalculateAverageMarks;
import sample.model.examination.GradingSubjects;
import sample.model.examination.SetStudentPosition;
import sample.model.finance.CalculateFees;
import sample.model.user.GenerateRandomPassword;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.sun.javafx.binding.StringFormatter.concat;

/**
 * THIS IS FOR TESTING DATABASE QUERIES
 * SHOULD BE REMOVED
 * */
public class databaseTester {
    public static void main(String[] args) {

        Connection connect;
        String table= "`users_details`";

//        try {

            DBConnector dbConnector = new DBConnector();
            connect = dbConnector.getConnection();

            LocalDate ldate = LocalDate.now();
            LocalDate test = ldate.minusYears(4);
//            Statement mystate = connect.createStatement();
//            ResultSet myresult = mystate.executeQuery("SELECT admission_number from students_details where date_of_birth >= test;");
//            while (myresult.next()) {
//                int ivalue =myresult.getInt("admission_number");
//                // String value= Integer.toString(ivalue);
//                System.out.println(ivalue);
//            }
//        } catch (SQLException e) {
//            System.out.println("Connection failed");
//            System.out.println("Server Offline");
//            e.printStackTrace();
//        }
            SchoolDetailsGenerator sdg = new SchoolDetailsGenerator();
            StudentDao dao = new StudentDao();
            System.out.println(sdg.getAdmissionNumber() + "  " + sdg.getStream() + " " + sdg.getDorm("female"));

            LocalDate date = LocalDate.now();
            DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formatted = date.format(DateFormat);
            System.out.println(formatted);


            CalculateAverageMarks xc = new CalculateAverageMarks();
            System.out.println(xc.checkIfRecordsExist(1, 100, 1));
            AverageMarksDao averageMarksDao = new AverageMarksDao();
            try {
                ResultSet rs = averageMarksDao.getCurrentStudentAveragemMarks(1, 1, 100);
                while (rs.next()) {
                    int form = rs.getInt("form");
                    int term = rs.getInt("term");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        SetStudentPosition setStudentPosition=new SetStudentPosition();
            setStudentPosition.setOverallPositionTie(1,1);

        System.out.println( Calendar.getInstance().get(Calendar.APRIL));

           System.out.println( Calendar.getInstance().get(Calendar.MONTH));


    }
}

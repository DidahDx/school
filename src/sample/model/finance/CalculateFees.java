package sample.model.finance;

import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.admission.StudentDao;
import sample.dataAccessObject.finance.FeeDao;
import sample.dataAccessObject.finance.SetFeesDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalculateFees {

    FeeDao feeDao=new FeeDao();
    StudentDao studentDao=new StudentDao();
    SetFeesDao setFeesDao=new SetFeesDao();
    int sform = 0; String studentType="";
    int sterm = 0;

    //this method get the expected fees of the student
   public Double getExpectedFees(int admissionNumber){

       Double feeExpected=0.0;
      Connection connection= DBConnector.getConnection();
       try {
           ResultSet rs1= studentDao.searchTable(admissionNumber,connection);

           while(rs1.next()){

               sform= rs1.getInt("form");
               sterm=rs1.getInt("term");
               studentType=rs1.getString("student_type");

           }

           ResultSet rs2=setFeesDao.ViewFees(sform,connection);

           while(rs2.next()){

               //this checks the student type and form and sets the fees accordingly
               if (studentType.matches("Boarder")) {
                   if (sterm == 1) {
                       feeExpected = rs2.getDouble("boarder_term_one");
                   } else if (sterm == 2) {
                       feeExpected = rs2.getDouble("boarder_term_two");
                   } else if (sterm == 3) {
                       feeExpected = rs2.getDouble("boarder_term_three");
                   }

               }else if (studentType.matches("Day Scholar")){
                   if (sterm == 1) {
                       feeExpected = rs2.getDouble("term_one");
                   } else if (sterm == 2) {
                       feeExpected = rs2.getDouble("term_two");
                   } else if (sterm == 3) {
                       feeExpected = rs2.getDouble("term_three");
                   }
               }
           }

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

       return feeExpected;
   }

   //this method checks if the expected fees is set and returns a true or false
   public boolean setExpectedFees(int admissionNumber){
       int form=0,checkForm=5;
       int term=0,checkTerm=5;
       boolean check=false;
       Connection connection=DBConnector.getConnection();
       try {

           //this is used to check which term and form the student is current in
         ResultSet rs2= studentDao.searchTable(admissionNumber,connection);
         while(rs2.next()){
             checkForm=rs2.getInt("form");
             checkTerm=rs2.getInt("term");
         }

         //this is used to check if the student has paid any fee in the current term
         ResultSet rs= feeDao.searchForCheckingFees(admissionNumber,checkForm,checkTerm,connection);
           while(rs.next()){
               form=rs.getInt("form");
               term=rs.getInt("term");
           }

         if (!((form==checkForm) && (term==checkTerm))){
             check=true;
         }else{
             check=false;
         }

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

       return check;
   }


   //this method calculates the balance
   public double CalculateBalance(double amountPaid,int admissionNumber){
       double balance=0.0;
        double feeExpected=0.0;
          Connection connection=DBConnector.getConnection();
        //this checks if the expected fees for the current term is set or not
       if(setExpectedFees(admissionNumber)){
           feeExpected=getExpectedFees(admissionNumber);
       }

       try {
           ResultSet rs=feeDao.getBalance(admissionNumber,connection);
           while (rs.next()){
             balance= rs.getDouble("balance");    //getting cuurent balance
           }
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

       balance=balance+feeExpected;
       balance=balance-amountPaid;

       return balance;
   }

}

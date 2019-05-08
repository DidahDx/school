package sample.model.admission;

import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.admission.StudentDao;
import sun.security.pkcs11.Secmod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class ChangeTermAndYear {

StudentDao stDao=new StudentDao();

    //this method is used to check the current term
    private int CheckTerm(){
        int term = 0;
       int month=Calendar.getInstance().get(Calendar.MONTH) +1;

      if ((month>=1)&& (month<=4) )
      {
          term=1;
      }else if ((month>=5)&& (month<=8) )
      {
          term=2;
      }else if ((month>=9)&& (month<=12) )
      {
          term=3;
      }

      return term;
    }


    //this method is used to set the term
    public void SetTerm(){
        int currentTerm=CheckTerm();
        int DBterm = 0;
      Connection connection=DBConnector.getConnection();
        try {
         ResultSet rs= stDao.getTerm(connection);
         while (rs.next()){
             DBterm=rs.getInt("current_term"); //getting the term stored in database
         }


        if (currentTerm>DBterm){
            if (currentTerm!=DBterm){
                 //updates term to currentTerm
                try {
                    stDao.setTerm(currentTerm,connection);
                    ChangeStudentTerm(connection);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else if((currentTerm==1) && (DBterm==3)){
                //updates term to currentTerm
            try {
                stDao.setTerm(currentTerm,connection);
                ChangeStudentTerm(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }
        catch (SQLException e) {
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
    }

    //this method is used to check the current year
    private int CheckYear(){

        LocalDate today=LocalDate.now();
        return today.getYear();
    }

    //this method is used to set the current year
    public void setYear() {
       int CurrentYear = CheckYear();
       int DBYear = 0;
       Connection connection=DBConnector.getConnection();
       try {
          ResultSet rs = stDao.getYear(connection);
          while (rs.next()) {
             DBYear = rs.getInt("current_year"); //getting the year in the database
          }


       if (CurrentYear > DBYear) {
          if (CurrentYear != DBYear) {
             try {
                stDao.setYear(CurrentYear,connection);  //changing year in database to current year
                ChangeStudentForm(connection);
             } catch (SQLException e) {
                e.printStackTrace();
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

    }

    //this method is used to change the students form
   private void ChangeStudentForm(Connection connection){
      int adminNo=0,form=0;

      try {
         ResultSet rs=stDao.getAllStudentDetails(connection);

         while(rs.next()){
            form=rs.getInt("form");
            adminNo=rs.getInt("admission_number");

            if (form!=0 && adminNo!=0){
               form++;
               stDao.UpdateStudentsForm(form,adminNo,connection);
            }
         }

      } catch (SQLException e) {
         e.printStackTrace();
      }

   }

    //this method is used to change the students term
    public void ChangeStudentTerm(Connection connection){
       int adminNo=0,term=0;

       try {
          ResultSet rs=stDao.getAllStudentDetails(connection);
          ResultSet rs1=stDao.getTerm(connection);
          while (rs1.next()){
             term=rs1.getInt("current_term"); //gets current term from database
          }

          while(rs.next()){
             adminNo=rs.getInt("admission_number");

             if (term!=0 && adminNo!=0){
                stDao.UpdateStudentsTerm(term,adminNo,connection);
             }
          }
       } catch (SQLException e) {
          e.printStackTrace();
       }
    }

}
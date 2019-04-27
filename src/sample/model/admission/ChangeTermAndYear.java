package sample.model.admission;

import sample.dataAccessObject.admission.StudentDao;
import sun.security.pkcs11.Secmod;

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

        try {
         ResultSet rs= stDao.getTerm();
         while (rs.next()){
             DBterm=rs.getInt("current_term"); //getting the term stored in database
         }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (currentTerm>DBterm){
            if (currentTerm!=DBterm){
                 //updates term to currentTerm
                try {
                    stDao.setTerm(currentTerm);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else if((currentTerm==1) && (DBterm==3)){
                //updates term to currentTerm
            try {
                stDao.setTerm(currentTerm);
            } catch (SQLException e) {
                e.printStackTrace();
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
       try {
          ResultSet rs = stDao.getYear();
          while (rs.next()) {
             DBYear = rs.getInt("current_year"); //getting the year in the database
          }
       } catch (SQLException e) {
          e.printStackTrace();
       }

       if (CurrentYear > DBYear) {
          if (CurrentYear != DBYear) {
             try {
                stDao.setYear(CurrentYear);  //changing year in database to current year
                ChangeStudentForm();
             } catch (SQLException e) {
                e.printStackTrace();
             }
          }
       }
    }

    //this method is used to change the students form
   private void ChangeStudentForm(){
      int adminNo=0,form=0;
      try {
         ResultSet rs=stDao.getAllStudentDetails();

         while(rs.next()){
            form=rs.getInt("form");
            adminNo=rs.getInt("admission_number");

            if (form!=0 && adminNo!=0){
               form++;
               stDao.UpdateStudentsForm(form,adminNo);
            }
         }

      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

    //this method is used to change the students term
    public void ChangeStudentTerm(){
       int adminNo=0,term=0;
       try {
          ResultSet rs=stDao.getAllStudentDetails();
          ResultSet rs1=stDao.getTerm();
          while (rs1.next()){
             term=rs1.getInt("current_term"); //gets current term from database
          }

          while(rs.next()){
             adminNo=rs.getInt("admission_number");

             if (term!=0 && adminNo!=0){
                stDao.UpdateStudentsTerm(term,adminNo);
             }
          }
       } catch (SQLException e) {
          e.printStackTrace();
       }
    }

}
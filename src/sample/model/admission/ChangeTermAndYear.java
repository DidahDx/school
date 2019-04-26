package sample.model.admission;

import sample.dataAccessObject.admission.StudentDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class ChangeTermAndYear {

StudentDao stDao=new StudentDao();

    //this method is used to check the current term
    public int CheckTerm(){
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
             DBterm=rs.getInt("current_term");
         }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (currentTerm>DBterm){
            if (!(currentTerm==DBterm)){
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
    public int CheckYear(){
        int year;

        LocalDate today=LocalDate.now();
        year=today.getYear();

        return  year;
    }

/** TODO: COMPLETE WRITING CHANGE STUDENT FORM*/
    //this method is used to change the students form
    public void ChangeStudentForm(){

    }

}

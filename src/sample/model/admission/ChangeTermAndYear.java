package sample.model.admission;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class ChangeTermAndYear {

    LocalDate today=LocalDate.now();

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


    public void SetTerm(){

    }

}

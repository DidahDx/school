package sample.model.admission;

import sample.dataAccessObject.admission.StudentDao;
import java.sql.SQLException;
import java.util.Random;

/**
 * THIS CLASS IS USED TO GENERATE ADMISSION NUMBER TO NEW STUDENTS,
 * ASSIGN A STUDENT A CLASSROOM AND
 * A DORM ROOM WHERE NECESSARY
 *
 */
public class SchoolDetailsGenerator {

private StudentDao dataAccessObject =new StudentDao();
    private Random random=new Random();

  //this method generates and return the new student's admission number
    public int getAdmissionNumber(){
    int lastAdmissionNumber = 0;
    try {
      lastAdmissionNumber= dataAccessObject.getLastAdmissionNumber();
    } catch (SQLException e) {
        e.printStackTrace();
    }

        return lastAdmissionNumber + 1;
}

   //this method generates and returns the stream the student is placed in
    public String getStream(){
        String stream;

        int randomNumber= random.nextInt(2);                //generates a random number

        if(randomNumber==1){
            stream="EAST";
        }else{
            stream="WEST";
        }

        return stream;
    }

    //this method generates and returns the Dorm the student is placed in
    public String getDorm(String gender){
        String dorm = null;
        int randomDorm=random.nextInt(2);

        if(gender.matches("male")){
            switch(randomDorm) {
                case 0:
                    dorm = "Elgon";
                    break;
                case 1:
                    dorm = "Kenya";
                    break;
            }

        }else if (gender.matches("female")){
            switch(randomDorm) {

                case 0:
                    dorm="Kilimanjaro";
                    break;
                case 1:
                    dorm="Longonot";
                    break;
            }
        }

       return dorm;
    }
}
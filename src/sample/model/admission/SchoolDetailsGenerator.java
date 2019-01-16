package sample.model.admission;

import sample.database.DataAccessObject;
import java.sql.SQLException;
import java.util.Random;

/**
 * THIS CLASS IS USED TO GENERATE ADMISSION NUMBER TO NEW STUDENTS,
 * ASSIGN A STUDENT A CLASSROOM AND
 * A DORM ROOM WHERE NECESSARY
 *
 */
public class SchoolDetailsGenerator {

private DataAccessObject dataAccessObject =new DataAccessObject();
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
    public String getDorm(){
        String dorm;
        int randomDorm=random.nextInt(4);

        switch(randomDorm) {
            case 0:
                dorm="Elgon";
                break;
            case 1:
                dorm="Kenya";
                break;
            case 2:
                dorm="Kilimanjaro";
                break;
            case 3:
                dorm="Longonot";
                break;
        default:
            dorm="Elgon";
            break;
        }

       return dorm;
    }
}
package sample.model.examination;

import sample.dataAccessObject.examination.AverageMarksDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * THIS CLASS IS USED TO SET THE STREAM AND OVERALL POSITION OF STUDENT
 * AND SETS THE TIE POSITION
 * */

public class SetStudentPosition {

    AverageMarksDao averageMarksDao=new AverageMarksDao();

    int streamPosition,averageId,total,OverallPosition;


    //this method is used to set the stream position
    public void setStreamPosition(int form,int term,String stream){
        streamPosition=0; OverallPosition=0;

        try {
            ResultSet rs= averageMarksDao.getStudentsTotalMarks(form,term,stream);
          while(rs.next()){
             averageId= rs.getInt("average_marks_id");
             total= rs.getInt("total");
             streamPosition+=1;

             averageMarksDao.setStreamPosition(averageId,streamPosition,total);
          }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setOverallPosition(form,term);
       setStreamPositionTies(form,term,stream);
        setOverallPositionTie(form, term);
    }

    //this method is used to set the overall position
    private void setOverallPosition(int form, int term){
        try {
            ResultSet rs= averageMarksDao.getTotalMarks(form, term);

            while(rs.next()){
                averageId=rs.getInt("average_marks_id");
                total= rs.getInt("total");
                OverallPosition+=1;

                averageMarksDao.setOverallPosition(averageId,OverallPosition,total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //this method  is used to tie students with same marks
    public void setStreamPositionTies(int form,int term,String stream){
        int averageId,averageId1,total,total1; int streamPosition,streamPosition1;
        try {
            ResultSet rs2= averageMarksDao.getStudentsTotalMarksSetTie(form,term,stream);

            while(rs2.next()){
                total= rs2.getInt("total");
                streamPosition=rs2.getInt("stream_position");

                ResultSet rs3= averageMarksDao.getStudentsTotalMarksSetTie(form,term,stream);
                while(rs3.next()){
                    streamPosition1=rs3.getInt("stream_position");
                    averageId1= rs3.getInt("average_marks_id");
                    total1= rs3.getInt("total");

                    if(!(streamPosition==streamPosition1)) {
                        if (total == total1 && streamPosition != streamPosition1) {
                            averageMarksDao.setStreamPosition(averageId1, streamPosition, total);
                            rs2 = averageMarksDao.getStudentsTotalMarksSetTie(form, term, stream); //gets  the new values from the database
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //this method is used to set tie position for for Each form
    public void setOverallPositionTie(int form, int term){
        try {
          int averageId1,total,total1; int OverallPosition,OverallPosition1;
            ResultSet rs2= averageMarksDao.getTotalMarksSetTies(form,term);
            while(rs2.next()){
                total= rs2.getInt("total");
                OverallPosition=rs2.getInt("overall_position");

                ResultSet rs3= averageMarksDao.getTotalMarksSetTies(form,term);
                while(rs3.next()){
                    OverallPosition1=rs3.getInt("overall_position");
                    averageId1= rs3.getInt("average_marks_id");
                    total1= rs3.getInt("total");

                    if(!(OverallPosition==OverallPosition1)) {
                        if (total == total1 && OverallPosition != OverallPosition1) {
                            averageMarksDao.setOverallPosition(averageId1, OverallPosition, total);
                            rs2 = averageMarksDao.getTotalMarksSetTies(form, term);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

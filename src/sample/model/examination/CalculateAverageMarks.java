package sample.model.examination;

import sample.dataAccessObject.examination.AverageMarksDao;
import sample.dataAccessObject.examination.CatMarkDao;
import sample.dataAccessObject.examination.EndTermDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * CLASS IS USED TO CALCULATE THE AVERAGE MARKS
 * OF THE STUDENTS
 * */
public class CalculateAverageMarks {

    int Maths,English,Kiswahili,Biology,Physics,
    Chemistry,History,Geography,Cre,BusinessStudies,ComputerStudies,agriculture;
    int Maths1,English1,Kiswahili1,Biology1,Physics1,
            Chemistry1,History1,Geography1,Cre1,BusinessStudies1,ComputerStudies1,agriculture1;
    int averageMarksId;
    CatMarkDao cM=new CatMarkDao();
    EndTermDao Ed=new EndTermDao();
    AverageMarksDao averageMarksDao=new AverageMarksDao();
    EndTermDao endTermDao=new EndTermDao();

    public void AddCatAndEndTermMark(int form,int admissionNumber,int term){

        LocalDate today=LocalDate.now();
        LocalTime now=LocalTime.now();

        try {
           ResultSet rs= cM.getCurrentStudentMarks(form,term,admissionNumber);

           while(rs.next()){
               Maths=rs.getInt("maths");
               English =rs.getInt("english");
                       Kiswahili=rs.getInt("kiswahili");
                       Biology=rs.getInt("biology");
                       Physics=rs.getInt("physics");
                       Chemistry=rs.getInt("chemistry");
                       History=rs.getInt("history");
                       Geography=rs.getInt("geography");
                       Cre=rs.getInt("cre");
                       BusinessStudies=rs.getInt("business_studies");
                       ComputerStudies=rs.getInt("computer_studies");
                       agriculture=rs.getInt("agriculture");
           }
            ResultSet rs1= Ed.getCurrentStudentEndTermMarks(form,term,admissionNumber);

            while(rs1.next()){
                Maths1=rs1.getInt("maths");
                Kiswahili1=rs1.getInt("kiswahili");
                English1 =rs1.getInt("english");
                Biology1=rs1.getInt("biology");
                Physics1=rs1.getInt("physics");
                History1=rs1.getInt("history");
                Chemistry1=rs1.getInt("chemistry");
                Geography1=rs1.getInt("geography");
                Cre1=rs1.getInt("cre");
                BusinessStudies1=rs1.getInt("business_studies");
                agriculture1=rs1.getInt("agriculture");
                ComputerStudies1=rs1.getInt("computer_studies");
            }

            int nMath=Maths+Maths1;
            int nPhysics=Physics+Physics1;
            int nCre=Cre+Cre1;
            int nHistory=History+History1;
            int nKiswahili=Kiswahili+Kiswahili1;
            int nBiology=Biology+Biology1;
            int nChemistry=Chemistry+Chemistry1;
            int nEnglish=English+English1;
            int nGeography=Geography+Geography1;
            int nComputer=ComputerStudies+ComputerStudies1;
            int nAgriculture=agriculture+agriculture1;
            int nBusiness =BusinessStudies+BusinessStudies1;

            int total=nMath+nEnglish+nKiswahili+nBiology+nPhysics+nChemistry+nHistory+
                    nGeography +nCre+nBusiness+nComputer+nAgriculture;


            if (checkIfRecordsExist(form,admissionNumber,term)){
                averageMarksDao.InsertAverageMarks(admissionNumber,nMath,nEnglish,nKiswahili, nBiology,nPhysics,nChemistry,nHistory,
                      nGeography ,nCre,nBusiness,nComputer,nAgriculture,form,term,today,now,total);
            }else{
                averageMarksDao.UpdateAverageMarks(admissionNumber,nMath,nEnglish,nKiswahili, nBiology,nPhysics,nChemistry,nHistory,
                        nGeography ,nCre,nBusiness,nComputer,nAgriculture,form,term,today,now,total);
            }

            if(checkRecords(form,admissionNumber,term)){
                averageMarksDao.DeleteAvearageMarks(averageMarksId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to check if the records exist in the average mark table in the database
    public boolean checkIfRecordsExist(int form,int admissionNumber,int term){
        boolean check=false; int sform = 0,sterm = 0;
        try {
           ResultSet rs= averageMarksDao.getCurrentStudentAveragemMarks(form,term,admissionNumber);
           while(rs.next()){
               sform=rs.getInt("form");
               sterm=rs.getInt("term");
               averageMarksId =rs.getInt("average_marks_id");
           }
            if (sform==0 && sterm==0){
                check=true;
            }else{
                check=false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return check;
    }

    //this method check if any records exist in the cat and endTerm tables  in the database
    public boolean checkRecords(int form,int admissionNumber,int term){
        int sform=0,sform1=0,mTerm=0,mTerm1=0;boolean check=false;
        try {
            ResultSet rs=cM.getCurrentStudentMarks(form,term,admissionNumber);
            while(rs.next()){
                sform=rs.getInt("form");
                mTerm=rs.getInt("term");
            }
            ResultSet  rs2= Ed.getCurrentStudentEndTermMarks(form,term,admissionNumber);
            while(rs2.next()){
                sform1=rs2.getInt("form");
                mTerm1=rs2.getInt("term");
            }

            if(sform==0 && sform1==0 && mTerm==0 && mTerm1==0){
                check=true;
            }else{
                check=false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return check;
    }

}

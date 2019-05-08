package sample.controllers.examination;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.controllers.finance.FeesPayment;
import sample.dataAccessObject.DBConnector;
import sample.model.Validation;
import sample.dataAccessObject.admission.GuardianDao;
import sample.dataAccessObject.admission.StudentDao;
import sample.dataAccessObject.examination.AverageMarksDao;
import sample.model.MyPrinter;
import sample.model.SendEmail;
import sample.model.examination.GradingSubjects;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.ResourceBundle;

public class ReportCard implements Initializable {
   public TextArea textArea;
   public JFXTextField AdmissionNumber;
   public JFXComboBox form;
   public JFXComboBox term;

   private MyPrinter printer=new MyPrinter();
   private Validation validation=new Validation();
   private ObservableList listOfForms= FXCollections.observableArrayList();
   private ObservableList listOfTerms=FXCollections.observableArrayList();
   StudentDao studentDao=new StudentDao();
   GradingSubjects gradingSubjects =new GradingSubjects();
   FeesPayment test=new FeesPayment();
   String content=" ";

   int Maths;
   int English;
   int Kiswahili;
   int Biology;
   int Physics;
   int Chemistry;int History;int Geography;int Cre;
   int BusinessStudies;int ComputerStudies;int agriculture;
   int streamPosition;
   int OverallPosition,tform,totalPoints;
   String emailAddress = null;

   private String name="name",grade,stream;
   AverageMarksDao averageMarksDao=new AverageMarksDao();
   int tTotal;
   String[]cform,cterm;
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      fillForm(form);
      fillTerm(term);
   }

   //this method is used to fill the term combobox
   private void fillTerm(ComboBox term)
   {
      listOfTerms.removeAll(listOfTerms);
   String g[]={"1","2","3"};

      Collections.addAll(listOfTerms,g);
      term.getItems().addAll(listOfTerms);
   }

   //this method is used to fill the form comboBox
   private void fillForm(ComboBox form){
      listOfForms.removeAll(listOfForms);

     String w[]={"1","2","3","4"};
      Collections.addAll(listOfForms,w);
      form.getItems().addAll(listOfForms);
   }

   //this method is used to get the report card of a student
   public void submit(ActionEvent actionEvent) {
      if (test.CheckIfStudentExist(AdmissionNumber)) {
         if (CheckIfStudentHasAverageMarks(AdmissionNumber)) {
            textArea.clear();
            content = " ";
            textArea.appendText(Content(getExamDetails()));
         }else{
            Alert alert2=new Alert(Alert.AlertType.WARNING);
            alert2.setHeaderText(null);
            alert2.setContentText("The student does have any average marks");
            alert2.showAndWait();
         }
      }else{
         Alert alert2=new Alert(Alert.AlertType.WARNING);
         alert2.setHeaderText(null);
         alert2.setContentText("There is no student with Admission Number "+ AdmissionNumber.getText());
         alert2.showAndWait();
      }
   }

   //this method is used to print the report
   public void print(ActionEvent actionEvent) {
      printer.Print(textArea);

   getEmail();
      new SendEmail().sendEmailMessage( "REPORT CARD",textArea.getText(), emailAddress);
   }

   //this method is used to set the content of the report card
   private String Content(String subject){
      LocalDate today=LocalDate.now();
      LocalTime time=LocalTime.now();
      String report="\nDATE:"+ validation.changeDateFormat(today)+"\t\t\t  SIGIRIA  SECODARY SCHOOL" +
              "\n \t\t\t\t\t\t\t P.O BOX 57-40700"+
              "\n \t\t\t\t\t\t\t MIGORI"+
              "\n\nADM.NO: "+AdmissionNumber.getText()+" \t\t\t  REPORT CARD  \t\t\t TIME: "+ time.minusNanos(time.getNano())+
              "\n-------------------------------------------------------------------------------------------------------------"+
              "\n\n  STUDENT NAME: "+name.toUpperCase()+"\t FORM: "+ form.getValue() +" "+ stream+
              "\n\n  SUBJECT \t\t\t \tMARKS \t\t GRADE \t\t POINTS \n"+
              subject+
              "\n--------------------------------------------------------------------------------------------------------------"+
              "\n  MEAN GRADE:\t"+ grade+"\t\tTOTAL POINTS:\t"+totalPoints+"\t\t TOTAL MARKS: "+tTotal+
              "\n  STREAM POSITION: "+streamPosition+"\t\t OVERALL POSITION:"+OverallPosition+
              "\n-------------------------------------------------------------------------------------------------------\n"+
              "\nCLASS TEACHER REMARKS:_______________________________________________________" +
              "\n\n PRINCIPAL REMARKS:__________________________________________________________________\n\n";


      return report;
   }

   //gets the exam details from database
   public String getExamDetails(){
      name="";
      AverageMarksDao averageMarksDao=new AverageMarksDao();
      Connection connection=DBConnector.getConnection();
      try {
         ResultSet rs= averageMarksDao.getCurrentStudentAverageMarks(Integer.parseInt(form.getValue().toString())
                 ,Integer.parseInt(term.getValue().toString()),Integer.parseInt(AdmissionNumber.getText().trim()),connection);
         ResultSet rs2=studentDao.searchTable(Integer.parseInt(AdmissionNumber.getText().trim()),connection);
         while(rs2.next()){
            name=rs2.getString("first_name");
            name+="  ";
            name+=rs2.getString("second_name");
            name+="  ";
            name+=rs2.getString("last_name");

         }

         while(rs.next()){
            History= rs.getInt("history");
            stream=rs.getString("stream");
            English= rs.getInt("english");
            Kiswahili= rs.getInt("kiswahili");
            Biology= rs.getInt("biology");
            Physics= rs.getInt("physics");
            Maths= rs.getInt("maths");
            Chemistry= rs.getInt("chemistry");
            Geography= rs.getInt("geography");
            BusinessStudies= rs.getInt("business_studies");
            ComputerStudies= rs.getInt("computer_studies");
            Cre= rs.getInt("cre");
            agriculture= rs.getInt("agriculture");
            tTotal=rs.getInt("total");
            streamPosition=rs.getInt("stream_position");
            OverallPosition=rs.getInt("overall_position");
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

      int[] marks={Maths,English,Kiswahili,Biology,Physics,Chemistry,History,Geography,BusinessStudies,ComputerStudies,Cre,agriculture};
      String[] subjectsNames={"MATHS              \t \t","ENGLISH              \t     ",
              "KISWAHILI             \t  ","BIOLOGY     \t          ","PHYSICS      \t        ",
              "CHEMISTRY   \t \t   ","HISTORY    \t \t\t   ","GEOGRAPHY    \t \t   ","BUSINESS STUDIES  ",
              "COMPUTER STUDIES","CRE             \t \t       ","AGRICULTURE     \t"};


      int r=0;
      for(int i=0;i<marks.length;i++){

         if (marks[i]!=0){
            content+= "\n  "+subjectsNames[i]+" \t\t "+marks[i]+" \t\t\t "+ gradingSubjects.GradeGenerator(marks[i])+" \t\t\t "+ gradingSubjects.pointsGenerator(marks[i])+" \n";
            r++;
         }
      }
      if(r==0) {

         totalPoints = tTotal / 1;
      }else{
         totalPoints=tTotal/r;
      }

      grade=gradingSubjects.GradeGenerator(totalPoints);

      return content;
   }

   //used to check if a student has any average marks
   public boolean CheckIfStudentHasAverageMarks(TextField admissionNumber){
      boolean check = false;
      int admission=0;
      Connection connection=DBConnector.getConnection();
      try {
         ResultSet rs= averageMarksDao.Search(Integer.parseInt(admissionNumber.getText().trim()),connection);
         while (rs.next()){
            admission= rs.getInt("admission_number");
         }

         if (admission==0){
            check=false;
         }else if (admission>0){
            check=true;
         }else {
            check=false;
         }

      } catch (SQLException e) {
         e.printStackTrace();
      }
      return check;
   }

   public void SendEmail(ActionEvent actionEvent) {
      getEmail();
      if (emailAddress.isEmpty() || emailAddress==null){

      new SendEmail().sendEmailMessage( "REPORT CARD",textArea.getText(), emailAddress);
   }else{
         Alert alt=new Alert(Alert.AlertType.WARNING);
         alt.setHeaderText(null);
        alt.setContentText("You have not submitted the details ");
         alt.showAndWait();
      }
   }

   private void getEmail(){
      GuardianDao gDao=new GuardianDao();
      Connection connection = DBConnector.getConnection();
      try {
         emailAddress=" ";
         ResultSet rs=gDao.searchGuardianTable(Integer.parseInt(AdmissionNumber.getText()),connection);
         while(rs.next()){
            emailAddress += rs.getString("guardian_email")+",";
         }
         emailAddress=emailAddress.substring(0,emailAddress.length()-1);
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

//   //load user
//   public void loadDetails(ActionEvent e) {
//      getDetails(cform,cterm);
//   }
//
//   private void getDetails(String [] form,String[] term) {
//      Connection connection=DBConnector.getConnection();
//      String sform = null,sterm=null;
//      try {
//
//         ResultSet rs=averageMarksDao.SearchFormAndTerm(Integer.parseInt(AdmissionNumber.getText()),connection);
//         int i=0;
//         while(rs.next()){
//            sform+=String.valueOf(rs.getInt("form"))+",";
//            sterm+=String.valueOf(rs.getInt("term"))+",";
//            i++;
//         }
//
//         sform.substring(0,sform.length()-1);
//         sterm.substring(0,sterm.length()-1);
//
//         cform=sform.split(",");
//         cterm=sterm.split(",");
//
//
//      } catch (SQLException e) {
//         e.printStackTrace();
//      }finally {
//         if (connection!=null){
//            try {
//               connection.close();
//            } catch (SQLException e) {
//               e.printStackTrace();
//            }
//         }
//      }
//
//   }


}

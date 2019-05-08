package sample.controllers.examination;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.controllers.finance.FeesPayment;
import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.admission.StudentDao;
import sample.dataAccessObject.examination.AverageMarksDao;
import sample.dataAccessObject.examination.EndTermDao;
import sample.model.Validation;
import sample.model.examination.CalculateAverageMarks;
import sample.model.examination.SetStudentPosition;
import sample.model.examination.ExamModelTable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class EndTermExams implements Initializable {


   public TableColumn<ExamModelTable,Integer> tAdmissionNumber;
   public TableColumn<ExamModelTable,Integer> tMath;
   public TableColumn<ExamModelTable,Integer> tEnglish;
   public TableColumn<ExamModelTable,Integer> tKiswahili;
   public TableColumn<ExamModelTable,Integer> tChemistry;
   public TableColumn<ExamModelTable,Integer> tPhysics;
   public TableColumn<ExamModelTable,Integer> tBiology;
   public TableColumn<ExamModelTable,Integer> tCre;
   public TableColumn<ExamModelTable,Integer> tHistory;
   public TableColumn<ExamModelTable,Integer> tGeography;
   public TableColumn<ExamModelTable,Integer> tAgriculture;
   public TableColumn<ExamModelTable,Integer> tBusinessStudies;
   public TableColumn<ExamModelTable,Integer> tComputerScience;
   public TableColumn<ExamModelTable,Integer> tForm;
   public TableColumn<ExamModelTable,Integer> tTerm;
   public TableColumn<ExamModelTable,String> tStream;
   public TableColumn<ExamModelTable,Integer> tDate;
   public TableColumn<ExamModelTable,Integer> tTime;
   public TableView<ExamModelTable> table;

   public JFXButton eAddMarks;
   public JFXButton eUpdate;
   public JFXButton eCancel;
   public JFXButton eMarksClear;
   public JFXButton eDelete;
   public JFXButton eEdit;

   public JFXTextField eAdmissionNumber;
   public JFXTextField maths;
   public JFXTextField english;
   public JFXTextField kiswahili;
   public JFXTextField chemistry;
   public JFXTextField physics;
   public JFXTextField biology;
   public JFXTextField cre;
   public JFXTextField geography;
   public JFXTextField history;
   public JFXTextField agriculture;
   public JFXTextField computerStudies;
   public JFXTextField business;

   public Label NameDisplayed;
   public ChoiceBox SearchForms;
   public TextField searchTextField;

   private CalculateAverageMarks calculateAverageMarks=new CalculateAverageMarks();
   SetStudentPosition setStudentPosition =new SetStudentPosition();
   private EndTermDao endTermDao=new EndTermDao();
  FeesPayment test=new FeesPayment();
   ObservableList tableList=FXCollections.observableArrayList();
   private String name="",second="";
   private int endTermId=0;
   private StudentDao stDao=new StudentDao();
   private CatMarks catMarks=new CatMarks();
   Validation validate=new Validation();
   AverageMarksDao averageMarksDao=new AverageMarksDao();
   CalculateAverageMarks cav=new CalculateAverageMarks();

   int form=0,term=0,admissionNumber=0,averageMarksId=0; String stream="";


   @Override
   public void initialize(URL location, ResourceBundle resources) {
      ForButtonVisibility(true);
      loadTable();
      catMarks.fillChoiceBox(SearchForms);
      SearchForms.setValue("All");
   }

   //this is used to search for End Term Marks with admission Number
   public void search(ActionEvent actionEvent) {
      Connection connection= DBConnector.getConnection();
      try {
         if (searchTextField.getText().isEmpty()) {
            fillTable(endTermDao.ReadEndTermMarks(connection));
         }
         else{
            fillTable(endTermDao.Search(Integer.parseInt(searchTextField.getText()),connection));
         }
      }catch (SQLException e) {
         e.printStackTrace();
      }finally {
         if(connection!=null){
            try {
               connection.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
   }

   //this method loads the table view with all the end term marks per Form
   public void loadFormTable(ActionEvent actionEvent) {
      Connection connection=DBConnector.getConnection();
      ResultSet rs = null;
      try {
         if (SearchForms.getValue().toString().matches("All")){
            loadTable();
         }else{
            rs=endTermDao.SearchForm(Integer.parseInt(SearchForms.getValue().toString()),connection);
            fillTable(rs);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         if(connection!=null){
            try {
               connection.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
   }

   //this method loads the table View with all the end Term
   private void loadTable(){
      Connection connection= DBConnector.getConnection();
      try {
         fillTable(endTermDao.ReadEndTermMarks(connection));
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         if(connection!=null){
            try {
               connection.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
   }

   //this method is used to hide add Marks button and delete button when edit button is pressed
   public void editable(ActionEvent actionEvent) {
      ForButtonVisibility(false);
      eAddMarks.setVisible(false);
   }

   //this method is used to delete End Term Marks
   public void delete(ActionEvent actionEvent) {
      Connection connection= DBConnector.getConnection();
      try {

         ResultSet rs7= stDao.searchTable(Integer.parseInt(eAdmissionNumber.getText()),connection);
         while(rs7.next()){
            stream=rs7.getString("stream");
         }

         Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
         alert.setContentText(Content("DELETE"));
         alert.setHeaderText(null);
         Optional<ButtonType> answer=alert.showAndWait();

         if (answer.get()==ButtonType.OK) {
            endTermDao.DeleteEndTermMarks(endTermId,connection);
            loadTable();

            calculateAverageMarks.AddCatAndEndTermMark(form, Integer.parseInt(eAdmissionNumber.getText()), term, stream);

            setStudentPosition.setStreamPosition(form,term,stream,connection);

//               if(cav.checkRecords(form,admissionNumber,term)){
//                  averageMarksDao.DeleteAvearageMarks(averageMarksId); //used to delete data in the average marks table
//               }

         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         if(connection!=null){
            try {
               connection.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
   }

   public void Clear(ActionEvent actionEvent) {
      ForButtonVisibility(false);
      eUpdate.setVisible(false);
      biology.setText(null);
      history.setText(null);
      chemistry.setText(null);
      english.setText(null);
      kiswahili.setText(null);
      physics.setText(null);
      cre.setText(null);
      maths.setText(null);
      agriculture.setText(null);
      eAdmissionNumber.setText(null);
      business.setText(null);
      geography.setText(null);
      computerStudies.setText(null);
   }

   public void cancel(ActionEvent actionEvent) {
      ForButtonVisibility(true);
   }

   //this method is used update end term marks
   public void update(ActionEvent actionEvent) {
      Connection connection= DBConnector.getConnection();
      if (test.CheckIfStudentExist(eAdmissionNumber)) {
         if (CheckAllFields()) {
            try {
               int form = 0, term = 0;
               ResultSet rs = stDao.searchTable(Integer.parseInt(eAdmissionNumber.getText()), connection);
               while (rs.next()) {
                  term = rs.getInt("term");
                  name = rs.getString("first_name");
                  second = rs.getString("second_name");
                  form = rs.getInt("form");
                  stream = rs.getString("stream");
               }
               LocalTime now = LocalTime.now(); //gets current time
               LocalDate today = LocalDate.now(); //gets current date

               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setContentText(Content("UPDATE"));
               alert.setHeaderText(null);
               Optional<ButtonType> answer = alert.showAndWait();

               if (answer.get() == ButtonType.OK) {
                  endTermDao.UpdateEndTermMarks(Integer.parseInt(eAdmissionNumber.getText()), Integer.parseInt(maths.getText()), Integer.parseInt(english.getText()),
                          Integer.parseInt(kiswahili.getText()), Integer.parseInt(biology.getText()), Integer.parseInt(physics.getText()), Integer.parseInt(chemistry.getText()),
                          Integer.parseInt(history.getText()), Integer.parseInt(geography.getText()), Integer.parseInt(cre.getText()),
                          Integer.parseInt(business.getText()), Integer.parseInt(computerStudies.getText()), Integer.parseInt(agriculture.getText()),
                          form, term, endTermId, now, today, stream, connection);

                  calculateAverageMarks.AddCatAndEndTermMark(form, Integer.parseInt(eAdmissionNumber.getText()), term, stream);
                  setStudentPosition.setStreamPosition(form, term, stream, connection);
                  loadTable();

               }
               ForButtonVisibility(true);
               loadTable();
            } catch (SQLException e) {
               e.printStackTrace();
            } finally {
               if (connection != null) {
                  try {
                     connection.close();
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               }
            }
         } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("All fields in red have invalid inputs. Try  Again");
            alert.showAndWait();
         }
      }else{
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setHeaderText(null);
         alert.setContentText("There is no student with that admission Number");
         alert.showAndWait();
      }
   }

   //this method inserts the end Term Marks to the database
   public void AddMarks(ActionEvent actionEvent)
   {
      Connection connection= DBConnector.getConnection();
      if (test.CheckIfStudentExist(eAdmissionNumber)){
      if (CheckAllFields()) {
         try {
            int form = 0, term = 0;
            int form1 = 0, term1 = 0;
            ResultSet rs = stDao.searchTable(Integer.parseInt(eAdmissionNumber.getText()), connection); //checking which form and term the student is currently in
            ResultSet rs2 = endTermDao.Search(Integer.parseInt(eAdmissionNumber.getText().trim()), connection);
            while (rs.next()) {
               term = rs.getInt("term");
               form = rs.getInt("form");
               stream = rs.getString("stream");
            }
            while (rs2.next()) {
               form1 = rs2.getInt("form");
               term1 = rs2.getInt("term");
            }
            LocalTime now = LocalTime.now();
            LocalDate today = LocalDate.now();

            if (!(form1 == form && term == term1)) {

               if (!(form == 0 || term == 0)) {
                  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                  alert.setContentText(Content("INSERT"));
                  alert.setHeaderText(null);
                  Optional<ButtonType> answer = alert.showAndWait();

                  if (answer.get() == ButtonType.OK) {
                     endTermDao.InsertEndTermMarks(Integer.parseInt(eAdmissionNumber.getText()), Integer.parseInt(maths.getText()), Integer.parseInt(english.getText()),
                             Integer.parseInt(kiswahili.getText()), Integer.parseInt(biology.getText()), Integer.parseInt(physics.getText()),
                             Integer.parseInt(chemistry.getText()), Integer.parseInt(history.getText()), Integer.parseInt(geography.getText()),
                             Integer.parseInt(cre.getText()), Integer.parseInt(business.getText()), Integer.parseInt(computerStudies.getText()),
                             Integer.parseInt(agriculture.getText()), form, term, today, now, stream, connection);

                     calculateAverageMarks.AddCatAndEndTermMark(form, Integer.parseInt(eAdmissionNumber.getText()), term, stream);
                     setStudentPosition.setStreamPosition(form, term, stream,connection);
                     loadTable();
                     Alert alert23 = new Alert(Alert.AlertType.INFORMATION);
                     alert23.setContentText(" Marks Entered Successfully ");
                     alert23.setHeaderText(null);
                     alert23.showAndWait();

                  }
               } else {
                  CheckStudentExist(eAdmissionNumber);
               }
            } else {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("The Student  with Admission Number " + eAdmissionNumber.getText() + " was already added \n search and edit the students marks");
               alert.setHeaderText(null);
               alert.showAndWait();
            }
            ForButtonVisibility(true);
            loadTable();

         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            if (connection != null) {
               try {
                  connection.close();
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            }
         }
      }else{

         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setHeaderText(null);
         alert.setContentText("All fields in red have invalid inputs. Try  Again");
         alert.showAndWait();
      }

      ForButtonVisibility(true);
      }else{
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setHeaderText(null);
         alert.setContentText("There is no student with that admission Number");
         alert.showAndWait();
      }

   }

   //this sets the right side of the borderPane with the data in the table View when selected
   public void selected() {

      ExamModelTable eModel=table.getSelectionModel().getSelectedItem();
      eAdmissionNumber.setText(String.valueOf(eModel.getAdmissionNumber()));
      maths.setText(String.valueOf(eModel.getMaths()));
      english.setText(String.valueOf(eModel.getEnglish()));
      kiswahili.setText(String.valueOf(eModel.getKiswahili()));
      chemistry.setText(String.valueOf(eModel.getChemistry()));
      physics.setText(String.valueOf(eModel.getPhysics()));
      biology.setText(String.valueOf(eModel.getBiology()));
      cre.setText(String.valueOf(eModel.getCre()));
      geography.setText(String.valueOf(eModel.getGeography()));
      history.setText(String.valueOf(eModel.getHistory()));
      agriculture.setText(String.valueOf(eModel.getAgriculture()));
      computerStudies.setText(String.valueOf(eModel.getComputerStudies()));
      business.setText(String.valueOf(eModel.getBusinessStudies()));
      endTermId=eModel.getEndTermId();

      Connection connection=DBConnector.getConnection();
      try {
         ResultSet rs=stDao.searchTable(eModel.getAdmissionNumber(),connection);
         while(rs.next()){
            second=rs.getString("second_name");
            name=rs.getString("first_name");
            form=rs.getInt("form");
            term=rs.getInt("term");
         }

         ResultSet rs9= averageMarksDao.getCurrentStudentAverageMarks(form,term,admissionNumber,connection);
         while(rs9.next()){
            averageMarksId =rs9.getInt("average_marks_id");
         }

         NameDisplayed.setText(name+"\t"+second);
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

   //this method is used to fill the table when called
   private void fillTable(ResultSet rs) throws SQLException {
      tableList.removeAll(tableList);

      while(rs.next()){
         tableList.add(new ExamModelTable(rs.getInt("admission_number"),rs.getInt("maths"),
                 rs.getInt("english"),rs.getInt("kiswahili"),rs.getInt("biology"),rs.getInt("physics"),
                 rs.getInt("chemistry"),rs.getInt("history"),rs.getInt("geography"),rs.getInt("cre"),
                 rs.getInt("business_studies"),rs.getInt("computer_studies"),
                 rs.getInt("agriculture"),rs.getInt("end_term_id"),
                 rs.getInt("term"),rs.getInt("form"),rs.getDate("date"),rs.getTime("time"),rs.getString("stream")));
      }

      tAdmissionNumber.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
      tMath.setCellValueFactory(new PropertyValueFactory<>("Maths"));
      tBusinessStudies.setCellValueFactory(new PropertyValueFactory<>("BusinessStudies"));
      tEnglish.setCellValueFactory(new PropertyValueFactory<>("English"));
      tPhysics.setCellValueFactory(new PropertyValueFactory<>("Physics"));
      tHistory.setCellValueFactory(new PropertyValueFactory<>("History"));
      tGeography.setCellValueFactory(new PropertyValueFactory<>("Geography"));
      tChemistry.setCellValueFactory(new PropertyValueFactory<>("Chemistry"));
      tDate.setCellValueFactory(new PropertyValueFactory<>("date"));
      tTime.setCellValueFactory(new PropertyValueFactory<>("time"));
      tKiswahili.setCellValueFactory(new PropertyValueFactory<>("Kiswahili"));
      tBiology.setCellValueFactory(new PropertyValueFactory<>("Biology"));
      tTerm.setCellValueFactory(new PropertyValueFactory<>("term"));
      tForm.setCellValueFactory(new PropertyValueFactory<>("form"));
      tCre.setCellValueFactory(new PropertyValueFactory<>("Cre"));
      tAgriculture.setCellValueFactory(new PropertyValueFactory<>("agriculture"));
      tComputerScience.setCellValueFactory(new PropertyValueFactory<>("ComputerStudies"));
      tStream.setCellValueFactory(new PropertyValueFactory<>("stream"));

      table.setItems(tableList);
   }

   //this method is used to set visibility
   private void ForButtonVisibility(boolean value){
      eUpdate.setVisible(!value);
      eEdit.setVisible(value);
      eCancel.setVisible(!value);
      eAddMarks.setVisible(!value);
      eDelete.setVisible(value);
      eMarksClear.setVisible(value);
   }

   //this sets the details to be confirmed
   private String Content(String message){
      String details=  "\t\t\t Confirm the Details below will be "+ message.toUpperCase() +
              "\n \t\t\t END TERM MARKS DETAILS FOR "+
              "\n========================================"+
              "\n Admission Number:\t"+ eAdmissionNumber.getText()+
              "\n  First Name: \t" + name + "" + "\t\t Second Name:\t " + second +
              "\n  Maths :\t\t\t\t" + maths.getText() +
              "\n English :\t\t\t\t" + english.getText() +
              "\n Biology :\t\t\t\t" + biology.getText() +
              "\n  Kiswahili:\t\t\t" + kiswahili.getText() +
              "\n  Chemistry:\t\t\t"+ chemistry.getText()+
              "\n  Physics :\t\t\t\t" + physics.getText() +
              "\n  Cre :\t\t\t\t" + cre.getText() +
              "\n  Geography :\t\t\t"+ geography.getText()+
              "\n  History :\t\t\t\t" + history.getText() +
              "\n Agriculture :\t\t\t" + agriculture.getText() +
              "\n  Computer Studies :\t" + computerStudies.getText() +
              "\n  Business Studeis: \t\t"+ business.getText()+
              "\n\n";

      return details;
   }

   //this method is used to inform the user that the admission number entered does not exist in the database records
   static void CheckStudentExist(JFXTextField eAdmissionNumber) {
      Alert alert=new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      alert.setContentText("Student with Admission Number "+ eAdmissionNumber.getText()+" does not exist");
      alert.showAndWait();
   }

   //used to validate textfield
   private boolean CheckTextField(TextField text){
      boolean check=false;
      if (validate.CheckMarks(text.getText().trim())){
         if (Integer.parseInt(text.getText().trim())< 70) {
            check = true;
            text.setStyle("-fx-prompt-text-fill:#FFB60D; ");
         }else{
            check=false;
            text.setStyle("-fx-prompt-text-fill:red; ");
         }
      }else{
         check=false;
         text.setStyle("-fx-prompt-text-fill:red; ");
      }
      return check;
   }

   //used to validate Admission Number
   private boolean CheckAdmissionNumber(TextField text){
      boolean check=false;
      if (validate.validateNumbers(text.getText().trim())){
         check=true;
         text.setStyle("-fx-prompt-text-fill:#FFB60D; ");
      }else{
         check=false;
         text.setStyle("-fx-prompt-text-fill:red; ");
      }

      return check;
   }

   //used to validate all textField
   private boolean CheckAllFields(){
      boolean check=false;

      if (CheckTextField(maths)&& CheckTextField(english)&& CheckTextField(kiswahili)
              && CheckTextField(biology) && CheckTextField(chemistry)&& CheckTextField(physics) && CheckTextField(agriculture)
              && CheckTextField(geography)&& CheckTextField(cre)&& CheckTextField(history)&& CheckTextField(business)
              && CheckTextField(computerStudies)&& CheckTextField(biology)&& CheckAdmissionNumber(eAdmissionNumber)){
         check=true;
      } else{
         check=false;
      }

      CheckTextField(maths); CheckTextField(english); CheckTextField(kiswahili);
      CheckTextField(biology) ; CheckTextField(chemistry); CheckTextField(physics) ; CheckTextField(agriculture);
      CheckTextField(geography); CheckTextField(cre); CheckTextField(history); CheckTextField(business);
      CheckTextField(computerStudies); CheckTextField(biology); CheckAdmissionNumber(eAdmissionNumber);

      return check;
   }
}
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
import sample.dataAccessObject.examination.CatMarkDao;
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
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class CatMarks implements Initializable {

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
   public TableColumn<ExamModelTable,Integer> tDate;
   public TableColumn<ExamModelTable,Integer> tTime;
   public TableColumn<ExamModelTable,String> tStream;
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

   public TextField searchTextField;
   public ChoiceBox SearchForms;
   public Label NameDisplayed;

   CalculateAverageMarks calculateAverageMarks=new CalculateAverageMarks();
   SetStudentPosition setStudentPosition =new SetStudentPosition();
   FeesPayment test=new FeesPayment();
   private CatMarkDao catMarkDao=new CatMarkDao();
   Validation validate=new Validation();
   private StudentDao sDao=new StudentDao();
   private ObservableList tableList= FXCollections.observableArrayList();
   private ObservableList listForm= FXCollections.observableArrayList();
   private int catId=0;
   private String name="",second="",stream="";
   int form=0,term=0;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      ForButtonVisibility(true);
      fillChoiceBox(SearchForms);
      SearchForms.setValue("All");
      loadTable();
   }

   //this method is used to fill the choiceBox
   public void fillChoiceBox(ChoiceBox eform){
      listForm.removeAll(listForm);
      String[] forForm={"1","2","3","4","All"};
      Collections.addAll(listForm,forForm);
      eform.getItems().addAll(listForm);
   }

   //this method loads the table view with all the cat marks per Form
   public void loadTableForm(ActionEvent actionEvent) {
         Connection connection=DBConnector.getConnection();
      ResultSet rs = null;
      try {
         if (SearchForms.getValue().toString().matches("All")){
            rs=catMarkDao.ReadCatMarks(connection);
         }else{
            rs=catMarkDao.SearchForm(Integer.parseInt(SearchForms.getValue().toString()),connection);
         }
         fillTable(rs);
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

   //this method loads the table View with all the cat Marks
   private void loadTable(){
      Connection connection=DBConnector.getConnection();
      try {
         fillTable(catMarkDao.ReadCatMarks(connection));
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


   //this sets the right side of the borderPane with the data in the table View when selected
   public void selected( ) {
      ExamModelTable eModel=table.getSelectionModel().getSelectedItem();
      eAdmissionNumber.setText(String.valueOf(eModel.getAdmissionNumber()));
      maths.setText(String.valueOf(eModel.getMaths()));
      kiswahili.setText(String.valueOf(eModel.getKiswahili()));
      english.setText(String.valueOf(eModel.getEnglish()));
      chemistry.setText(String.valueOf(eModel.getChemistry()));
      physics.setText(String.valueOf(eModel.getPhysics()));
      cre.setText(String.valueOf(eModel.getCre()));
      biology.setText(String.valueOf(eModel.getBiology()));
      geography.setText(String.valueOf(eModel.getGeography()));
      history.setText(String.valueOf(eModel.getHistory()));
      computerStudies.setText(String.valueOf(eModel.getComputerStudies()));
      agriculture.setText(String.valueOf(eModel.getAgriculture()));
      business.setText(String.valueOf(eModel.getBusinessStudies()));
      catId=eModel.getCatId();

      Connection connection= DBConnector.getConnection();
      try {
         ResultSet rs=sDao.searchTable(eModel.getAdmissionNumber(),connection);
         while(rs.next()){
            name=rs.getString("first_name");
            second=rs.getString("second_name");
            form=rs.getInt("form");
            term=rs.getInt("term");
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

   //this is used to search for Cat Marks with admission Number
   public void search(ActionEvent actionEvent) {
     Connection connection=DBConnector.getConnection();
      try {

         if (searchTextField.getText().isEmpty()){

            fillTable(catMarkDao.ReadCatMarks(connection));
         }else{

            fillTable(catMarkDao.Search(Integer.parseInt(searchTextField.getText()),connection));
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

   public void cancel(ActionEvent actionEvent) {
      ForButtonVisibility(true);
   }

   public void Clear(ActionEvent actionEvent) {
      biology.setText(null);
      chemistry.setText(null);
      history.setText(null);
      business.setText(null);
      kiswahili.setText(null);
      computerStudies.setText(null);
      cre.setText(null);
      ForButtonVisibility(false);
      physics.setText(null);
      eUpdate.setVisible(false);
      geography.setText(null);
      agriculture.setText(null);
      maths.setText(null);
      english.setText(null);
      eAdmissionNumber.setText(null);
   }

   //this is used to update the cat marks
   public void update(ActionEvent actionEvent) {
      Connection connection=DBConnector.getConnection();
      if (test.CheckIfStudentExist(eAdmissionNumber)) {
         if (CheckAllFields()) {
            try {
               int form = 0, term = 0;
               ResultSet rs = sDao.searchTable(Integer.parseInt(eAdmissionNumber.getText()), connection);
               while (rs.next()) {
                  term = rs.getInt("term");
                  form = rs.getInt("form");
                  name = rs.getString("first_name");
                  second = rs.getString("second_name");
                  stream = rs.getString("stream");
               }
               LocalTime now = LocalTime.now();
               LocalDate today = LocalDate.now();

               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setHeaderText(null);
               alert.setContentText(Content("UPDATE"));
               Optional<ButtonType> answer = alert.showAndWait();
               if (CheckAllFields()) {
                  if (answer.get() == ButtonType.OK) {
                     catMarkDao.UpdateCatMarks(Integer.parseInt(eAdmissionNumber.getText()), Integer.parseInt(maths.getText()), Integer.parseInt(english.getText()),
                             Integer.parseInt(kiswahili.getText()), Integer.parseInt(biology.getText()), Integer.parseInt(physics.getText()), Integer.parseInt(chemistry.getText()),
                             Integer.parseInt(history.getText()), Integer.parseInt(geography.getText()), Integer.parseInt(cre.getText()),
                             Integer.parseInt(business.getText()), Integer.parseInt(computerStudies.getText()), Integer.parseInt(agriculture.getText()),
                             form, term, catId, now, today, stream, connection);

                     calculateAverageMarks.AddCatAndEndTermMark(form, Integer.parseInt(eAdmissionNumber.getText()), term, stream);
                     setStudentPosition.setStreamPosition(form, term, stream, connection);
                     loadTable();

                  }
               } else {

                  Alert alert2 = new Alert(Alert.AlertType.ERROR);
                  alert2.setHeaderText(null);
                  alert2.setContentText("All fields in red have invalid inputs. Try  Again");
                  alert2.showAndWait();
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
      }else {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setHeaderText(null);
         alert.setContentText("There is no student with that admission Number");
         alert.showAndWait();
      }
   }

   //this is used to add the cat marks to the database
   public void AddMarks(ActionEvent actionEvent) {
      Connection connection=DBConnector.getConnection();
      if (test.CheckIfStudentExist(eAdmissionNumber)) {
         try {
            int form = 0, term = 0, form1 = 0, term1 = 0;
            ResultSet rs = sDao.searchTable(Integer.parseInt(eAdmissionNumber.getText()), connection);
            ResultSet rs2 = catMarkDao.Search(Integer.parseInt(eAdmissionNumber.getText()), connection);

            while (rs.next()) {
               form = rs.getInt("form");
               term = rs.getInt("term");
               stream = rs.getString("stream");
            }

            while (rs2.next()) {
               form1 = rs2.getInt("form");
               term1 = rs2.getInt("term");
            }

            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            if (!(form == form1 && term == term1)) {
               if (CheckAllFields()) {
                  if (!(form == 0 || term == 0)) {
                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                     alert.setHeaderText(null);
                     alert.setContentText(Content("Insert"));
                     Optional<ButtonType> answer = alert.showAndWait();

                     if (answer.get() == ButtonType.OK) {
                        catMarkDao.InsertCatMarks(Integer.parseInt(eAdmissionNumber.getText()), Integer.parseInt(maths.getText()), Integer.parseInt(english.getText()),
                                Integer.parseInt(kiswahili.getText()), Integer.parseInt(biology.getText()), Integer.parseInt(physics.getText()),
                                Integer.parseInt(chemistry.getText()), Integer.parseInt(history.getText()), Integer.parseInt(geography.getText()),
                                Integer.parseInt(cre.getText()), Integer.parseInt(business.getText()), Integer.parseInt(computerStudies.getText()),
                                Integer.parseInt(agriculture.getText()),
                                form, term, today, now, stream, connection);

                        Alert alert23 = new Alert(Alert.AlertType.INFORMATION);
                        alert23.setContentText(" Marks Entered Successfully ");
                        alert23.setHeaderText(null);
                        alert23.showAndWait();

                        calculateAverageMarks.AddCatAndEndTermMark(form, Integer.parseInt(eAdmissionNumber.getText()), term, stream);
                        setStudentPosition.setStreamPosition(form, term, stream, connection);
                        loadTable();

                     }
                  } else {
                     EndTermExams.CheckStudentExist(eAdmissionNumber);
                  }
               } else {

                  Alert alert2 = new Alert(Alert.AlertType.ERROR);
                  alert2.setHeaderText(null);
                  alert2.setContentText("All fields in red have invalid inputs. Try  Again");
                  alert2.showAndWait();
               }
            } else {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setHeaderText(null);
               alert.setContentText(" The Student  with Admission Number " + eAdmissionNumber.getText() + " was already added \n search and edit the students marks");
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
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setHeaderText(null);
         alert.setContentText("There is no student with that admission Number");
         alert.showAndWait();
      }
   }

   //this is used to delete the cat marks from the database
   public void delete(ActionEvent actionEvent) {
      Connection connection=DBConnector.getConnection();
      try {
         ResultSet rs5= sDao.searchTable(Integer.parseInt(eAdmissionNumber.getText()),connection);
         while(rs5.next()) {
            stream = rs5.getString("stream");
         }

         Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
         alert.setHeaderText(null);
         alert.setContentText(Content("DELETE"));
         Optional<ButtonType> answer=alert.showAndWait();

         if (answer.get()==ButtonType.OK) {
            catMarkDao.DeleteCatMarks(catId,connection);
            loadTable();

            calculateAverageMarks.AddCatAndEndTermMark(form, Integer.parseInt(eAdmissionNumber.getText()), term, stream);
            setStudentPosition.setStreamPosition(form,term,stream,connection);
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

   //this method is used to hide add Marks button and delete button when edit button is pressed
   public void editable(ActionEvent actionEvent) {
      ForButtonVisibility(false);
      eAddMarks.setVisible(false);
   }


   //this method is used to fill the table when called
   private void fillTable(ResultSet rs) throws SQLException {
      tableList.removeAll(tableList);

      while(rs.next()){
         tableList.add(new ExamModelTable(rs.getInt("admission_number"),rs.getInt("maths"),
                 rs.getInt("english"),rs.getInt("kiswahili"),rs.getInt("biology"),rs.getInt("physics"),
                 rs.getInt("chemistry"),rs.getInt("history"),rs.getInt("geography"),rs.getInt("cre"),
                 rs.getInt("business_studies"),rs.getInt("computer_studies"),rs.getInt("agriculture"),rs.getInt("cat_id"),
                 rs.getInt("term"),rs.getInt("form"),rs.getDate("date"),rs.getTime("time"),rs.getString("stream")));
      }

      tAdmissionNumber.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
      tMath.setCellValueFactory(new PropertyValueFactory<>("Maths"));
      tEnglish.setCellValueFactory(new PropertyValueFactory<>("English"));
      tKiswahili.setCellValueFactory(new PropertyValueFactory<>("Kiswahili"));
      tBiology.setCellValueFactory(new PropertyValueFactory<>("Biology"));
      tPhysics.setCellValueFactory(new PropertyValueFactory<>("Physics"));
      tChemistry.setCellValueFactory(new PropertyValueFactory<>("Chemistry"));
      tHistory.setCellValueFactory(new PropertyValueFactory<>("History"));
      tGeography.setCellValueFactory(new PropertyValueFactory<>("Geography"));
      tCre.setCellValueFactory(new PropertyValueFactory<>("Cre"));
      tAgriculture.setCellValueFactory(new PropertyValueFactory<>("agriculture"));
      tBusinessStudies.setCellValueFactory(new PropertyValueFactory<>("BusinessStudies"));
      tComputerScience.setCellValueFactory(new PropertyValueFactory<>("ComputerStudies"));
      tTerm.setCellValueFactory(new PropertyValueFactory<>("term"));
      tForm.setCellValueFactory(new PropertyValueFactory<>("form"));
      tDate.setCellValueFactory(new PropertyValueFactory<>("date"));
      tTime.setCellValueFactory(new PropertyValueFactory<>("time"));
      tStream.setCellValueFactory(new PropertyValueFactory<>("stream"));

      table.setItems(tableList);
   }

   //this method is used to set visibility of the buttons
   private void ForButtonVisibility(boolean value){
      eUpdate.setVisible(!value);
      eEdit.setVisible(value);
      eDelete.setVisible(value);
      eCancel.setVisible(!value);
      eAddMarks.setVisible(!value);
      eMarksClear.setVisible(value);
   }

   //this sets the details to be confirmed
   private String Content(String message){
      String details=  "\t\t\t Confirm the Details below will be "+ message.toUpperCase() +
              "\n \t\t\t CAT MARKS DETAILS FOR "+
              "\n========================================"+
              "\n Admission Number:\t"+ eAdmissionNumber.getText()+
              "\n  First Name: \t" + name + "" + "\t\t Second Name:\t " + second +
              "\n  Maths :\t\t\t\t" + maths.getText() +
              "\n English :\t\t\t\t" + english.getText() +
              "\n  Kiswahili:\t\t\t" + kiswahili.getText() +
              "\n  Chemistry:\t\t\t"+ chemistry.getText()+
              "\n  Physics :\t\t\t\t" + physics.getText() +
              "\n Biology :\t\t\t\t" + biology.getText() +
              "\n  Cre :\t\t\t\t" + cre.getText() +
              "\n  Geography :\t\t\t"+ geography.getText()+
              "\n  History :\t\t\t\t" + history.getText() +
              "\n Agriculture :\t\t\t" + agriculture.getText() +
              "\n  Computer Studies :\t" + computerStudies.getText() +
              "\n  Business Studeis: \t\t"+ business.getText()+
              "\n\n";

      return details;
   }

   //used to validate marks
   private boolean CheckTextField(TextField text){
      boolean check=false;
      if (validate.CheckMarks(text.getText().trim())){
         if (Integer.parseInt(text.getText().trim())< 30) {
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

   //used to validate all fields
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
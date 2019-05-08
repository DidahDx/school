package sample.controllers.admission;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.Controller;
import sample.dataAccessObject.DBConnector;
import sample.model.Validation;
import sample.dataAccessObject.admission.StudentDao;
import sample.model.admission.StudentModelTable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class studentDetails implements Initializable {

    @FXML public TextField searchTextField;
    @FXML public AnchorPane anchorPane;
    @FXML public ChoiceBox SearchForms;
     public Label NameDisplayed;
    @FXML public JFXTextField eFirstName;
    @FXML public JFXTextField eLastName;
    @FXML public JFXTextField eSecondName;
    @FXML public JFXTextField eAdmissionNumber;
    @FXML public ComboBox<String> eGender;
    @FXML public ComboBox<String> eCounty;
    @FXML public AnchorPane AnchorPane;
    @FXML public BorderPane borderPane;
    @FXML public JFXButton cancelButton;
    @FXML public JFXButton updateButton;
    @FXML public JFXButton editButton;
    @FXML public JFXButton delete;
     public JFXDatePicker eDateOfBirth;
     public ComboBox<String> eForm;
     public JFXDatePicker eDateOfAdmission;
     public ComboBox<String> eStudentType;
    public ComboBox<String> eStream;
    public ComboBox<String> eDorm;
    public JFXTimePicker eTimeOfAdmission;
    public ComboBox eTerm;
    public ComboBox sStream;

    @FXML TableView<StudentModelTable> table;
    @FXML TableColumn<StudentModelTable,String> admissionNumber;
    @FXML TableColumn<StudentModelTable,String> firstName;
    @FXML TableColumn<StudentModelTable,String> secondName;
    @FXML TableColumn<StudentModelTable,String> lastName;
    @FXML TableColumn<StudentModelTable,String> stream;
    @FXML TableColumn<StudentModelTable,String> form;
    @FXML TableColumn<StudentModelTable,String> gender;

    Controller controller=new Controller();
    private admissionsForm adForm=new admissionsForm();
    private ObservableList<StudentModelTable> oblist= FXCollections.observableArrayList();
    private StudentDao dao=new StudentDao();
    private ObservableList forms=FXCollections.observableArrayList();
    private ObservableList<String> list_gender=FXCollections.observableArrayList();
    private ObservableList<String> listStudentType=FXCollections.observableArrayList();
    private ObservableList<String> listDorm=FXCollections.observableArrayList();
    private ObservableList<String> listStream=FXCollections.observableArrayList();
    private ObservableList<String> listTerm=FXCollections.observableArrayList();

    private Validation  validate=new Validation();
    private admissionsForm validateFields=new admissionsForm();
    private GuardianDetails validateAdmissionNo= new GuardianDetails();
    private int admissionDelete=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllForms();
        fillForm(forms,SearchForms);
        edit(false);
        eAdmissionNumber.setEditable(false);
        validate.changeDatePickerFormat(eDateOfBirth);
        validate.changeDatePickerFormat(eDateOfAdmission);
        validate.pastDates(eDateOfBirth);
        updateButton.setVisible(false);
        cancelButton.setVisible(false);
        adForm.loadComboBox(this.eCounty);
        adForm.FormComboBox(this.eForm);
        fillTerm(eTerm);
        fillGender(eGender);
        fillStudentType(eStudentType);
        fillStream(eStream);
        fillStream(sStream);
        fillDorm(eDorm);
    }

    //this loads all the forms table
    private void loadAllForms(){
        ResultSet rs;
       Connection connection= DBConnector.getConnection();
        try {
            rs=dao.getAllStudentDetails(connection);
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

    //this loads the choiceBox with the form numbers 1,2,3,4 and All
   public void fillForm(ObservableList<String> forms, ChoiceBox SearchForms){
        forms.removeAll(forms);

        String f []={"1","2","3","4","All"};
        forms.addAll(Arrays.asList(f));

        SearchForms.getItems().addAll(forms);
        SearchForms.setValue(f[4]);
    }

    //this loads the comboBox with the gender male and female
    private void fillTerm(ComboBox Gender){
        listTerm.removeAll(listTerm);

        String[] gender = {"1", "2", "3"};
        listTerm.addAll(Arrays.asList(gender));

        Gender.getItems().addAll(listTerm);
    }

    //this loads the comboBox with the gender male and female
    private void fillGender(ComboBox Gender){
        list_gender.removeAll(list_gender);

        String[] gender = {"male", "female"};
        list_gender.addAll(Arrays.asList(gender));

        Gender.getItems().addAll(list_gender);
    }

    //this loads the comboBox with the student types
    private void fillStudentType(ComboBox StudentType){
        listStudentType.removeAll(listStudentType);

        String[] studentType = {"Boarder", "Day Scholar"};
        listStudentType.addAll(Arrays.asList(studentType));

        StudentType.getItems().addAll(listStudentType);
    }

    //this loads the comboBox with the dorm
    private void fillDorm(ComboBox Dorm){
        listDorm.removeAll(listDorm);

        String[] dorm ={"Elgon","Kenya","Kilimanjaro","Longonot","NONE"};
        listDorm.addAll(Arrays.asList(dorm));

        Dorm.getItems().addAll(listDorm);
    }


    //this loads the comboBox with the stream/
    public void fillStream(ComboBox Stream){
        listStream.removeAll(listStream);

        String[] stream = {"EAST", "WEST"};
        listStream.addAll(Arrays.asList(stream));

        Stream.getItems().addAll(listStream);
        Stream.setValue(stream[1]);
    }

    //handles the searching for students with admission number
    public void search(ActionEvent actionEvent) {
        Connection connection=DBConnector.getConnection();
        ResultSet rs = null;
        try {

            if(!(searchTextField.getText().trim().isEmpty())){
             rs= dao.searchTable(Integer.parseInt(searchTextField.getText().trim()),connection);
            }else {
               rs= dao.getAllStudentDetails(connection);
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

    //this method is used to load the whole table
    public void loadTable(ActionEvent actionEvent) {
        ResultSet rs;
        Connection connection=DBConnector.getConnection();
        try {
            if(SearchForms.getValue().toString()== "All"){
                rs=dao.getAllStudentDetails(connection);
            }
            else { rs= dao.loadsForms( Integer.parseInt(SearchForms.getValue().toString()),connection);}

            if(!SearchForms.getValue().toString().matches("All") &&
                    (sStream.getValue().toString().matches("EAST") || sStream.getValue().toString().matches("WEST")))
            {
                rs=dao.loadsStream(Integer.parseInt(SearchForms.getValue().toString()),sStream.getValue().toString(),connection);
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

    //this method is used to fill the table when called
    private void fillTable(ResultSet rs) throws SQLException {
            oblist.removeAll(oblist);

            while(rs.next()){
                    oblist.add(new StudentModelTable(rs.getString("first_name"),rs.getString("second_name"),
                            rs.getString("stream"), rs.getInt("form"),
                            rs.getString("gender"),rs.getInt("admission_number"),
                            rs.getString("last_name")));
            }

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        secondName.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        stream.setCellValueFactory(new PropertyValueFactory<>("stream"));
        form.setCellValueFactory(new PropertyValueFactory<>("form"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        admissionNumber.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        table.setItems(oblist);
    }


    //this get the row data from the table when its selected
    public void selected() {
        edit(false);
        StudentModelTable studentModelTable = table.getSelectionModel().getSelectedItem();
        eFirstName.setText(studentModelTable.getFirstName());
        NameDisplayed.setText(studentModelTable.getFirstName()+"  "+ studentModelTable.getSecondName());
        eSecondName.setText(studentModelTable.getSecondName());
        eLastName.setText(studentModelTable.getLastName());
        eAdmissionNumber.setText(Integer.toString(studentModelTable.getAdmissionNumber()));
        eGender.setValue(studentModelTable.getGender());
        admissionDelete=studentModelTable.getAdmissionNumber();

        ResultSet rs;
        Connection connection=DBConnector.getConnection();
            try {
             rs=   dao.searchTable(studentModelTable.getAdmissionNumber(),connection);

             while(rs.next()){
                 eDateOfBirth.setValue(LocalDate.parse(rs.getDate("date_of_birth").toString()));
                 eGender.setValue(rs.getString("gender"));
                 eDateOfAdmission.setValue(rs.getDate("date_of_admission").toLocalDate());
                 eStudentType.setValue(rs.getString("student_type"));
                 eForm.setValue(Integer.toString(rs.getInt("form")));
                 eCounty.setValue(rs.getString("county_of_resident"));
                 eDorm.setValue(rs.getString("dorm"));
                 eTimeOfAdmission.setValue(rs.getTime("time_of_admission").toLocalTime());
                 eStream.setValue(rs.getString("stream"));
                 eTerm.setValue(rs.getInt("term"));
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

    //this makes the fields editable
    private void edit(boolean value) {

        eSecondName.setEditable(value);
        eDateOfBirth.setEditable(value);
        eLastName.setEditable(value);
        eTimeOfAdmission.setEditable(value);
        eFirstName.setEditable(value);
    }

    //this makes the fields editable
    public void edit(ActionEvent actionEvent) {
        updateButton.setVisible(true);
        cancelButton.setVisible(true);
        editButton.setVisible(false);
        delete.setVisible(false);
       edit(true);
       eFirstName.requestFocus();
    }

    //updates the students details
    public void update(ActionEvent actionEvent) {
       if(CheckAllFields()) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setContentText(Content("UPDATED"));
          alert.setHeaderText(null);
          Optional<ButtonType> button = alert.showAndWait();

          if (button.get() == ButtonType.OK) {

             dao.UpdateStudentDetails(Integer.parseInt(eAdmissionNumber.getText()), eFirstName.getText().trim(),
                     eSecondName.getText().trim(), eLastName.getText().trim(), eCounty.getValue(), eGender.getValue(),
                     eDateOfBirth.getValue(), eDateOfAdmission.getValue(), eStudentType.getValue(), Integer.parseInt(eForm.getValue()),
                     eDorm.getValue(), eTimeOfAdmission.getValue(), eStream.getValue(),
                     Integer.parseInt(eTerm.getValue().toString()));

          }
          updateButton.setVisible(false);
          cancelButton.setVisible(false);
          editButton.setVisible(true);
          delete.setVisible(true);
          loadAllForms();
       }else{
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setContentText("");
          alert.setHeaderText(null);
       }

    }

    //cancels editing
    public void cancel(ActionEvent actionEvent) {
        updateButton.setVisible(false);
        cancelButton.setVisible(false);
        editButton.setVisible(true);
        delete.setVisible(true);
    }

    //deletes students records
    public void delete(ActionEvent actionEvent) {
       Connection connection=DBConnector.getConnection();
       try {
           Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Confiramtion");
           alert.setHeaderText(null);
           alert.setContentText(Content("DELETE"));
           Optional<ButtonType> answer= alert.showAndWait();

           if(answer.get()==ButtonType.OK) {
               dao.deleteStudentRecord(admissionDelete,connection);
           }

       }catch (SQLException s){
           s.printStackTrace();
       }finally {
          if (connection!=null){
             try {
                connection.close();
             } catch (SQLException e) {
                e.printStackTrace();
             }
          }
       }
      loadAllForms();
    }

    //this sets the details to be confirmed
    private String Content(String message){
        String details=  "\t\t\t Confirm the Details below will be "+ message +
                "\n\n \t\t\t Student Details "+
                "\n========================================"+
                "\n Admission Number:\t"+eAdmissionNumber.getText()+
                "\n  First Name:\t\t\t" + eFirstName.getText() +
                "\n Second Name:\t\t" + eSecondName.getText() +
                "\n  Last Name:\t\t\t" + eLastName.getText() +
                "\n  Form:\t\t\t\t"+ eForm.getValue()+
                "\n Stream:\t\t\t\t"+eStream.getValue()+
                "\n\n";

        return details;
    }

   /*******************************************************************************************************************
    *
    *        Validation methods
    *
    ******************************************************************************************************************/


    //this method is used to validate all the fields
   private boolean CheckAllFields(){
       boolean check=false;
       if(validateAdmissionNo.CheckAdmissionNumber(eAdmissionNumber) && validateFields.checkOptionalTextField(eSecondName)&&
       validateFields.checkTextField(eFirstName)&& validateFields.checkTextField(eLastName)){

          check=true;
       }else{
          check=false;
       }
      validateAdmissionNo.CheckAdmissionNumber(eAdmissionNumber); validateFields.checkOptionalTextField(eSecondName);
              validateFields.checkTextField(eFirstName); validateFields.checkTextField(eLastName);

       return check;
   }
}
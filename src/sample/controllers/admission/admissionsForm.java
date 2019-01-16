package sample.controllers.admission;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.database.DataAccessObject;
import sample.Validation;
import sample.model.MyPrinter;
import sample.model.admission.SchoolDetailsGenerator;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.ResourceBundle;

public class admissionsForm implements Initializable {
    
    @FXML public ComboBox<String> Form;
    @FXML public JFXTextField studentMiddleName,studentFirstName,studentLastName;
    @FXML public JFXTextField  phoneNumber,guardianFirstName,guardianLastName,guardianEmail;
    @FXML public ToggleGroup Select,Gender;
    @FXML public JFXDatePicker dateOfAdmission;
    @FXML public AnchorPane anchorPane;
    @FXML public JFXTextField lastAdmissionNumber;
    @FXML public Tooltip tooltip;
    @FXML public RequiredFieldValidator selectFormValidator;
    @FXML public JFXDatePicker dateOfBirth;
    public TextArea studentDetails;
    public JFXRadioButton boarder;
    public JFXRadioButton female;
    public JFXRadioButton male;
    public JFXRadioButton DayScholar;
    public AnchorPane printArea;
    @FXML private ComboBox<String> County;

    private DataAccessObject dao=new DataAccessObject();
    private Validation valid=new Validation();
    private Controller con =new Controller();
    private ObservableList list_of_counties = FXCollections.observableArrayList();
    private ObservableList list_of_forms = FXCollections.observableArrayList();
    private SchoolDetailsGenerator  schoolDetailsGenerator=new SchoolDetailsGenerator();
    private LocalDate today=LocalDate.now();
    private LocalTime now=LocalTime.now();


    //Called to initialize a controller after its root element has been completely processed.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateOfAdmission.setValue(today);
        valid.changeDateFormat(dateOfAdmission);
        valid.changeDateFormat(dateOfBirth);
       loadComboBox(this.County);
        FormComboBox();
      setLastAdmissionNumber();
    }


    //This method is used to Add items to the County ComboBox
    public void loadComboBox(ComboBox County){
        list_of_counties.removeAll( list_of_counties );
        String[] counties = {"Mombasa", "Kwale", "Kilifi", "Tana River", "Lamu", "Taita–Taveta", "Garissa", "Wajir",
                "Mandera", "Marsabit", "Isiolo", "Meru", "Tharaka-Nithi", "Embu", "Kitui", "Machakos", "Makueni", "Nyandarua",
                "Nyeri", "Kirinyaga", "Murang'a", "Kiambu", "Turkana",
                "West Pokot", "Samburu", "Trans-Nzoia", "Uasin Gishu",
                "Elgeyo-Marakwet", "Nandi", "Baringo", "Laikipia", "Nakuru", "Narok", "Kajiado",
                "Kericho", "Bomet", "Kakamega", "Vihiga", "Bungoma ", "Busia ",
                "Siaya ", "Kisumu", "Homa Bay", "Migori", "Kisii", "Nyamira", "Nairobi"};

        Collections.addAll(list_of_counties, counties);

        Collections.sort(list_of_counties);
        County.getItems().addAll(list_of_counties);

    }

    //This method is used to Add items to the Form ComboBox
    private void FormComboBox(){
        list_of_forms.removeAll( list_of_forms );

        String a="1";
        String b="2";
        String c="3";
        String d="4";

        list_of_forms.addAll(a,b,c,d);
        Form.getItems().addAll(list_of_forms);
        Form.setEditable(false);
    }


    //This method sets the color the label after the text is validated and returns a true or false
    private boolean checkTextField(TextField text){
        boolean check;
        if(valid.validate(text.getText().trim())) {
            check=true;
            text.setStyle("-fx-prompt-text-fill:#FFB60D; ");
        }else{
            text.setStyle("-fx-prompt-text-fill:red;");

            check=false;
        }
        return check;
    }

    //This is used to show a textField if students details table is empty
    private void setLastAdmissionNumber(){
           int admissionNumber = 0;

           try {
               admissionNumber=dao.getLastAdmissionNumber();
           } catch (SQLException e) {
               e.printStackTrace();
           }
           if (admissionNumber==0){
               lastAdmissionNumber.setVisible(true);
          }else{
               lastAdmissionNumber.setVisible(false);
           }


    }


    public void submitStudentDetails(ActionEvent actionEvent) {
        checkTextField(studentFirstName); checkTextField(studentLastName);
        String gender = null;
        String studentType=null;
        if(male.isSelected()){
            gender="male";
        }else if(female.isSelected()){
            gender="female";
        }
        if(boarder.isSelected()){
            studentType="Boarder";
        }else if(DayScholar.isSelected()){
            studentType="Day Scholar";
        }
//        checkTextField(guardianFirstName);checkTextField(guardianLastName);

        studentDetails.clear();
        studentDetails.appendText(
                    "\n\t\t\t Student Details" +
                    "\n========================================"+
                    "\n Admission Number:\t\t"+ schoolDetailsGenerator.getAdmissionNumber() +
                    " \n First Name:\t\t\t\t" + studentFirstName.getText() +
                    "\n Second Name:\t\t\t" + studentMiddleName.getText() +
                    "\n Last Name:\t\t\t\t" + studentLastName.getText() +
                    "\n County Of Resident:\t\t"+ County.getValue() +
                    "\n Gender :\t\t\t\t"+ gender +
                    "\n Date Of Birth:\t\t\t"+  dateOfBirth.getValue()+

                    "\n\n\n \t\t\t Guardian Details "+
                    "\n========================================"+
                    " \n Guardian First Name:\t\t\t" + guardianFirstName.getText() +
                    "\n Guardian Last Name:\t\t\t" + guardianLastName.getText() +
                    "\n Guardian Email:\t\t\t\t" + guardianEmail.getText() +
                    "\n Guardian Phone Number:\t\t"+phoneNumber.getText()+

                    "\n\n\n \t\t\t School Details "+
                    "\n========================================"+
                    " \n Date Of Admission:\t\t" + dateOfAdmission.getValue() +
                    "\n Student Type:\t\t\t" + studentType +
                    "\n Form:\t\t\t\t\t" + Form.getValue() +
                    "\n Dorm:\t\t\t\t\t" + schoolDetailsGenerator.getDorm() +
                    "\n Stream:\t\t\t\t\t" + schoolDetailsGenerator.getStream() +
                    "\n Time :\t\t\t\t\t"+ now.minusNanos(now.getNano())

        );

        if(checkTextField(studentFirstName) && checkTextField(studentLastName))
        {

            dao.SaveStudentsNames(studentFirstName.getText().trim(), studentMiddleName.getText().trim(), studentLastName.getText().trim(),
                    County.getValue(), String.valueOf(Gender.getSelectedToggle().toString()),dateOfBirth.getValue(),schoolDetailsGenerator.getAdmissionNumber());

            dao.SaveGuardianDetails(guardianFirstName.getText().trim(),guardianLastName.getText().trim(),guardianEmail.getText().trim(),
                    Integer.parseInt(phoneNumber.getText().trim()),schoolDetailsGenerator.getAdmissionNumber());


        }
        }

        //this show a text hint on hover
    public void showHint(MouseEvent mouseEvent) { tooltip.setText("The last admission number given to a student");
    }

    @Deprecated
    public void handleForm(ActionEvent actionEvent) {
        JFXAutoCompletePopup<String> autoCompletePopup=new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(County.getItems());

    autoCompletePopup.setSelectionHandler(event-> County.setValue(event.getObject()));
    }


    public void validateBirthDate() {

        if(!(valid.pastDates(dateOfBirth))) {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("INFORMATION");
            alert.setContentText("Choose a Valid Date");
            dateOfBirth.setValue(null);

            alert.show();
        }
    }


    public void printDetails(ActionEvent actionEvent) {


        new MyPrinter().Print(printArea);
    }


}
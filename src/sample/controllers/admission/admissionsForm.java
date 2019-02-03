package sample.controllers.admission;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Controller;
import sample.dataAccessObject.admission.GuardianDao;
import sample.dataAccessObject.admission.StudentDao;
import sample.Validation;
import sample.model.MyPrinter;
import sample.model.admission.SchoolDetailsGenerator;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
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
    public Label labelDOB;
    public JFXComboBox term;
    @FXML private ComboBox<String> County;

    private StudentDao dao=new StudentDao();
    private Validation valid=new Validation();
    private Controller con =new Controller();
    private ObservableList list_of_counties = FXCollections.observableArrayList();
    private ObservableList list_of_forms = FXCollections.observableArrayList();
    private SchoolDetailsGenerator  schoolDetailsGenerator=new SchoolDetailsGenerator();
    private LocalDate today=LocalDate.now();
    private LocalTime now=LocalTime.now();
    private int admissionNumber = 0;
    private GuardianDao gDao=new GuardianDao();
private Validation validation=new Validation();

    //Called to initialize a controller after its root element has been completely processed.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateOfAdmission.setValue(today);
        valid.changeDatePickerFormat(dateOfAdmission);
        valid.changeDatePickerFormat(dateOfBirth);
       loadComboBox(this.County);
        FormComboBox(this.Form);
      setLastAdmissionNumber();
      TermComboBox(term);

    }


    //This method is used to AddNewGuardian items to the County ComboBox
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
    public void FormComboBox(ComboBox Form){
        list_of_forms.removeAll( list_of_forms );

        String[] a={"1","2","3","4"};
        Collections.addAll(list_of_forms,a);
        Form.getItems().addAll(list_of_forms);
        Form.setEditable(false);
    }

    //This method is used to AddNewGuardian items to the Form ComboBox
    public void TermComboBox(ComboBox Form){
        list_of_forms.removeAll( list_of_forms );

        String[] b={"1","2","3"};
        Collections.addAll(list_of_forms,b);
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

           try {
               admissionNumber =dao.getLastAdmissionNumber();
           } catch (SQLException e) {
               e.printStackTrace();
           }
           if (admissionNumber==0){
               lastAdmissionNumber.setVisible(true);
          }else{
               lastAdmissionNumber.setVisible(false);
           }

    }

//this method sends the data to the database
    public void submitStudentDetails(ActionEvent actionEvent) {
        checkTextField(studentFirstName); checkTextField(studentLastName);
        String gender = null;
        String studentType=null;
        String Dorm=null;

        if(male.isSelected()){
            gender="male";
        }else if(female.isSelected()){
            gender="female";
        }

        if(boarder.isSelected()){
            studentType="Boarder";
          Dorm=schoolDetailsGenerator.getDorm(gender);
        }else if(DayScholar.isSelected()){
            studentType="Day Scholar";
            Dorm="NONE";
        }

        if(!(admissionNumber==0) && !(lastAdmissionNumber.isVisible())){
            admissionNumber=schoolDetailsGenerator.getAdmissionNumber();
        }else{
            admissionNumber = 1 +Integer.parseInt(lastAdmissionNumber.getText());
        }

//        checkTextField(guardianFirstName);checkTextField(guardianLastName);

        studentDetails.clear();
        studentDetails.appendText(
                    "\n\t\t\t Student Details" +
                    "\n========================================"+
                    "\n Admission Number:\t\t"+ admissionNumber +
                    " \n First Name:\t\t\t\t" + studentFirstName.getText() +
                    "\n Second Name:\t\t\t" + studentMiddleName.getText() +
                    "\n Last Name:\t\t\t\t" + studentLastName.getText() +
                    "\n County Of Resident:\t\t"+ County.getValue() +
                    "\n Gender :\t\t\t\t"+ gender +
                    "\n Date Of Birth:\t\t\t"+ validation.changeDateFormat(dateOfBirth.getValue())+

                    "\n\n \t\t\t Guardian Details "+
                    "\n========================================"+
                    " \n Guardian First Name:\t\t\t" + guardianFirstName.getText() +
                    "\n Guardian Last Name:\t\t\t" + guardianLastName.getText() +
                    "\n Guardian Email:\t\t\t\t" + guardianEmail.getText() +
                    "\n Guardian Phone Number:\t\t"+phoneNumber.getText()+

                    "\n\n \t\t\t School Details "+
                    "\n========================================"+
                    " \n Date Of Admission:\t\t" + validation.changeDateFormat(dateOfAdmission.getValue()) +
                    "\n Student Type:\t\t\t" + studentType +
                    "\n Form:\t\t\t\t\t" + Form.getValue() +
                            "\n Term:\t\t\t\t\t"+ term.getValue() +
                            "\n Dorm:\t\t\t\t\t" + Dorm +
                    "\n Stream:\t\t\t\t\t" + schoolDetailsGenerator.getStream() +
                    "\n Time :\t\t\t\t\t"+ now.minusNanos(now.getNano())

        );

        if(checkTextField(studentFirstName) && checkTextField(studentLastName))
        {

          dao.SaveStudentsNames(studentFirstName.getText().trim(), studentMiddleName.getText().trim(), studentLastName.getText().trim(),
            County.getValue(),gender, dateOfBirth.getValue(),
           admissionNumber,dateOfAdmission.getValue(),studentType, Integer.parseInt(Form.getValue()),
                  Dorm,now.minusNanos(now.getNano()),schoolDetailsGenerator.getStream(),Integer.parseInt(term.getValue().toString()));

          gDao.AddGuardianDetails(guardianFirstName.getText().trim(), guardianLastName.getText().trim(),
            guardianEmail.getText().trim(), Integer.parseInt(phoneNumber.getText().trim()),
            admissionNumber);
               setLastAdmissionNumber();
        }

        }

        //this show a text hint on hover
    public void showHint(MouseEvent mouseEvent) { tooltip.setText("The last admission number given to a student");
    }

    //set the color label of Date of birth when validate
    public void validateBirthDate() {

        if(valid.pastDates(dateOfBirth))   //validation of birth date
        {
            labelDOB.setStyle("-fx-text-fill: #FFB60D;");
        }else{
            labelDOB.setStyle("-fx-text-fill: red;");
            dateOfBirth.setValue(null);
        }
    }

    // this method prints the student details
    public void printDetails(ActionEvent actionEvent) {
        new MyPrinter().Print(studentDetails);
    }
}
package sample.controllers;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXDatePicker;
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
import sample.Controller;
import sample.database.DataAccessObject;
import sample.validation;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class admissionsForm implements Initializable {
    
    @FXML public ComboBox<String> Form;
    @FXML public JFXTextField studentMiddleName,studentFirstName,studentLastName;
    @FXML public JFXTextField  phoneNumber,guardianFirstName,guardianLastName,guardianEmail;
    @FXML public ToggleGroup Select,Gender;
    @FXML public JFXDatePicker dateOfAdmission;
    @FXML public AnchorPane anchorPane;
    public JFXTextField lastAdmissionNumber;
    public Tooltip tooltip;
    public RequiredFieldValidator selectFormValidator;
    @FXML private ComboBox<String> County;

    private DataAccessObject dao=new DataAccessObject();
    private validation valid=new validation();
    private Controller con =new Controller();
    private ObservableList list_of_counties = FXCollections.observableArrayList();
    private ObservableList list_of_forms = FXCollections.observableArrayList();

    //Called to initialize a controller after its root element has been completely processed.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       loadComboBox(this.County);
        FormComboBox();
        setLastAdmissionNumber();
    }


    //This method is used to Add items to the County ChoiceBox
    public void loadComboBox(ComboBox County){
        list_of_counties.removeAll( list_of_counties );
        String counties[]={"Mombasa", "Kwale", "Kilifi", "Tana River","Lamu", "Taita–Taveta", "Garissa", "Wajir",
                "Mandera", "Marsabit", "Isiolo", "Meru","Tharaka-Nithi","Embu", "Kitui", "Machakos", "Makueni", "Nyandarua",
               "Nyeri", "Kirinyaga", "Murang'a", "Kiambu", "Turkana",
                "West Pokot", "Samburu", "Trans-Nzoia", "Uasin Gishu",
                "Elgeyo-Marakwet", "Nandi", "Baringo", "Laikipia", "Nakuru", "Narok", "Kajiado",
                "Kericho","Bomet","Kakamega","Vihiga","Bungoma ","Busia ",
                "Siaya ","Kisumu","Homa Bay","Migori", "Kisii","Nyamira","Nairobi"
};

        for (String county : counties) {
            list_of_counties.addAll(county);
        }
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
        checkTextField(guardianFirstName);checkTextField(guardianLastName);
        if(checkTextField(studentFirstName) && checkTextField(studentLastName) && checkTextField(guardianFirstName) && checkTextField(guardianLastName))
        {
            dao.AdmittingNewStudents(studentFirstName.getText().trim(), studentMiddleName.getText().trim(), studentLastName.getText().trim() );
            con.close(anchorPane);
        }
        }

    public void showHint(MouseEvent mouseEvent) { tooltip.setText("The last admission number given to a student");
    }

    public void handleForm(ActionEvent actionEvent) {
        JFXAutoCompletePopup<String> autoCompletePopup=new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(County.getItems());

    autoCompletePopup.setSelectionHandler(event-> County.setValue(event.getObject()));
    }
}
package sample.controllers.admission;

import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import sample.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class admission implements Initializable {

    public AnchorPane anchorPane;
   Controller control=new Controller();

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle){
       new SlideInRight(anchorPane).play();
   }

   //changes user interface to admissionForm
    public void studentsAdmission(ActionEvent actionEvent) {
        control.changeModUi("admissionsForm");
    }

    //changes user interface to EditStudentDetails
    public void editStudentDetails(ActionEvent actionEvent) {
         control.changeUi("studentDetails");
      }

    //changes user interface to Guardian Details
    public void EditGuardianDetails(ActionEvent actionEvent) {
        control.changeUi("guardianDetails");
    }

    //changes user interface to school
    public void schoolStatistics(ActionEvent actionEvent) {
        control.changeUi("schoolStatistics");
    }
}

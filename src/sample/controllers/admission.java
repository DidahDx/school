package sample.controllers;

import javafx.event.ActionEvent;
import sample.Controller;

public class admission {

   Controller control=new Controller();

   //changes user interface to admissionForm
    public void studentsAdmission(ActionEvent actionEvent) {
        control.changeUi("admissionsForm");
    }

    //changes user interface to editStudentDetails
    public void editStudentDetails(ActionEvent actionEvent) {
         control.changeUi("studentDetails");
      }

    //changes user interface to Guardian Details
    public void EditGuardianDetails(ActionEvent actionEvent) {
        control.changeUi("guardianDetails");
    }

    //changes user interface to school
    public void schoolStatistics(ActionEvent actionEvent) {
    }
}

package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.Validation;

import java.net.URL;
import java.util.ResourceBundle;

public class signUp implements Initializable {

    @FXML public Label requiredFields;
    @FXML AnchorPane anchorPane;
    @FXML private ChoiceBox<String> roles;
    @FXML private ToggleGroup gender;
    @FXML private TextField password;
    @FXML private TextField email;
    @FXML private TextField userName;
    @FXML private TextField confirmPassword;



   private Validation valid=new Validation();
   private Controller con =new Controller();

    private ObservableList list = FXCollections.observableArrayList();

     public void handleSubmit(ActionEvent actionEvent)
     {

     if(checkTextField(password) && checkPassword(confirmPassword)){
         con.close(anchorPane);
         con.changeUi("login");
     }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      loadChoiceBox();
    }

    private void loadChoiceBox(){
        list.removeAll(list);

       String principal="Prinicipal";
       String deputy="Deputy Principal";
       String finance="Finance";
       String admissions="Admission Officer";
       String examination="Examinations Officer";

       list.addAll(principal,deputy,finance,admissions,examination);
       roles.getItems().addAll(list);
    }

    public boolean getChoiceBoxValue(){
         boolean check;
         String role=roles.getValue();

           if(role!=null) {
               System.out.println("the value is " + role);
               check=true; }
               else{
               System.out.println("The choice box must be selected ");
               check=false; }
     return check;
     }

   private boolean checkTextField(TextField text){
       boolean check;
         if(valid.validate(text.getText().trim())) {
         check=true;
             text.setStyle("-fx-prompt-text-fill:#FFB60D; ");
         }
         else{
             text.setStyle("-fx-prompt-text-fill:red;");
             check=false;
         }
      return check;
     }

    private boolean checkPassword(TextField text){
        boolean check;
        if(valid.validatePassword(text.getText().trim())) {
            check=true;
            text.setStyle("-fx-prompt-text-fill:#FFB60D; ");
        }else{
            text.setStyle("-fx-prompt-text-fill:red;");

            check=false;
        }
        return check;
    }

    //used to go back to login
    public void backToLogin(MouseEvent mouseEvent) {
        con.changeUi("login");
        con.close(anchorPane);
    }
}
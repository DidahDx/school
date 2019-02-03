package sample.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.Validation;


public class forgotPassword {


    @FXML
    Label errorLabel;
    @FXML
    private TextField textValidate;

    @FXML private AnchorPane anchorPane;

    Controller con= new Controller();

     Validation valid= new Validation();
    public void submitButton(ActionEvent actionEvent) {


          if(valid.validate(textValidate.getText().trim())){

              errorLabel.setText("Valid user name");
              errorLabel.setStyle("-fx-text-fill:green; -fx-font-size:20;");
              con.changeUi("login");
              con.close(anchorPane);

          }else{
              errorLabel.setText("Invalid User name");
              errorLabel.setStyle("-fx-text-fill:red; -fx-font-size:20;");
          }
    }

    //used to go back to login
    public void backToLogin(MouseEvent mouseEvent) {
        con.changeUi("login");
        con.close(anchorPane);
    }
}
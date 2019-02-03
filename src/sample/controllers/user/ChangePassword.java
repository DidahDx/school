package sample.controllers.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.Controller;

public class ChangePassword {

    @FXML
    public Label errorLabel;
    @FXML
    public JFXPasswordField newPassword;
    @FXML
    public JFXPasswordField confirmPassword;
    @FXML
    public JFXButton submitButton;

    Controller con = new Controller();

   @FXML AnchorPane anchorPane;
    public void subButton(ActionEvent actionEvent) {

      con.close(anchorPane);

    }
}

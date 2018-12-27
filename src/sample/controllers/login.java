package sample.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.database.DBConnector;
import sample.validation;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import org.controlsfx.control.textfield.TextFields;



import static org.controlsfx.control.textfield.TextFields.createClearableTextField;

public class login implements Initializable {

    @FXML
    AnchorPane anchorPane;
    @FXML
    Label errorLabel;
    @FXML
    JFXTextField emailTextField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    CheckBox passwordCheckBox;
    @FXML
    JFXTextField showPasswordField;

    private Controller con = new Controller();
    private validation valid =new validation();


//handles login by checking the database for correct userName and password if it is true it logs in
    public void handleLogin(ActionEvent actionEvent) {

        if(valid.validate(emailTextField.getText().trim()) && valid.validatePassword(passwordField.getText().trim())) {

            Connection connect= DBConnector.getConnection();
            try {
                Statement statement=connect.createStatement();
                String sql="select user_name,password from users_details where user_name='"+emailTextField.getText().trim()+"' and password='"+
                        passwordField.getText().trim()+"' ;";
                ResultSet results =statement.executeQuery(sql);

                if (results.next()){

                    con.changeUi("mainUi");
                    errorLabel.setText(" ");
                    con.close(anchorPane);
                }else {
                    errorLabel.setText("Wrong username or password");
                    errorLabel.setStyle("-fx-text-fill:red;");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{

            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("please sign up");
            alert.showAndWait();

        }
    }

    //changes the screen to the forgotPassword screen and closes the login screen
    public void handleForgotPassword(ActionEvent actionEvent) {

        con.changeUi("forgotPassword");
        con.close(anchorPane);
    }

  //The X used to close the login screen
    public void closeHandle(MouseEvent mouseEvent) {

        con.close(anchorPane);
    }

    //changes the screen to the signUp screen and closes the login screen
    public void handleSignUp(ActionEvent actionEvent) {

        con.changeUi("SignUp");
        con.close(anchorPane);
    }

    // Used to show and hide the Password of the user in login screen
    public void showPassword(ActionEvent actionEvent) {

      if(passwordCheckBox.isSelected()) {
          passwordField.setVisible(false);
          showPasswordField.setText(passwordField.getText().trim());
          showPasswordField.setVisible(true);
      } else{
          passwordField.setText(showPasswordField.getText().trim());
          showPasswordField.setVisible(false);
          passwordField.setVisible(true);
      }
    }

    //it initial hides the TextField, used to show the Password after the login finishes to load
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPasswordField.setVisible(false);
    }

     //set the password field text after a key is released
    public void setPassword(KeyEvent keyEvent) {
        passwordField.setText(showPasswordField.getText().trim());
    }

}
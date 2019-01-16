package sample.controllers;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.ZoomInUp;
import animatefx.animation.ZoomOutDown;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.Controller;
import sample.database.DBConnector;
import sample.Validation;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class login implements Initializable {

    public AnchorPane slashPane;
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

    static String id;
    private Controller con = new Controller();
    private Validation valid =new Validation();
//    RequiredFieldValidator requiredFieldValidator=new RequiredFieldValidator();
//    MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE);

    //it initial hides the TextField, used to show the Password after the login finishes to load
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new ZoomInUp(anchorPane).play();
        showPasswordField.setVisible(false);
//        emailTextField.getValidators().add(requiredFieldValidator);
//        requiredFieldValidator.setMessage("");
//        requiredFieldValidator.setIcon(icon);


    }

//handles login by checking the database for correct userName and password if it is true it logs in
    public void handleLogin(ActionEvent actionEvent) {

        if(valid.validate(emailTextField.getText().trim()) && valid.validatePassword(passwordField.getText().trim())) {

            Connection connect= DBConnector.getConnection();
            try {
                Statement statement=connect.createStatement();
                String sql="select user_name,password from users_details where user_name='"
                        +emailTextField.getText().trim()+"' and password='"+
                        passwordField.getText().trim()+"' ;";
                ResultSet results =statement.executeQuery(sql);

                if (results.next()){
                    id=emailTextField.getText();
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

     //set the password field text after a key is released
    public void setPassword(KeyEvent keyEvent) {
        passwordField.setText(showPasswordField.getText().trim());
    }

    //this method is used to minimise the login screen
    public void minimise(MouseEvent mouseEvent) {

        con.Minimise(anchorPane);
    }


}
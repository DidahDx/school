package sample.controllers.user;

import animatefx.animation.FadeInRight;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.dataAccessObject.user.UserDao;
import sample.model.Validation;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * THIS CLASS IS USED TO LOGIN A USER WITH THE RIGHT CREDENTIALS
 * */

public class login implements Initializable {

    @FXML
   public AnchorPane anchorPane;
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

    public static String id,role;
    private Controller con = new Controller();
    private Validation valid =new Validation();
    private UserDao userDao=new UserDao();

    //it initial hides the TextField, used to show the Password after the login finishes to load
    @Override
    public void initialize(URL location, ResourceBundle resources) {

         new FadeInRight(anchorPane).play();
        showPasswordField.setVisible(false);
    }

//handles login by checking the database for correct tUserName and password if it is true or false
    public void handleLogin(ActionEvent actionEvent) {

        ResultSet rs;
        if(valid.validate(emailTextField.getText().trim()) && valid.validatePassword(passwordField.getText().trim()))
        {
            try {
               rs=userDao.login(emailTextField.getText().trim(),passwordField.getText().trim());

                if (rs.next())
                {
                    id=emailTextField.getText();
                    role=rs.getString("role");

                    if(!role.isEmpty() && !role.matches("block")) {
                        con.changeUi("mainUi");
                        errorLabel.setText(" ");
                        con.close(anchorPane);
                    }else{
                        errorLabel.setText("Account is Blocked");
                        errorLabel.setStyle("-fx-text-fill:red;");
                    }

                }else {
                    errorLabel.setText("Wrong username or password");
                    errorLabel.setStyle("-fx-text-fill:red;");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            errorLabel.setText("Wrong username or password");
            errorLabel.setStyle("-fx-text-fill:red;");
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
        Platform.exit();
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
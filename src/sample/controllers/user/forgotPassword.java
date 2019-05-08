package sample.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.dataAccessObject.DBConnector;
import sample.model.Validation;
import sample.dataAccessObject.user.UserDao;
import sample.model.SendEmail;
import sample.model.user.GenerateRandomPassword;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class forgotPassword {


   @FXML
   Label errorLabel;
   @FXML
   private TextField textValidate;

   @FXML private AnchorPane anchorPane;

   private String EmailAddress="";
   Controller con= new Controller();
   GenerateRandomPassword genPassword=new GenerateRandomPassword();
   SendEmail sendEmail=new SendEmail();
   UserDao userDao=new UserDao();

   Validation valid= new Validation();
   public void submitButton(ActionEvent actionEvent) {

      String password=genPassword.RandomPassword();
      String passwordMessage="";
      Alert alert=new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      Connection connection= DBConnector.getConnection();
      if(valid.validate(textValidate.getText().trim())){

         try {
            ResultSet rs=userDao.ReadEmail(textValidate.getText().trim(),connection);
            while(rs.next()){
               EmailAddress=rs.getString("email");
            }

            if(!EmailAddress.isEmpty()){
               passwordMessage="\n YOUR NEW PASSWORD IS:  "+ password;
               passwordMessage+="\n AFTER LOG IN YOU SHOULD CHANGE YOUR PASSWORD";
               if (sendEmail.sendEmailMessage("PASSWORD RESET",passwordMessage,EmailAddress)){


                  userDao.UpdatePassword(password,textValidate.getText().trim(),connection);
                  alert.setContentText("CHECK YOUR EMAIL A RESET PASSWORD HAS BEEN SENT");
                  alert.showAndWait();
                  errorLabel.setText("Valid user name");
                  errorLabel.setStyle("-fx-text-fill:green; -fx-font-size:20;");
                  con.changeModUi("login");
                  con.close(anchorPane);


               }
            }else{
               errorLabel.setText("Invalid User name");
               errorLabel.setStyle("-fx-text-fill:red; -fx-font-size:20;");
            }
         } catch (SQLException e) {
            e.printStackTrace();
            alert.setContentText("Failed to Update database try again");
            alert.showAndWait();
         }finally {
            if (connection!=null){
               try {
                  connection.close();
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            }
         }
      }else{
         errorLabel.setText("Invalid User name");
         errorLabel.setStyle("-fx-text-fill:red; -fx-font-size:20;");
      }
   }

   //used to go back to login
   public void backToLogin(MouseEvent mouseEvent) {
      con.changeModUi("login");
      con.close(anchorPane);
   }
}
package sample.controllers.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.controllers.mainUi;
import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.user.UserDao;
import sample.model.Validation;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class UserProfile implements Initializable {

   public JFXTextField firstName;
   public JFXTextField secondName;
   public JFXTextField email;
   public JFXTextField UserName;
   public JFXPasswordField password;
   public JFXPasswordField confirmPassword;
   public Label genderLabel;
   public JFXButton editButton;
   public JFXButton saveButton;
   public JFXTextField PhoneNumber;
   public ComboBox genderComboBox;

   private int idUser;
   Validation valid=new Validation();
   UserDao userDao=new UserDao();
   ObservableList genderList= FXCollections.observableArrayList();

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      editButton.setVisible(true);
      saveButton.setVisible(false);
      LoadData();
      LoadGender();
      SetEdit(false);
   }

   private void LoadGender(){
      String [] g={"male","female","other"};
      genderList.removeAll(genderList);
      Collections.addAll(genderList,g);

      genderComboBox.getItems().addAll(genderList);
   }

   public UserProfile(){
      idUser=login.User_id;
   }

   public void Save(ActionEvent actionEvent) {
  String g="";
      Connection connection=DBConnector.getConnection();
      try {
         if (genderComboBox.getValue()=="male");
         {
            g="male";
         }
         if (genderComboBox.getValue()=="female"){
            g="female";
         }
         if (genderComboBox.getValue()=="other"){
            g="other";
         }

      if (CheckAllField()){
         if (password.getText().trim().matches(confirmPassword.getText().trim())){
            userDao.Update(firstName.getText().trim(),secondName.getText().trim(),email.getText(),
                    Integer.parseInt(PhoneNumber.getText().trim()),g,password.getText().trim(),
                    UserName.getText().trim(),idUser,connection);

         SetEdit(false);
         editButton.setVisible(true);
         saveButton.setVisible(false);

         Alert alt1=new Alert(Alert.AlertType.INFORMATION);
         alt1.setHeaderText(null);
         alt1.setContentText("Details saved successfully ");
         alt1.showAndWait();
         }else{
            Alert alt1=new Alert(Alert.AlertType.WARNING);
            alt1.setHeaderText(null);
            alt1.setContentText("Your passwords do not match. Try again");
            alt1.showAndWait();
         }

      } else{
         Alert alt1=new Alert(Alert.AlertType.WARNING);
         alt1.setHeaderText(null);
         alt1.setContentText("All fields in red have invalid inputs. Try again");
         alt1.showAndWait();
      }
      } catch (SQLException e) {
            e.printStackTrace();
         }finally {
         if (connection!=null){
            try {
               connection.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }

   }

   public void Edit(ActionEvent actionEvent) {
      editButton.setVisible(false);
      saveButton.setVisible(true);
      SetEdit(true);
   }

   //used to load user data
   private void LoadData(){
      Connection connection= DBConnector.getConnection();
      try {
         ResultSet rs=null;
         rs= userDao.Read(idUser,connection);

         while(rs.next()){
            firstName.setText( rs.getString("first_name"));
            secondName.setText(rs.getString("second_name"));
            email.setText(rs.getString("email"));
            PhoneNumber.setText("0"+ rs.getInt("phone_number"));
            genderComboBox.setValue(rs.getString("gender"));
            password.setText(rs.getString("password"));
            confirmPassword.setText(rs.getString("password"));
            UserName.setText(rs.getString("user_name"));

         }

      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         if (connection!=null){
            try {
               connection.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }

   }


   //used to validate the Emails
   private boolean CheckEmail(TextField text){
      boolean check;
      if(valid.validateEmail(text.getText().trim())){
         text.setStyle("-fx-prompt-text-fill:black; ");
         check=true;
      }else{
         check=false;
         text.setStyle("-fx-prompt-text-fill:red;");
      }

      return check;
   }

   //used to validate the phone number
   private boolean CheckPhoneNumber(TextField text){
      boolean check;

      if(valid.validatePhoneNumber(text.getText().trim())){
         text.setStyle("-fx-prompt-text-fill:black; ");
         check=true;
      }else{
         check=false;
         text.setStyle("-fx-prompt-text-fill:red;");
      }

      return check;
   }

   //used to validate the username
   private boolean CheckUserName(TextField text){
      boolean check;

      if(valid.validateAlphaNumericValues(text.getText().trim())){
         text.setStyle("-fx-prompt-text-fill:black; ");
         check=true;
      }else{
         check=false;
         text.setStyle("-fx-prompt-text-fill:red;");
      }

      return check;
   }

   //used to validate names
   private boolean checkTextField(TextField text){
      boolean check;
      if(valid.validateLetters(text.getText().trim())) {
         check=true;
         text.setStyle("-fx-prompt-text-fill:#FFB60D; ");
      }
      else{
         text.setStyle("-fx-prompt-text-fill:red;");
         check=false;
      }
      return check;
   }

   //used to validate the password
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

   //used to check the gender
   private boolean CheckGender(){
      boolean check;
      if (genderComboBox.getValue()!=null){
         genderLabel.setStyle("-fx-text-fill: black");
         check=true;
      }else{
         genderLabel.setStyle("-fx-text-fill: red");
         check=false;
      }
      return check;
   }

   //used to validate all the fields
   private boolean CheckAllField(){
      boolean check;
      if(CheckEmail(email)&& CheckPhoneNumber(PhoneNumber) && CheckUserName(UserName) &&checkTextField(firstName)
              && checkTextField(secondName) && checkPassword(password)&& checkPassword(confirmPassword) && CheckGender()){
         check=true;
      }else {
         check=false;
      }

      CheckEmail(email); CheckPhoneNumber(PhoneNumber); CheckUserName(UserName); checkTextField(firstName);
      checkTextField(secondName); checkPassword(password); checkPassword(confirmPassword);
      CheckGender();

      return check;
   }


   //used to set the textFields to be editable or not
   private  void SetEdit(boolean value){
      email.setEditable(value);
      PhoneNumber.setEditable(value);
      UserName.setEditable(value);
      firstName.setEditable(value);
      secondName.setEditable(value);
      password.setEditable(value);
      confirmPassword.setEditable(value);
   }

}

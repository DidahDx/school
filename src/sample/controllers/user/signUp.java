package sample.controllers.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import sample.Controller;
import sample.model.Validation;
import sample.dataAccessObject.user.UserDao;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;

public class signUp implements Initializable {

    @FXML public Label requiredFields;
    public JFXButton button;
    public JFXTextField lastName;
    public JFXTextField phoneNumber;
    public JFXRadioButton male;
    public JFXRadioButton other;
    public JFXRadioButton female;
    @FXML AnchorPane anchorPane;
    @FXML private ChoiceBox<String> roles;
    @FXML private ToggleGroup gender;
    @FXML private JFXTextField password;
    @FXML private JFXTextField email;
    @FXML private JFXTextField userName;
    @FXML private JFXTextField confirmPassword;
     @FXML  private JFXTextField FirstName;


   private Validation valid=new Validation();
   private Controller con =new Controller();
   String mGender="";
   UserDao userDao=new UserDao();

    RequiredFieldValidator requiredFieldValidator=new RequiredFieldValidator();
    MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE);


    private ObservableList list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE);
        SetError(userName,"Input Required");
        SetError(password,"Input Required");
        SetError(confirmPassword,"Input Required");
        SetError(FirstName,"Input Required");
        SetError(lastName,"Input Required");
        SetError(email,"Input Required");
    }


    public void handleSubmit(ActionEvent actionEvent)
     {

         if(male.isSelected()){
             mGender="male";
         }else if (female.isSelected()){
             mGender="female";
         }else {
             mGender="other";
         }

         if(password.getText().trim().matches(confirmPassword.getText().trim())){

     if(checkPassword(password) && checkPassword(confirmPassword)){

         try {
             userDao.Create(FirstName.getText().trim(),lastName.getText().trim(),email.getText().trim(),Integer.parseInt(phoneNumber.getText().trim()),mGender,
                     password.getText().trim(),userName.getText().trim());
             con.close(anchorPane);
             con.changeUi("login");
         } catch (SQLException e) {
             e.printStackTrace();
         }
         con.close(anchorPane);
         con.changeUi("login");
     }
         }else{
             SetErrorPassword(password," Password do not match");
         }

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

    public void recommend(KeyEvent keyEvent) {
         SetPopup(lastName,"Only Alphabets Allowed");
    }

    public void recommend1(KeyEvent keyEvent) {
        SetPopup(FirstName,"Only Alphabets Allowed");
    }


    public void SetPopup(Node node,String text){

        Label a=new Label();
        a.setText(text);
        VBox vbox=new VBox(a);

        PopOver pop3=new PopOver(vbox);
        pop3.show(node);
    }

    public void userRecommend(KeyEvent keyEvent) {
         SetPopup(userName,"Only Alphanumerical values");
    }

    public void passwordRecommend(KeyEvent actionEvent) {
         SetPopup(password,"A password should contain:\n At least six characters \n At least one UpperCase letter" +
                 " \n At least one LowerCase letter \n At least one Number \nAt least one special character, supported special characters eg. @#$% ");
    }

    public void confirmPasswordRecommend(KeyEvent keyEvent) {

        SetPopup(confirmPassword,"A password should contain:\n At least six characters \n At least one UpperCase letter" +
                " \n At least one LowerCase letter \n At least one Number \nAt least one special character, supported special characters eg. @#$% ");
    }

    public void SetError(JFXTextField text,String errorMessage){
        text.getValidators().add(requiredFieldValidator);
        requiredFieldValidator.setMessage(errorMessage);
        requiredFieldValidator.setIcon(icon);
        text.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if(!newValue){
                text.validate();
            }
        });
    }

    public void SetErrorPassword(JFXTextField text,String errorMessage){
        text.getValidators().add(requiredFieldValidator);
        requiredFieldValidator.setIcon(icon);
        requiredFieldValidator.setMessage(errorMessage);
        text.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if(!newValue){
                text.validate();
            }
        });
    }
}
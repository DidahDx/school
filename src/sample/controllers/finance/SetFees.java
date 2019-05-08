package sample.controllers.finance;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.controllers.admission.admissionsForm;
import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.finance.SetFeesDao;
import sample.model.Validation;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class SetFees implements Initializable {


    public TextField term1;
    public TextField term2;
    public TextField term3;
    public JFXComboBox forms;
    public JFXButton Update;
    public JFXComboBox studentType;
    public JFXButton addFees;

    admissionsForm adForm=new admissionsForm();
    SetFeesDao setFeesDao=new SetFeesDao();
    ObservableList student_type= FXCollections.observableArrayList();
    Validation validation=new Validation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
           adForm.FormComboBox(forms);
           setComboBox(studentType);

    }

    //this method set the student type comboBox
    public void setComboBox(JFXComboBox combo){
        student_type.removeAll(student_type);
        String[] studentType={"Boarder","DayScholar"};

        Collections.addAll(student_type,studentType);
        combo.getItems().addAll(student_type);
    }


    public void handlesForms(ActionEvent actionEvent){
        term3.setText(null);
        term2.setText(null);
        term1.setText(null);

         Connection connection=DBConnector.getConnection();
       try{
           ResultSet rs=setFeesDao.ViewFees(Integer.parseInt(forms.getValue().toString()),connection);

           while (rs.next()){
               if(studentType.getValue()=="DayScholar"){

                  term1.setText(String.valueOf(rs.getDouble("term_one")));
                   term2.setText(String.valueOf(rs.getDouble("term_two")));
                   term3.setText(String.valueOf(rs.getDouble("term_three")));

               }
               else if (studentType.getValue()=="Boarder"){
                   term1.setText(String.valueOf(rs.getDouble("boarder_term_one")));
                   term2.setText(String.valueOf(rs.getDouble("boarder_term_two")));
                   term3.setText(String.valueOf(rs.getDouble("boarder_term_three")));
               }
           }
       }catch (SQLException e){
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

    //this handles Update
    public void handleUpdate(ActionEvent actionEvent) {
       if(CheckAllFields()){
 Connection connection=DBConnector.getConnection();
     try {
         if (studentType.getValue()=="DayScholar"){
       setFeesDao.UpdateFees(Double.valueOf(term1.getText()), Double.valueOf(term2.getText().trim()),
               Double.valueOf(term3.getText().trim()), Integer.parseInt(forms.getValue().toString()),connection);
         }else if (studentType.getValue()=="Boarder"){
             setFeesDao.UpdateBoarderFees(Double.valueOf(term1.getText()), Double.valueOf(term2.getText().trim()),
                     Double.valueOf(term3.getText().trim()), Integer.parseInt(forms.getValue().toString()),connection);
         }
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Fees added successfully");
        alert.showAndWait();
     } catch(SQLException e){
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
       }else{
          Alert alert=new Alert(Alert.AlertType.WARNING);
          alert.setHeaderText(null);
          alert.setContentText("All fields in red have invalid inputs. Try  Again");
          alert.showAndWait();
       }

    }

    //handles Adding fees to database
    public void handleAddFees(ActionEvent actionEvent) {
       if (CheckAllFields()) {

          Connection connection = DBConnector.getConnection();
          try {
             if (studentType.getValue() == "DayScholar") {
                setFeesDao.setFees(Integer.parseInt(forms.getValue().toString()), Double.valueOf(term1.getText()), Double.valueOf(term2.getText().trim()),
                        Double.valueOf(term3.getText().trim()), connection);
             } else if (studentType.getValue() == "Boarder") {
                setFeesDao.setBoarderFees(Integer.parseInt(forms.getValue().toString()), Double.valueOf(term1.getText()),
                        Double.valueOf(term2.getText().trim()), Double.valueOf(term3.getText().trim()), connection);
             }
             Alert alert=new Alert(Alert.AlertType.INFORMATION);
             alert.setHeaderText(null);
             alert.setContentText("Fees added successfully");
             alert.showAndWait();

          } catch (SQLException e) {
             e.printStackTrace();
          } finally {
             if (connection != null) {
                try {
                   connection.close();
                } catch (SQLException e) {
                   e.printStackTrace();
                }
             }
          }
       }else{
          Alert alert=new Alert(Alert.AlertType.WARNING);
          alert.setHeaderText(null);
          alert.setContentText("All fields in red have invalid inputs. Try  Again");
          alert.showAndWait();
       }
    }


   //used to validate the amount
   boolean CheckAmount(TextField text){
      boolean check;
      if(validation.validateDouble(text.getText().trim())){
         text.setStyle("-fx-prompt-text-fill:#000; ");
         check=true;
      } else{
         text.setStyle("-fx-prompt-text-fill:red;");
         check=false;
      }
      return check;
   }

   //used to validate all fields
   private boolean CheckAllFields(){
       boolean check;
       if(CheckAmount(term1) && CheckAmount(term2) && CheckAmount( term3)){
          check=true;
       }else{
          check=false;
       }
       CheckAmount(term1);  CheckAmount(term2) ; CheckAmount( term3);

       return check;
   }

}

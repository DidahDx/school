package sample.controllers.finance;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import sample.controllers.admission.admissionsForm;
import sample.dataAccessObject.finance.SetFeesDao;

import java.net.URL;
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

       try{
           ResultSet rs=setFeesDao.ViewFees(Integer.parseInt(forms.getValue().toString()));

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
       }

    }

    //this handles Update
    public void handleUpdate(ActionEvent actionEvent) {

     try {
         if (studentType.getValue()=="DayScholar"){
       setFeesDao.UpdateFees(Double.valueOf(term1.getText()), Double.valueOf(term2.getText().trim()),
               Double.valueOf(term3.getText().trim()), Integer.parseInt(forms.getValue().toString()));
         }else if (studentType.getValue()=="Boarder"){
             setFeesDao.UpdateBoarderFees(Double.valueOf(term1.getText()), Double.valueOf(term2.getText().trim()),
                     Double.valueOf(term3.getText().trim()), Integer.parseInt(forms.getValue().toString()));
         }
     } catch(SQLException e){
       e.printStackTrace();
     }
    }

    //handles Adding fees to database
    public void handleAddFees(ActionEvent actionEvent) {
        try {
            if (studentType.getValue()=="DayScholar"){
                setFeesDao.setFees(Integer.parseInt(forms.getValue().toString()),Double.valueOf(term1.getText()), Double.valueOf(term2.getText().trim()),
                        Double.valueOf(term3.getText().trim()));
            }else if (studentType.getValue()=="Boarder"){
                setFeesDao.setBoarderFees(Integer.parseInt(forms.getValue().toString()),Double.valueOf(term1.getText()),
                        Double.valueOf(term2.getText().trim()), Double.valueOf(term3.getText().trim()));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}

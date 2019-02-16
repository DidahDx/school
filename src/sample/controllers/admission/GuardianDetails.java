package sample.controllers.admission;

 import com.jfoenix.controls.JFXButton;
 import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

 import javafx.scene.control.*;
 import javafx.scene.control.cell.PropertyValueFactory;
 import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.dataAccessObject.admission.GuardianDao;
import sample.dataAccessObject.admission.StudentDao;
 import sample.model.Validation;
 import sample.model.admission.GuardianModelTable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class GuardianDetails implements Initializable {

    public TableColumn<GuardianModelTable,String> admissionNumber;
    public TableView<GuardianModelTable> table;
    public AnchorPane anchorPane;
    public BorderPane borderPane;
    public TableColumn<GuardianModelTable,String> guardianFirstName;
    public TableColumn<GuardianModelTable,String> guardianLastName;
    public TableColumn<GuardianModelTable,String> guardianPhoneNumber;
    public TableColumn<GuardianModelTable,String> email;
    public TextField searchTextField;
    public ChoiceBox SearchForms;
    public AnchorPane AnchorPane;
    public Label NameDisplayed;
    public JFXTextField eAdmissionNumber;
    public JFXTextField eStudentName;
    public JFXTextField eGuardianFirstName;
    public JFXTextField eGuardianLastName;
    public JFXTextField ePhoneNumber;
    public JFXTextField eEmail;
    public JFXButton eGuardianClear;
    public JFXButton eAddGuardian;
    public JFXButton eUpdate;
    public JFXButton eCancel;
    public JFXButton eDelete;
    public JFXButton eEdit;

    private ObservableList<GuardianModelTable> oblist= FXCollections.observableArrayList();
    private StudentDao dao=new StudentDao();
    private GuardianDao gDao=new GuardianDao();
    private ObservableList forms=FXCollections.observableArrayList();
    private studentDetails student=new studentDetails();
    private Validation validate=new Validation();
    int guardianId=0;  String emailHolder="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        student.fillForm(forms,SearchForms); //fill the choiceBox with the options
        loadTable(); //initial fills the table
        eStudentName.setEditable(false);
         forButtonVisibility(true);
    }

    //this method is used to search for guardians using students admission number
    public void search() {
   ResultSet rs;

        try {
        if(!(searchTextField.getText().trim().isEmpty())) {
            rs = gDao.searchGuardianTable(Integer.parseInt(searchTextField.getText().trim()));
        }else{
            rs=gDao.loadGuardianTable();
        }
        
        fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this get the values of the table
    public void selected() {
        ResultSet rs,rsId;
        GuardianModelTable  guardianModelTable=table.getSelectionModel().getSelectedItem();
       NameDisplayed.setText(guardianModelTable.getGuardianFirstName()+" "+guardianModelTable.getGuardianLastName());
       eAdmissionNumber.setText(String.valueOf(guardianModelTable.getAdmissionNumber()));
       eGuardianFirstName.setText(guardianModelTable.getGuardianFirstName());
       eGuardianLastName.setText(guardianModelTable.getGuardianLastName());
       eEmail.setText(guardianModelTable.getEmail());
       ePhoneNumber.setText(String.valueOf(guardianModelTable.getPhoneNumber()));
       emailHolder=guardianModelTable.getEmail();

        String firstName = null,lastName=null;
        try {
          rsId=gDao.checkGuardianEmail(guardianModelTable.getEmail());
            rs= dao.loadStudentsName(guardianModelTable.getAdmissionNumber());
        while(rs.next()){
            firstName=rs.getString("students_details.first_name");
             lastName=rs.getString("students_details.last_name");
        }

        while (rsId.next()){
            guardianId=rsId.getInt("guardian_id");
        }


        eStudentName.setText(firstName+" "+lastName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        edit(false);
    }

    //this loads the whole table
    private void loadTable(){
    ResultSet rs;

        try {
            rs=gDao.loadGuardianTable();
            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method loads the guardians for a particular form
    public void loadForms(ActionEvent actionEvent) {
        ResultSet rs;
     if(!(SearchForms.getValue().toString()== "All")) {
         try {
             rs = gDao.loadGuardianForms(Integer.parseInt(SearchForms.getValue().toString()));
             fillTable(rs);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     } else{
         try {
             rs=gDao.loadGuardianTable();
             fillTable(rs);
            } catch (SQLException e) {
             e.printStackTrace();
           }
        }
    }


    //this method fill the table
    private void fillTable(ResultSet rs) throws SQLException{
        oblist.removeAll(oblist);

        while(rs.next()) {
            oblist.add(new GuardianModelTable(rs.getString("guardian_first_name"),
                    rs.getString("guardian_last_name"), rs.getString("guardian_email")
                    , rs.getString("guardian_phone_number"), rs.getInt("admission_number")));
        }
            guardianFirstName.setCellValueFactory(new PropertyValueFactory<>("guardianFirstName"));
            guardianLastName.setCellValueFactory(new PropertyValueFactory<>("guardianLastName"));
            guardianPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            admissionNumber.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));

            table.setItems(oblist);
    }

    //this handles adding a guardian details
    public void AddNewGuardian(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(Content("ADDED"));
        if(validate.validatePhoneNumber(ePhoneNumber.getText().trim())){

            if( !(emailHolder.matches(eEmail.getText().trim()))){
            Optional<ButtonType> answer = alert.showAndWait();

            if (answer.get() == ButtonType.OK)
            {
                  gDao.AddGuardianDetails(eGuardianFirstName.getText().trim(), eGuardianLastName.getText().trim(),
                    eEmail.getText().trim(), Integer.parseInt(ePhoneNumber.getText().trim()),
                    Integer.parseInt(eAdmissionNumber.getText()));
            forButtonVisibility(true);
            }
            }
        }
        edit(false);
        loadTable();
    }

    //this method clear the fields
    public void Clear(){
        forButtonVisibility(false);
        eUpdate.setVisible(false);
        edit(true);
        eEmail.setText(null);
        ePhoneNumber.setText(null);
        eGuardianFirstName.setText(null);
        eGuardianLastName.setText(null);
        eAdmissionNumber.setText(null);
        eStudentName.setText(null);
        NameDisplayed.setText(null);
    }

    //this method set the the fields to editable
    public void edit(boolean value) {
        eGuardianFirstName.setEditable(value);
        ePhoneNumber.setEditable(value);
        eGuardianLastName.setEditable(value);
        eEmail.setEditable(value);
        eAdmissionNumber.setEditable(value);
    }

    //this updates guardian details
    public void update(ActionEvent actionEvent) {
    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
    alert.setHeaderText(null);
    alert.setContentText(Content("UPDATED"));
        Optional<ButtonType> answer= alert.showAndWait();

        if(answer.get() ==ButtonType.OK){
            gDao.UpdateGuardianDetails(eGuardianFirstName.getText().trim(), eGuardianLastName.getText().trim(),
                    eEmail.getText().trim(), Integer.parseInt(ePhoneNumber.getText().trim()),guardianId);
       forButtonVisibility(true);
        }
        edit(false);
        loadTable();
    }

    //this sets the details to be confirmed
    public String Content(String message){
        String details=  "\t\t\t Confirm the Details below will be "+ message +
                "\n\n \t\t\t Guardian Details "+
                "\n========================================"+
                " \n Guardian First Name:\t\t\t" + eGuardianFirstName.getText() +
                "\n Guardian Last Name:\t\t\t" + eGuardianLastName.getText() +
                "\n Guardian Email:\t\t\t\t" + eEmail.getText() +
                "\n Guardian Phone Number:\t\t"+ ePhoneNumber.getText()+"\n\n";

        return details;
    }

   //set the fields editable
    public void editable(ActionEvent actionEvent) {
        forButtonVisibility(false);
        eAddGuardian.setVisible(false);
        edit(true);
    }

    //this is used to delete data from the table
    public void delete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(Content(" DELETED "));

            Optional<ButtonType> answer = alert.showAndWait();

            if (answer.get() == ButtonType.OK) {
                try {
                    gDao.deleteGuardian(guardianId);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        edit(false);
        loadTable();
    }

    //this method is used to set visibility
    public void forButtonVisibility(boolean value){
        eAddGuardian.setVisible(!value);
        eCancel.setVisible(!value);
        eUpdate.setVisible(!value);
        eGuardianClear.setVisible(value);
        eDelete.setVisible(value);
        eEdit.setVisible(value);
    }


    public void cancel(ActionEvent actionEvent) {
        forButtonVisibility(true);
    }
}
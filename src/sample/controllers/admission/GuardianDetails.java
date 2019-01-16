package sample.controllers.admission;

 import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.database.DataAccessObject;
import sample.model.admission.GuardianModelTable;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private ObservableList<GuardianModelTable> oblist= FXCollections.observableArrayList();
    private DataAccessObject dao=new DataAccessObject();
    private ObservableList forms=FXCollections.observableArrayList();
   private studentDetails student=new studentDetails();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        student.fillForm(forms,SearchForms); //fill the choiceBox with the options
        loadTable(); //initial fills the table
    }

    //this method

    public void search() {
    }

    public void selected() {
    }

    //this loads the whole table
    private void loadTable(){
    ResultSet rs;

        try {
            rs=dao.loadGuardianTable();
            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadForms(ActionEvent actionEvent) {
        ResultSet rs;
     if(!(SearchForms.getValue().toString()== "All")) {
         try {
             rs = dao.loadGuardianForms(Integer.parseInt(SearchForms.getValue().toString()));
             fillTable(rs);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     else{

         try {
             rs=dao.loadGuardianTable();
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
            oblist.add(new GuardianModelTable(rs.getString("guardian_first_name"), rs.getString("guardian_last_name"), rs.getString("guardian_email")
                    , rs.getString("guardian_phone_number"), rs.getInt("admission_number")));
        }
            guardianFirstName.setCellValueFactory(new PropertyValueFactory<>("guardianFirstName"));
            guardianLastName.setCellValueFactory(new PropertyValueFactory<>("guardianLastName"));
            guardianPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            admissionNumber.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));

            table.setItems(oblist);


    }

}
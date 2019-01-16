package sample.controllers.admission;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.Controller;
import sample.database.DataAccessObject;
import sample.model.MyPrinter;
import sample.model.admission.StudentModelTable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class studentDetails implements Initializable {

    @FXML public TextField searchTextField;
    @FXML public AnchorPane anchorPane;
    @FXML public ChoiceBox SearchForms;
    @FXML public Label NameDisplayed;
    @FXML public JFXTextField eFirstName;
    @FXML public JFXTextField eLastName;
    @FXML public JFXTextField eSecondName;
    @FXML public JFXTextField eAdmissionNumber;
    @FXML public ComboBox<String> eGender,eCounty;
    @FXML public AnchorPane AnchorPane;
    @FXML public BorderPane borderPane;
    @FXML TableView<StudentModelTable> table;
    @FXML TableColumn<StudentModelTable,String> admissionNumber;
    @FXML TableColumn<StudentModelTable,String> firstName;
    @FXML TableColumn<StudentModelTable,String> secondName;
    @FXML TableColumn<StudentModelTable,String> lastName;
    @FXML TableColumn<StudentModelTable,String> stream;
    @FXML TableColumn<StudentModelTable,String> form;
    @FXML TableColumn<StudentModelTable,String> gender;

    Controller controller=new Controller();
    private ObservableList<StudentModelTable> oblist= FXCollections.observableArrayList();
    private DataAccessObject dao=new DataAccessObject();
    private ObservableList forms=FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllForms();
        fillForm(forms,SearchForms);
        eLastName.setEditable(false);
        eSecondName.setEditable(false);
        eAdmissionNumber.setEditable(false);
        eFirstName.setEditable(false);

    }

    //this loads all the forms table
    private void loadAllForms(){
        ResultSet rs;
        try {
            rs=dao.loadStudentTable();
            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this loads the choiceBox
    public void fillForm(ObservableList<String> forms,ChoiceBox SearchForms){
        forms.removeAll(forms);

        String f []={"1","2","3","4","All"};
       for(int i=0;i<f.length;i++){
            forms.add(f[i]);
        }

        SearchForms.getItems().addAll(forms);
        SearchForms.setValue(f[4]);

    }

    //handles the search button
    public void search(ActionEvent actionEvent) {

        ResultSet rs = null;
        try {

            if(!(searchTextField.getText().trim().isEmpty())){
             rs= dao.searchTable(Integer.parseInt(searchTextField.getText().trim()));
            }else {
               rs= dao.loadStudentTable();
            }

            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to load the whole table
    public void loadTable(ActionEvent actionEvent) {
        ResultSet rs;
        try {
            if(SearchForms.getValue().toString()== "All"){
                rs=dao.loadStudentTable();
            }
            else{ rs= dao.loadsForms( Integer.parseInt(SearchForms.getValue().toString()));}

            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to fill the table when called
    private void fillTable(ResultSet rs) throws SQLException {
            oblist.removeAll(oblist);

            while(rs.next()){
                    oblist.add(new StudentModelTable(rs.getString("first_name"),rs.getString("second_name"),
                            rs.getString("stream"), rs.getInt("form"),
                            rs.getString("gender"),rs.getInt("admission_number"),
                            rs.getString("last_name")));
            }

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        secondName.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        stream.setCellValueFactory(new PropertyValueFactory<>("stream"));
        form.setCellValueFactory(new PropertyValueFactory<>("form"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        admissionNumber.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        table.setItems(oblist);
    }


    //this get the row data from the table when its selected
    public void selected() {
        StudentModelTable studentModelTable = table.getSelectionModel().getSelectedItem();
        NameDisplayed.setText(studentModelTable.getFirstName()+"  "+ studentModelTable.getSecondName());
        eFirstName.setText(studentModelTable.getFirstName());
        eSecondName.setText(studentModelTable.getSecondName());
        eLastName.setText(studentModelTable.getLastName());
        eAdmissionNumber.setText(Integer.toString(studentModelTable.getAdmissionNumber()));
        eGender.setValue(studentModelTable.getGender());

    }

    //this makes the fields editable
    public void edit() {
        eLastName.setEditable(true);
        eSecondName.setEditable(true);
        eFirstName.setEditable(true);

    }

    public void printTable(ActionEvent actionEvent) {
     new MyPrinter().Print(table);
    }
}
package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Controller;
import sample.database.DataAccessObject;
import sample.database.modelTable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class studentDetails implements Initializable {

    public TextField searchTextField;
    public AnchorPane anchorPane;
    public TableColumn admissionNumber;
    public ChoiceBox SearchForms;

    @FXML
    TableView<modelTable> table;

    @FXML
    TableColumn<modelTable,String> firstName;

    @FXML
    TableColumn<modelTable,String> secondName;

    @FXML
    TableColumn<modelTable,String> stream;

    @FXML
    TableColumn<modelTable,String> form;

    @FXML
    TableColumn<modelTable,String> gender;

    Controller controller=new Controller();
    private ObservableList<modelTable> oblist= FXCollections.observableArrayList();
    private DataAccessObject dao=new DataAccessObject();
    private ObservableList forms=FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
   loadAllForms();
    fillForm();
    }

    //this loads all the forms table
    private void loadAllForms(){
        ResultSet rs;
        try {
            rs=dao.loadTable();
            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this loads the combobox
    private void fillForm(){
        forms.removeAll(forms);
        String a="1";
        String b="2";
        String c="3";
        String d="4";
        String e="All";
        forms.addAll(a,b,c,d,e);
        SearchForms.getItems().addAll(forms);
    }

    //handles the search button
    public void search(ActionEvent actionEvent) {

        ResultSet rs = null;
        try {

            if(!(searchTextField.getText().trim().isEmpty())){
             rs= dao.searchTable(Integer.parseInt(searchTextField.getText().trim()));
            }else {
              rs= dao.loadTable();
            }

            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadTable(ActionEvent actionEvent) {
        ResultSet rs;
        try {
            if(SearchForms.getValue().toString()== "All"){
                rs=dao.loadTable();
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
                    oblist.add(new modelTable(rs.getString("first_name"),rs.getString("second_name"),
                            rs.getString("stream"), rs.getInt("form"),
                            rs.getString("gender"),rs.getInt("admission_number")));
            }

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        secondName.setCellValueFactory(new PropertyValueFactory<>("secondName"));
        stream.setCellValueFactory(new PropertyValueFactory<>("stream"));
        form.setCellValueFactory(new PropertyValueFactory<>("form"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        admissionNumber.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));

        table.setItems(oblist);
    }

}
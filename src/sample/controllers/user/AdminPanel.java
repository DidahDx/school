package sample.controllers.user;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.dataAccessObject.user.UserDao;
import sample.model.user.UserModelTable;

import java.net.URL;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class AdminPanel implements Initializable {

    public TableView<UserModelTable> table;
    public TableColumn<UserModelTable,String> tUserName;
    public JFXTextField userId;
    public JFXTextField FirstName;
    public JFXTextField SecondName;
    public JFXTextField phoneNumber;
    public JFXTextField email;
    public ComboBox privilege;
    public ComboBox gender;
    public Label userName;
    int Id;

    private UserDao userDao=new UserDao();
   private ObservableList user_names= FXCollections.observableArrayList();
    private ObservableList list_role= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTable();
        fillPrivilegeComboBox(privilege);
    }

    //this method is used to fill the privilege combobox
    public void fillPrivilegeComboBox(ComboBox list)
    {
        list_role.removeAll(list_role);

        String[] i={"examAdmin","admin","admissionAdmin","financeAdmin","block"};
        Collections.addAll(list_role,i);
        list.getItems().addAll(list_role);
    }

    //this method is used to get the selected users details
    public void selected(MouseEvent mouseEvent) {
        UserModelTable user=table.getSelectionModel().getSelectedItem();
       Id= user.getUserIdl();
        userId.setText(String.valueOf(Id));
       userName.setText(user.getUserName());
        ResultSet rs;
        try {
          rs=  userDao.Read(Id);
          while(rs.next()){
              FirstName.setText(rs.getString("first_name"));
              SecondName.setText(rs.getString("second_name"));
              email.setText(rs.getString("email"));
              phoneNumber.setText(String.valueOf(rs.getInt("phone_number")));
              gender.setValue(rs.getString("gender"));
              privilege.setValue(rs.getString("role"));
          }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used get the table data
    public void loadTable(){
        try {
           ResultSet rs= userDao.ReadAll();
           fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to fill the table
    public void fillTable(ResultSet rs) throws SQLException {
        user_names.removeAll(user_names);

        while(rs.next()){
            user_names.add(new UserModelTable(rs.getString("user_name"),rs.getInt("user_id")));
        }

        tUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        table.setItems(user_names);
    }

    //this method is used to set privilege
    public void submit(ActionEvent actionEvent) {
        try {
            userDao.UpdatePrivilege(privilege.getValue().toString(),Integer.parseInt(userId.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Added Privilege successfully");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method id used to delete
    public void deleteUser(ActionEvent actionEvent) {

        try {
            userDao.Delete(Id);

            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("User deleted successfully");
            alert.showAndWait();
            loadTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

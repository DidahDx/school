package sample.controllers.user;

import com.jfoenix.controls.JFXTextField;
import com.sun.corba.se.impl.encoding.CodeSetConversion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.user.UserDao;
import sample.model.user.UserModelTable;

import java.net.URL;
import java.security.Security;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
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
       Connection connection= DBConnector.getConnection();
        try {
          rs=  userDao.Read(Id,connection);
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

    //this method is used get the table data
    public void loadTable(){
       Connection connection=DBConnector.getConnection();
        try {
           ResultSet rs= userDao.ReadAll(connection);
           fillTable(rs);
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
       Connection connection=DBConnector.getConnection();
        try {
           Alert alert1= new Alert(Alert.AlertType.CONFIRMATION);
           alert1.setHeaderText(null);
           alert1.setContentText("Are you sure you want to set user "+ FirstName.getText()+" "+SecondName.getText()+
                   "\n To be a/an "+privilege.getValue()+" ?");
           Optional<ButtonType>answer= alert1.showAndWait();
           if (answer.get()==ButtonType.OK) {
              userDao.UpdatePrivilege(privilege.getValue().toString(), Integer.parseInt(userId.getText()), connection);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setHeaderText(null);
              alert.setContentText("Privilege saved successfully");
              alert.showAndWait();
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

    //this method id used to delete
    public void deleteUser(ActionEvent actionEvent) {
      Connection connection=DBConnector.getConnection();
        try {
           Alert alert1= new Alert(Alert.AlertType.CONFIRMATION);
           alert1.setHeaderText(null);
           alert1.setContentText("Are you sure you want to delete user "+ FirstName.getText()+" "+SecondName.getText());
          Optional<ButtonType>answer= alert1.showAndWait();
           if (answer.get()==ButtonType.OK) {
              userDao.Delete(Id, connection);

              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setHeaderText(null);
              alert.setContentText("User deleted successfully");
              alert.showAndWait();
              loadTable();
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
           if (connection!=null){
              try {
                 connection.close();
              } catch (SQLException e) {
                 e.printStackTrace();
              }
           }
        }
    }
}

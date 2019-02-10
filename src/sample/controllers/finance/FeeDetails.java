package sample.controllers.finance;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sample.controllers.admission.studentDetails;
import sample.dataAccessObject.admission.StudentDao;
import sample.dataAccessObject.finance.FeeDao;
import sample.model.finance.FinanceModelTable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class FeeDetails implements Initializable {
    public TableView<FinanceModelTable> FeeTable;
    public TableColumn<FinanceModelTable,Integer> admission;
    public TableColumn<FinanceModelTable,Double> feeExpected;
    public TableColumn<FinanceModelTable,Double> FeePaid;
    public TableColumn<FinanceModelTable,LocalDate> DateOfPayment;
    public TableColumn<FinanceModelTable,String> bankTransactionId;
    public TableColumn<FinanceModelTable,Double> feeBalance;
    public TableColumn<FinanceModelTable,Time> TimeOfPayment;
    public TableColumn<FinanceModelTable,Integer> Term;
    public TextField searchTextField;
    public JFXTextField eAdmissionNumber;
    public JFXTextField eFeeBalance;
    public JFXTextField eFeePaid;
    public JFXTextField eFeeExpected;
    public JFXDatePicker eDateOfPayment;
    public JFXTextField eBankTransactionId;
    public Label NameDisplayed;
    public ChoiceBox<String> form;
    public TableColumn<FinanceModelTable,Integer> financeId;
    public TableColumn<FinanceModelTable,Integer> tForm;
    public TableColumn<FinanceModelTable,String> bankName;


    private ObservableList<FinanceModelTable> oblist= FXCollections.observableArrayList();
    FeeDao feeDao=new FeeDao();
    StudentDao studentDao=new StudentDao();
    studentDetails stDetails=new studentDetails();
    ObservableList LForm=FXCollections.observableArrayList();
    private int finance_id=0;   String name="",secondName="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stDetails.fillForm(LForm,form);
           loadFinanceTable();
    }

    //this method is used to load the table
    public void loadFinanceTable(){

        try {
           ResultSet rs= feeDao.loadTable();
           fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to fill the table
    public void fillTable(ResultSet rs) throws SQLException {
        oblist.removeAll(oblist);
        while(rs.next()){
            oblist.add(new FinanceModelTable(rs.getInt("admission_number"),rs.getString("bank_transaction_id"),rs.getDouble("fee_expected")
            ,rs.getDouble("fee_paid"),rs.getDouble("balance"),
                    rs.getInt("term"),rs.getTime("time_of_payment"),rs.getDate("date_of_payment"),rs.getInt("finance_id")
                    ,rs.getInt("form"),rs.getString("bank_name")));
        }

        admission.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
        bankTransactionId.setCellValueFactory(new PropertyValueFactory<>("bankTransactionId"));
        feeExpected.setCellValueFactory(new PropertyValueFactory<>("feesExpected"));
        FeePaid.setCellValueFactory(new PropertyValueFactory<>("feesPaid"));
        feeBalance.setCellValueFactory(new PropertyValueFactory<>("feeBalance"));
        Term.setCellValueFactory(new PropertyValueFactory<>("term"));
        TimeOfPayment.setCellValueFactory(new PropertyValueFactory<>("timeOfPayment"));
        DateOfPayment.setCellValueFactory(new PropertyValueFactory<>("dateOfPayment"));
        tForm.setCellValueFactory(new PropertyValueFactory<>("form"));
        financeId.setCellValueFactory(new PropertyValueFactory<>("financeId"));
        bankName.setCellValueFactory(new PropertyValueFactory<>("bankName"));


         FeeTable.setItems(oblist);
    }

    //this is used to search using admission number
    public void search(ActionEvent actionEvent) {

        if(!(searchTextField.getText().isEmpty())){

            try{
                ResultSet rs=feeDao.search(Integer.parseInt(searchTextField.getText()));
                fillTable(rs);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        else{
            try {
                ResultSet rs=feeDao.loadTable();
                fillTable(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSelected(){
        FinanceModelTable fmt=FeeTable.getSelectionModel().getSelectedItem();
        eAdmissionNumber.setText(String.valueOf(fmt.getAdmissionNumber()));
        eBankTransactionId.setText(fmt.getBankTransactionId());
        eDateOfPayment.setValue(fmt.getDateOfPayment().toLocalDate());
        eFeeBalance.setText(String.valueOf(fmt.getFeeBalance()));
        eFeeExpected.setText(String.valueOf(fmt.getFeesExpected()));
        eFeePaid.setText(String.valueOf(fmt.getFeesPaid()));
        finance_id=fmt.getFinanceId();
        String name="",secondName="";
        try{
          //  ResultSet rs2=feeDao.search();
        ResultSet rs=studentDao.searchTable(fmt.getAdmissionNumber());
        while (rs.next()){
           name =rs.getString("first_name");
            secondName= rs.getString("second_name");
        }
         }catch (SQLException e){
             e.printStackTrace();
         }
        NameDisplayed.setText(name+"\t"+ secondName);
    }


    public void selected(MouseEvent mouseEvent) {
        setSelected();
    }

    public void select(KeyEvent keyEvent) {
        setSelected();
    }


    public void released(KeyEvent keyEvent) {
        setSelected();
    }


    //this method loads the finance table for a specific form at a time or all at once
    public void loadForm(ActionEvent actionEvent) {

        ResultSet rs = null;
        try {
        if (form.getValue()=="All"){
            rs=feeDao.loadTable();
            fillTable(rs);
        }else {
            rs=feeDao.searchForm(Integer.parseInt(form.getValue()));
            fillTable(rs);
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        try {
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText(Content());
            Optional<ButtonType> answer=alert.showAndWait();
            if (answer.get()==ButtonType.OK){
                feeDao.deleteFees(finance_id);
                loadFinanceTable();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method set the message to be displayed before the record is deleted
    public String Content(){
        String message="Are you sure you want to delete the following details ?" +
                "\n Finance Id "+finance_id+"\n Students Name "+ name +" "+secondName ;

        return message;
    }
}

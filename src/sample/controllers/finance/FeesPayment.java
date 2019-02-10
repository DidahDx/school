package sample.controllers.finance;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import sample.model.Validation;
import sample.dataAccessObject.admission.GuardianDao;
import sample.dataAccessObject.admission.StudentDao;
import sample.dataAccessObject.finance.FeeDao;
import sample.model.MyPrinter;
import sample.model.SendEmail;
import sample.model.finance.CalculateFees;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class FeesPayment implements Initializable {

   @FXML
   public TextArea feesDetailsTextArea;
    public JFXTextField BankTransactionId;
    public JFXTextField admissionNumber;
    public JFXTextField Amount;
    public JFXTextField bankName;

    private LocalTime now=LocalTime.now();
   private LocalDate today=LocalDate.now();
    private Validation validation=new Validation();
    private StudentDao studentDao=new StudentDao();
    private CalculateFees calculateFees=new CalculateFees();
    private GuardianDao guardianDao=new GuardianDao();
    private SendEmail sEmail=new SendEmail();
    private FeeDao feeDao=new FeeDao();
     private int term,form;
     String emailAddress="  ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      feesDetailsTextArea.setEditable(false);

    }

    //this prints the fees
    public void printFees(ActionEvent actionEvent) {
     new MyPrinter().Print(feesDetailsTextArea);
    }

    //this handles payment of fees
    public void submitPayment(ActionEvent actionEvent) {
        feesDetailsTextArea.clear();
        double dbfeeExpected=0.0;
       String name= StudentName(Integer.parseInt(admissionNumber.getText()));
        Double feeExpected=calculateFees.getExpectedFees(Integer.parseInt(admissionNumber.getText())); //gets expected fees
        Double feeBalance=calculateFees.CalculateBalance(Integer.parseInt(Amount.getText()),
                Integer.parseInt(admissionNumber.getText())); //gets fee balance

        if(calculateFees.setExpectedFees(Integer.parseInt(admissionNumber.getText()))) //checks if expected fees is set
        {
             dbfeeExpected=feeExpected;
        }

        //content to display  fees details
        feesDetailsTextArea.appendText(   "\n\t\t\t Fee Payment Details "+
              "\n================================="+
              "\n Admission Number:\t"+admissionNumber.getText()+
              "\n Student Name:\t\t"+ name +
                "\n Fee Expected \n For Form "+this.form+" term "+ this.term+": \t"+feeExpected +
                "\n Bank:\t\t\t\t"+ bankName.getText()+
                "\n Amount Paid:\t\t\t"+Amount.getText()+
                "\n Bank Transaction Id:\t"+ BankTransactionId.getText()+
                "\n Fee Balance:\t\t\t"+ feeBalance +
              "\n Date Of Payment:\t\t"+ validation.changeDateFormat(today) +
              "\n Time Of Payment:\t\t"+ now.minusNanos(now.getNano()));

        try {
            feeDao.AddFee(Integer.parseInt(admissionNumber.getText()),dbfeeExpected,Double.valueOf(Amount.getText()),
                    today,feeBalance,BankTransactionId.getText(),this.term,now.minusNanos(now.getNano()),this.form,bankName.getText());

            sEmail.sendEmailMessage("Fees Details Slip",feesDetailsTextArea.getText(),emailAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to get the students details
    private String StudentName(int admission){
         String name="";
        try {
            ResultSet rs=studentDao.searchTable(admission);
            ResultSet rs1=guardianDao.searchGuardianTable(admission);
         while(rs.next()){
             name=rs.getString("first_name");
             name+="\t";
             name+=rs.getString("last_name");
             term=rs.getInt("term");
             form=rs.getInt("form");
         }

         while(rs1.next()){
             emailAddress += rs1.getString("guardian_email")+",";
         }
            emailAddress=emailAddress.substring(0,emailAddress.length()-1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}
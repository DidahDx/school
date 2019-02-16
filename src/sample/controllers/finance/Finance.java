package sample.controllers.finance;

import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import sample.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class Finance implements Initializable {

    public AnchorPane anchorPane;

    Controller con=new Controller();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
          new SlideInRight(anchorPane).play();
    }

    //this handles changing the User Interface to feePayment
    public void feePayment(ActionEvent actionEvent) {
           con.changeModUi("feesPayment");
    }

    //this handles changing the User Interface to feeDetails
    public void feeDetails(ActionEvent actionEvent) {
        con.changeUi("feeDetails");
    }

    //this handles changing the User Interface to setFees
    public void setFees(ActionEvent actionEvent) {
        con.changeModUi("setFees");
    }
}

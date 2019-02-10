package sample.controllers.examination;

import animatefx.animation.AnimationFX;
import animatefx.animation.RollIn;
import animatefx.animation.SlideInRight;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import sample.Controller;

import java.net.URL;
import java.util.ResourceBundle;


public class Examination implements Initializable {
    public AnchorPane anchorPane;
    Controller con=new Controller();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       new SlideInRight(anchorPane).play();
    }

    public void examDetails(ActionEvent actionEvent) {
    con.changeUi("examDetails");

    }

    public void ReportCard(ActionEvent actionEvent) {
        con.changeUi("reportCard");
    }
}

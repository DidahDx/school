package sample.controllers.examination;

import animatefx.animation.AnimationFX;
import animatefx.animation.RollIn;
import animatefx.animation.SlideInRight;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Examination implements Initializable {
    public AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       new SlideInRight(anchorPane).play();
    }
}

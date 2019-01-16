package sample.controllers.finance;

import animatefx.animation.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Finance implements Initializable {

    public AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
          new SlideInRight(anchorPane).play();
    }
}

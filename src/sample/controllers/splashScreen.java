package sample.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class splashScreen implements Initializable {

    public AnchorPane slashPane;

    public Controller con=new Controller();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSlashScreen();
    }

    public void loadSlashScreen(){


        FadeTransition fadeIn=new FadeTransition(Duration.seconds(4),slashPane);
        fadeIn.setFromValue(0); //
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.play();

        FadeTransition fadeOut=new FadeTransition(Duration.seconds(4),slashPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0.3);
        fadeOut.setCycleCount(1);

        fadeIn.setOnFinished(e ->{
            fadeOut.play();
        } );

        fadeOut.setOnFinished(e ->{
            con.changeUi("login");
            con.close(slashPane);
        });
    }




}

package sample;

import animatefx.animation.*;
import de.jensd.fx.glyphs.emojione.EmojiOneView;
import de.jensd.fx.glyphs.weathericons.WeatherIcon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

/**
 * THIS CLASS CONTAINS METHODS USED TO CHANGE AND CLOSE THE USER INTERFACE SCREENS
 * AND SETTING THE TITLES OF THE SCREENS
 * ALSO SETS THE STYLES OF THE SCREENS
 * **/
public class Controller {

    private Stage stage = new Stage();
 private double x,y;
    Parent root1;
    // This method is used to loads a different User Interface screens
    public void changeUi(String ui){

        FXMLLoader loadUi = new FXMLLoader(getClass().getResource("/fxml/" + ui +".fxml"));
        try {
            root1= loadUi.load();
            Scene scene= new Scene(root1);



            switch (ui) {
                case "mainUi":
                    //setting the Title of maiUi to Sigiria secondary school
                    stage.setTitle(("Sigiria secondary school").toUpperCase());
                    stage.setMaximized(true);
                    stage.setOnCloseRequest(e -> {
                        e.consume();
                        closeMainUi();

                    });
                    break;

                case "splashScreen":
                    stage.initStyle(StageStyle.UNDECORATED);
                    break;
                    
                case "login":
                    stage.initStyle(StageStyle.UNDECORATED);

                    root1.setOnMousePressed(event -> {
                        x=event.getSceneX();
                        y=event.getSceneY();
                    });

                    //  used to drag around the login screen
                    root1.setOnMouseDragged(event -> {
                        stage.setX(event.getScreenX()-x);
                        stage.setY(event.getScreenY()-y);
                    });


                    break;

                case "admissionsForm":
                    stage.setTitle(("Admissions Form").toUpperCase());
                    stage.setResizable(false);
                    stage.setMaximized(false);
                  //  stage.initModality(Modality.APPLICATION_MODAL); //blocks events from being delivered to any other application window.
                    break;
                case "studentDetails":
                      stage.setResizable(true);
                      stage.setMaximized(true);
                    stage.setTitle(("Student Details").toUpperCase());
                      break;
                case "guardianDetails":
                    stage.setResizable(true);
                    stage.setMaximized(true);
                    stage.setTitle(("Guardian Details").toUpperCase());
                    break;
              default:
                    stage.setTitle((ui).toUpperCase());
                    stage.setResizable(true);
                    break;
            }


            stage.setScene(scene);
            Image image=new Image("/images/DX9.png"); //logo icon for application
            stage.getIcons().add(image);
          stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

     //This method is used to close screens
    public void close(AnchorPane anchorPane){
        Stage stage= (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    //This method is used to close the mainUi screens
    private void closeMainUi(){

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit ? Press OK to Exit");
        alert.setHeaderText(null);

        Optional<ButtonType> answer= alert.showAndWait();

          if(answer.get() ==ButtonType.OK){
              stage.close();
        }
    }

    //This method is used to close screens
    public void close(BorderPane borderPane){
        Stage stage= (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    //this method is used to minimise a window
    public void Minimise(AnchorPane anchorPane){
        Stage stage= (Stage) anchorPane.getScene().getWindow();
        stage.setIconified(true);

    }

}
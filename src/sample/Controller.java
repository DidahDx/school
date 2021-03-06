package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
   Image image=new Image("/images/DX9.png"); //logo icon for application

   // This method is used to loads a different User Interface screens
   public void changeUi(String ui){

      FXMLLoader loadUi = new FXMLLoader(getClass().getResource("/fxml/" + ui +".fxml"));
      try {
         root1= loadUi.load();
         Scene scene= new Scene(root1);

         //the switch case is used set the Characteristics of the stage such title,initStyle,Maximized,Resizability
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

            case "login":
               stage.initStyle(StageStyle.UNDECORATED);
               stage.initStyle(StageStyle.TRANSPARENT);
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
            case "splashScreen":
               stage.initStyle(StageStyle.UNDECORATED);
               break;

            case "forgotPassword":
               stage.initStyle(StageStyle.UNDECORATED);

               root1.setOnMousePressed(event -> {
                  x=event.getSceneX();
                  y=event.getSceneY();
               });

               //  used to drag around the  screen
               root1.setOnMouseDragged(event -> {
                  stage.setX(event.getScreenX()-x);
                  stage.setY(event.getScreenY()-y);
               });

               break;

            case "SignUp":
               stage.initStyle(StageStyle.UNDECORATED);

               root1.setOnMousePressed(event -> {
                  x=event.getSceneX();
                  y=event.getSceneY();
               });

               //  used to drag around the screen
               root1.setOnMouseDragged(event -> {
                  stage.setX(event.getScreenX()-x);
                  stage.setY(event.getScreenY()-y);
               });

               break;



            case "admissionsForm":
               stage.setTitle(("Admissions Form").toUpperCase());
               stage.setResizable(false);
               stage.setMaximized(false);
               //   stage.initModality(Modality.APPLICATION_MODAL); //blocks events from being delivered to any other application window.
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

            case "feeDetails":
               stage.setResizable(false);
               stage.setMaximized(true);
               stage.setTitle(("Fee Details").toUpperCase());
               break;

            case "schoolStatistics":
               stage.setResizable(true);
               stage.setMaximized(true);
               stage.setTitle(("School Statistics").toUpperCase());
               break;

            case "examDetails":
               stage.setResizable(true);
               stage.setMaximized(true);
               stage.setTitle(("Exam Details").toUpperCase());
               break;

            case"reportCard":
               stage.setResizable(true);
               stage.setMaximized(true);
               stage.setTitle(("Report Card").toUpperCase());
               break;
            default:
               stage.setTitle((ui).toUpperCase());
               stage.setResizable(true);
               break;
         }


         stage.setScene(scene);
         stage.getIcons().add(image);
         stage.show();

      }catch (IOException e) {
         e.printStackTrace();
      }
   }

   //This method is used to close the mainUi screens
   void closeMainUi(){

      Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
      alert.setContentText("Are you sure you want to exit ? Press OK to Exit");
      alert.setHeaderText(null);

      Optional<ButtonType> answer= alert.showAndWait();

      if((answer.get() ==ButtonType.OK)){
         Platform.exit();
         stage.close();
      }
   }

   //This method is used to close screens
   public void close(Node node){
      Stage stage= (Stage) node.getScene().getWindow();
      // Platform.exit();
      stage.close();
   }

   //This method is used to logOut
   public void close(BorderPane borderPane){
      Stage stage= (Stage) borderPane.getScene().getWindow();
      stage.close();
   }

   //this method is used to minimise a window
   public void Minimise(AnchorPane anchorPane){
      Stage stage= (Stage) anchorPane.getScene().getWindow(); //get the window
      stage.setIconified(true); //minimises the window
   }

   //this method is used to change a window with the Modality.APPLICATION_MODAL
   public void changeModUi(String ui){
      Stage stage1 = new Stage();
      FXMLLoader loadUi = new FXMLLoader(getClass().getResource("/fxml/" + ui +".fxml"));
      try {
         root1= loadUi.load();
      } catch (IOException e) {
         e.printStackTrace();
      }

      switch (ui) {
         case "admissionsForm":
            stage1.setTitle(("Admissions Form").toUpperCase());
            stage1.setResizable(false);
            stage1.setMaximized(false);
            break;

         case"feesPayment":
            stage1.setTitle(("Fee Payment").toUpperCase());
            stage1.setResizable(false);
            stage1.setMaximized(false);
            break;

         case"setFees":
            stage1.setTitle("Set Fees");
            stage1.setResizable(false);
            stage1.setMaximized(false);
            break;

         case "mainUi":
            //setting the Title of maiUi to Sigiria secondary school
            stage1.setTitle(("Sigiria secondary school").toUpperCase());
            stage1.setMaximized(true);

            stage1.setOnCloseRequest(e -> {
               e.consume();
               closeMainUi();
            });
            break;

         case "login":
            stage1.initStyle(StageStyle.UNDECORATED);
            stage1.initStyle(StageStyle.TRANSPARENT);
            root1.setOnMousePressed(event -> {
               x=event.getSceneX();
               y=event.getSceneY();
            });

            //  used to drag around the login screen
            root1.setOnMouseDragged(event -> {
               stage1.setX(event.getScreenX()-x);
               stage1.setY(event.getScreenY()-y);
            });

            break;


         case "forgotPassword":
            stage1.initStyle(StageStyle.UNDECORATED);

            root1.setOnMousePressed(event -> {
               x=event.getSceneX();
               y=event.getSceneY();
            });

            //  used to drag around the  screen
            root1.setOnMouseDragged(event -> {
               stage1.setX(event.getScreenX()-x);
               stage1.setY(event.getScreenY()-y);
            });

            break;

         case "SignUp":
            stage1.initStyle(StageStyle.UNDECORATED);

            root1.setOnMousePressed(event -> {
               x=event.getSceneX();
               y=event.getSceneY();
            });

            //  used to drag around the screen
            root1.setOnMouseDragged(event -> {
               stage1.setX(event.getScreenX()-x);
               stage1.setY(event.getScreenY()-y);
            });

            break;

         case "studentDetails":
            stage1.setResizable(true);
            stage1.setMaximized(true);
            stage1.setTitle(("Student Details").toUpperCase());
            break;

         case "guardianDetails":
            stage1.setResizable(true);
            stage1.setMaximized(true);
            stage1.setTitle(("Guardian Details").toUpperCase());
            break;

         case "feeDetails":
            stage1.setResizable(false);
            stage1.setMaximized(true);
            stage1.setTitle(("Fee Details").toUpperCase());
            break;

         case "schoolStatistics":
            stage1.setResizable(true);
            stage1.setMaximized(true);
            stage1.setTitle(("School Statistics").toUpperCase());
            break;

         case "examDetails":
            stage1.setResizable(true);
            stage1.setMaximized(true);
            stage1.setTitle(("Exam Details").toUpperCase());
            break;

         case"reportCard":
            stage1.setResizable(true);
            stage1.setMaximized(true);
            stage1.setTitle(("Report Card").toUpperCase());
            break;
      }

      stage1.initModality(Modality.APPLICATION_MODAL); //blocks events from being delivered to any other application window
      Scene scene= new Scene(root1);
      stage1.setScene(scene);
      stage1.getIcons().add(image);
      stage1.show();

   }
}
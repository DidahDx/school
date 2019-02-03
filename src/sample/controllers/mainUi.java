package sample.controllers;

import animatefx.animation.SlideInRight;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import sample.Controller;
import sample.controllers.user.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainUi implements Initializable {

    public String userId;
    @FXML public MaterialDesignIconView threeDots;
    @FXML public  Label loggedIn;
    @FXML public FontAwesomeIconView user;
    @FXML private BorderPane borderPane;
    @FXML private AnchorPane AnchorPane;
    @FXML private Label showSideMenuName;
 Controller controller=new Controller();

 public mainUi() {
     userId= login.id;
 }

    //this loads the dashboard user interface to center of the borderPane of the main user interface(mainUi.fxml)
    public void dashboard(ActionEvent actionEvent) {
        try {
            loadUiTabs("dashboard");
            showSideMenuName.setText(("dashboard").toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //this loads the examination user interface to center of the borderPane of the main user interface(mainUi.fxml)
    public void exam(ActionEvent actionEvent) {
          String exam="examination";
        try {
            loadUiTabs(exam);
            showSideMenuName.setText(exam.toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this loads the admission user interface to center of the borderPane of the main user interface(mainUi.fxml)
    public void admission(ActionEvent actionEvent)  {
        try {
            loadUiTabs("admission");
            showSideMenuName.setText(("admission").toUpperCase());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this loads the finance user interface to center of the borderPane of the main user interface(mainUi.fxml)
    public void finance(ActionEvent actionEvent) {
        try {
            loadUiTabs("finance");
            showSideMenuName.setText(("finance").toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This methods loads the sideMenu tabs to the centre of the borderPane
    private void loadUiTabs(String ui) throws IOException {

       AnchorPane= FXMLLoader.load(getClass().getResource("/fxml/"+ui+".fxml"));
        borderPane.setCenter(AnchorPane);
        new SlideInRight(AnchorPane).play();
        borderPane.setStyle("-fx-margin:0;");
        borderPane.setLayoutX(0);
        borderPane.setLayoutY(0);

    }

    //this loads the dashboard to center of the borderPane(mainUi.fxml) when the programs initial runs mainUi.fxml
    @Override
    public void initialize(URL location, ResourceBundle resources) {
           loggedIn.setText(userId);
           user.setStyleClass("user-circle");

        showSideMenuName.setText(("dashboard").toUpperCase());
        try {
            loadUiTabs("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //handles logOut
    @FXML
    public void logOut(MouseEvent mouseEvent) {
      initPopup();

    }

    public void initPopup(){
        JFXButton log_out=new JFXButton("Log Out");
        JFXButton profile=new JFXButton("Profile");

             log_out.setOnAction(event -> {

                controller.close(borderPane);
                 controller.changeUi("login");
             });
        VBox vbox=new VBox(profile,log_out);
        vbox.setAlignment(Pos.CENTER);
        JFXPopup popup=new JFXPopup();
        popup.setPopupContent(vbox);
       popup.show(user);

    }
}
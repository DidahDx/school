package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller control= new Controller();

    @Override
    public void start(Stage stage) {
        control.changeUi("mainUi");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
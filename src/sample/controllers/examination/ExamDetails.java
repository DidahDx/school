package sample.controllers.examination;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExamDetails implements Initializable {
    public AnchorPane cat;
    public AnchorPane EndTerm;
    public AnchorPane Average;

    public Tab catTab;
    public Tab endTerm;
    public Tab AverageMarks;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadTab(catTab,"catMarks",cat);
        LoadTab(endTerm,"EndTermExams",EndTerm);
        LoadTab(AverageMarks,"AverageMarks",Average);
    }

    public void loadsCat(MouseEvent mouseEvent) {
        LoadTab(catTab,"catMarks",cat);
    }

    public void loadMain(MouseEvent mouseEvent) {
        LoadTab(endTerm,"EndTermExams",EndTerm);
    }


    public void loadAverage(MouseEvent mouseEvent) {
        LoadTab(AverageMarks,"AverageMarks",Average);
    }


    private void LoadTab(Tab tab,String ui,AnchorPane anchorPane){
        try {
            anchorPane= FXMLLoader.load(getClass().getResource("/fxml/"+ui+".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tab.setContent(anchorPane);
    }
}

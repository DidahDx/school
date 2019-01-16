package sample.controllers.admission;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import org.controlsfx.control.textfield.CustomTextField;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class SchoolPopulation implements Initializable {
    public CustomTextField textFields;

    public javafx.scene.control.Label label1;
    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields.setRight(icon);
        icon.setCursor(Cursor.DEFAULT);
        Search();

    }

    @FXML
    public void Search(){
        icon.setOnMousePressed(e -> {

            label1.setText(textFields.getText());

        });
    }


}



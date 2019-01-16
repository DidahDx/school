package sample.controllers;

import animatefx.animation.SlideInLeft;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class dashboard implements Initializable {
    public CategoryAxis x;
    public NumberAxis y;
    public LineChart<?,?> LineChart;
    public AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SlideInLeft(anchorPane).play();

        XYChart.Series series=new XYChart.Series();
        series.getData().add(new XYChart.Data("Form 1 EAST", 23));
        series.getData().add(new XYChart.Data("Form 1 west", 12));
        series.getData().add(new XYChart.Data("Form 2 EAST", 53));
        series.getData().add(new XYChart.Data("Form 2 west", 13));
        series.getData().add(new XYChart.Data("Form 3 EAST", 73));
        series.getData().add(new XYChart.Data("Form 3 west", 43));
        series.getData().add(new XYChart.Data("Form 4 EAST", 83));
        series.getData().add(new XYChart.Data("Form 4 west", 3));

        LineChart.getData().addAll(series);}
}

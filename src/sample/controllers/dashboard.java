package sample.controllers;

import animatefx.animation.SlideInLeft;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.admission.SchoolStatisticsDao;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class dashboard implements Initializable {
    public CategoryAxis x;
    public NumberAxis y;
    public LineChart<?,?> LineChart;
    public AnchorPane anchorPane;
    private SchoolStatisticsDao schoolStatisticsDao=new SchoolStatisticsDao();
    private int E1,W1,E2,W2,E3,W3,W4,E4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // new SlideInLeft(anchorPane).play();

      setSchoolStatistics();

        XYChart.Series series=new XYChart.Series();
        series.getData().add(new XYChart.Data("Form 1 EAST", E1));
        series.getData().add(new XYChart.Data("Form 1 WEST", W1));
        series.getData().add(new XYChart.Data("Form 2 EAST", E2));
        series.getData().add(new XYChart.Data("Form 2 WEST", W2));
        series.getData().add(new XYChart.Data("Form 3 EAST", E3));
        series.getData().add(new XYChart.Data("Form 3 WEST", W3));
        series.getData().add(new XYChart.Data("Form 4 EAST", E4));
        series.getData().add(new XYChart.Data("Form 4 WEST", W4));

        LineChart.getData().addAll(series);}

        public void setSchoolStatistics(){
           Connection connection= DBConnector.getConnection();
            try {
               ResultSet rs=schoolStatisticsDao.StreamFormStatistics(1,"EAST",connection);
               while(rs.next()) {
                 E1=rs.getInt("COUNT(admission_number)");
               }
                ResultSet rs1=schoolStatisticsDao.StreamFormStatistics(1,"WEST",connection);
                while(rs1.next()) {
                    W1=rs1.getInt("COUNT(admission_number)");
                }
                ResultSet rs2=schoolStatisticsDao.StreamFormStatistics(2,"EAST",connection);
                while(rs2.next()) {
                    E2=rs2.getInt("COUNT(admission_number)");
                }
                ResultSet rs3=schoolStatisticsDao.StreamFormStatistics(2,"WEST",connection);
                while(rs3.next()) {
                    W2=rs3.getInt("COUNT(admission_number)");
                }
                ResultSet rs4=schoolStatisticsDao.StreamFormStatistics(3,"EAST",connection);
                while(rs4.next()) {
                    E3=rs4.getInt("COUNT(admission_number)");
                }
                ResultSet rs5=schoolStatisticsDao.StreamFormStatistics(3,"WEST",connection);
                while(rs5.next()) {
                    W3=rs5.getInt("COUNT(admission_number)");
                }
                ResultSet rs6=schoolStatisticsDao.StreamFormStatistics(4,"EAST",connection);
                while(rs6.next()) {
                    E4=rs6.getInt("COUNT(admission_number)");
                }
                ResultSet rs7=schoolStatisticsDao.StreamFormStatistics(4,"WEST",connection);
                while(rs7.next()) {
                    W4=rs7.getInt("COUNT(admission_number)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
               if(connection!=null){
                  try {
                     connection.close();
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               }
            }
        }

}

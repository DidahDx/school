package sample.controllers.admission;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.admission.SchoolStatisticsDao;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DormStatistics implements Initializable {

    public LineChart<?,?> dormBarChart;

   private SchoolStatisticsDao schoolStatisticsDao =new SchoolStatisticsDao();

   private ResultSet rs=null,rs2=null,rs3=null,rs4=null;
    private int BKenya,BElgon,GKili,GLongont;
    ObservableList setGender= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LoadData();
    }


    public void LoadData() {
         XYChart.Series series=new XYChart.Series();
       Connection connection= DBConnector.getConnection();

        try {


           rs= schoolStatisticsDao.getTotalDorm("male","Kenya",connection);
               while(rs.next()){
                   BKenya=rs.getInt("COUNT(admission_number)");
                  series.getData().add(new XYChart.Data("BOYS DORM Kenya", BKenya));
               }

               rs2= schoolStatisticsDao.getTotalDorm("male","Elgon",connection);
               while(rs2.next()){
                  BElgon=rs2.getInt("COUNT(admission_number)");
                  series.getData().add(new XYChart.Data("BOYS DORM Elgon",BElgon));
               }


           rs3= schoolStatisticsDao.getTotalDorm("female","Kilimanjaro",connection);
           while(rs3.next()){
             GKili=rs3.getInt("COUNT(admission_number)");
              series.getData().add(new XYChart.Data("GIRLS DORM Kilimanjaro",GKili));
           }

           rs4= schoolStatisticsDao.getTotalDorm("female","Longonot",connection);
           while(rs4.next()){
              GLongont=rs4.getInt("COUNT(admission_number)");
              series.getData().add(new XYChart.Data("GIRLS DORM Longonot",GLongont));

        }

        } catch (SQLException e) {
           e.printStackTrace();
        }finally {
           if (connection!=null){
              try {
                 connection.close();
              } catch (SQLException e) {
                 e.printStackTrace();
              }
           }
        }

       dormBarChart.getData().addAll(series);
       dormBarChart.setTitle("Dorm Population For Boys and Girls");
    }

}

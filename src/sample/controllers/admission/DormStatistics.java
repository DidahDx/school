package sample.controllers.admission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import sample.dataAccessObject.admission.SchoolStatisticsDao;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DormStatistics implements Initializable {
    public PieChart pieChart;

    SchoolStatisticsDao schoolStatisticsDao =new SchoolStatisticsDao();
    ObservableList<PieChart.Data> pieData= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadPieChart();
        pieChart.setData(pieData);
    }

    public void loadPieChart(){

        ResultSet rs,rs1;
        try {

            rs= schoolStatisticsDao.GenderSchoolStatistics("male");
            rs1= schoolStatisticsDao.GenderSchoolStatistics("female");
            while(rs.next() && rs1.next()){
                pieData.addAll(new PieChart.Data("male",rs.getInt("COUNT(admission_number)")),
                        new PieChart.Data("female",rs1.getInt("COUNT(admission_number)")),
                        new PieChart.Data("test",40),new PieChart.Data("best",20),
                        new PieChart.Data("some",15));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

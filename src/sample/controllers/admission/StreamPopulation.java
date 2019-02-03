package sample.controllers.admission;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import sample.dataAccessObject.admission.SchoolStatisticsDao;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * THIS CLASS IS USED TO CONTROL THE PIE CHART FOR THE SCHOOL POPULATION STATISTICS
 * */
public class StreamPopulation implements Initializable {
    public PieChart pieChart;
    public PieChart pieChart1;
    public JFXComboBox form;
    public JFXComboBox Stream;
    public Label fTotalPopulation;
    public Label fBoysPopulation;
    public Label fGirlsPopulation;
    public Label sTotalPopulation;
    public Label sBoysPopulation;
    public Label sGirlsPopulation;

    private SchoolStatisticsDao schoolStatisticsDao =new SchoolStatisticsDao();
    private ObservableList<PieChart.Data> pieData= FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pieData1= FXCollections.observableArrayList();
    private ObservableList listForm=FXCollections.observableArrayList();
    private ObservableList stream=FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadFormComboBox(form);
       loadStreamComboBox(Stream);
        loadPieChart();
        pieChart.setData(pieData);
        pieChart1.setData(pieData1);
        form.setValue("All");
        Stream.setValue("EAST");
    }

    //this method is used to add items to the form comboBox
    public void loadFormComboBox(ComboBox form){
        listForm.removeAll(listForm);
        String[] a={"1","2","3","4","All"};
        Collections.addAll(listForm,a);
        form.getItems().addAll(listForm);
    }

    //This method is used to Add items to the stream ComboBox
    public void loadStreamComboBox(ComboBox Stream){
        stream.removeAll( stream );

        String[] a={"WEST","EAST"};
        Collections.addAll(stream,a);
        Stream.getItems().addAll(stream);

    }

    public void loadPieChart(){

        ResultSet rs,rs1;
        try {

           rs= schoolStatisticsDao.GenderSchoolStatistics("male");
           rs1= schoolStatisticsDao.GenderSchoolStatistics("female");
           while(rs.next() && rs1.next()){
              pieData.addAll(new PieChart.Data("male",rs.getInt("COUNT(admission_number)")),
                      new PieChart.Data("female",rs1.getInt("COUNT(admission_number)")),
                      new PieChart.Data("test",30),new PieChart.Data("best",20),
                      new PieChart.Data("some",15));

              pieData1.addAll(new PieChart.Data("male",rs.getInt("COUNT(admission_number)")),
                       new PieChart.Data("female",rs1.getInt("COUNT(admission_number)")),
                       new PieChart.Data("test",30),new PieChart.Data("best",20));
           }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //this method is used to load the pie Charts
    public void loadChart(ActionEvent actionEvent) {

        /**this if else conditions are used load the pieChart with data for the
         * whole school of boys and girls population for the first pieChart
         */
        if (form.getValue().toString().matches("All")){

            try {
                ResultSet rs= schoolStatisticsDao.GenderSchoolStatistics("male");
                ResultSet rs1= schoolStatisticsDao.GenderSchoolStatistics("female");
                while (rs.next() && rs1.next()){
                    pieData.removeAll(pieData);
                    pieData.addAll(new PieChart.Data("female",rs1.getInt("COUNT(admission_number)")),
                            new PieChart.Data("male",rs.getInt("COUNT(admission_number)")));

                    fBoysPopulation.setText(String.valueOf(rs.getInt("COUNT(admission_number)")));
                    fGirlsPopulation.setText(String.valueOf(rs1.getInt("COUNT(admission_number)")));
                    fTotalPopulation.setText(String.valueOf(rs.getInt("COUNT(admission_number)") + rs1.getInt("COUNT(admission_number)")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pieChart.setData(pieData);
        } else if (!form.getValue().toString().matches("All")){
            LoadingChart();
        }

        //this if else conditions are used to load the pieChart with the data for the each particular stream
        if (form.getValue().toString().matches("All") && Stream.getValue().toString().matches("EAST")){
            loadStreamChart();
        }
        else if (form.getValue().toString().matches("All") && Stream.getValue().toString().matches("WEST")){
            loadStreamChart();
        }else if (!form.getValue().toString().matches("All")){
            loadFormPerStream();
        }
    }

        //this method loads the chart data for the stream of each form used in the second chart
        private void loadFormPerStream() {
        try {
            ResultSet rs2= schoolStatisticsDao.StreamGenderFormStatistics(Integer.parseInt(form.getValue().toString()),"female",Stream.getValue().toString());
            ResultSet rs= schoolStatisticsDao.StreamGenderFormStatistics(Integer.parseInt(form.getValue().toString()),"male",Stream.getValue().toString());
            pieData1.removeAll(pieData1);
            while(rs.next() && rs2.next()){
                pieData1.addAll(new PieChart.Data("male",rs.getInt("COUNT(admission_number)"))
                        ,new PieChart.Data("female",rs2.getInt("COUNT(admission_number)")));

                sTotalPopulation.setText(String.valueOf(rs.getInt("COUNT(admission_number)")+ rs2.getInt("COUNT(admission_number)")));
                sBoysPopulation.setText(String.valueOf(rs.getInt("COUNT(admission_number)")));
                sGirlsPopulation.setText(String.valueOf(rs2.getInt("COUNT(admission_number)")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pieChart1.setData(pieData1);
    }

    //this method loads the chart for the forms data of boys and girls in the first chart
    private void LoadingChart() {
        try {
            ResultSet rs= schoolStatisticsDao.GenderFormStatistics(Integer.parseInt(form.getValue().toString()),"male");
            ResultSet rs1= schoolStatisticsDao.GenderFormStatistics(Integer.parseInt(form.getValue().toString()),"female");
            loopResults(rs, rs1, pieData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        pieChart.setData(pieData);
    }

    //this method loads the second chart with the streams data for both boys and girls
    private void loadStreamChart() {
        try {
            ResultSet rs= schoolStatisticsDao.StreamGenderSchoolStatistics(Stream.getValue().toString(),"male");
            ResultSet rs1= schoolStatisticsDao.StreamGenderSchoolStatistics(Stream.getValue().toString(),"female");
            pieData1.removeAll(pieData1);

            while (rs.next() && rs1.next()) {
                sBoysPopulation.setText(String.valueOf(rs.getInt("COUNT(admission_number)")));
                sTotalPopulation.setText(String.valueOf(rs.getInt("COUNT(admission_number)")
                        +  rs1.getInt("COUNT(admission_number)")));
                sGirlsPopulation.setText(String.valueOf(rs1.getInt("COUNT(admission_number)")));

                pieData1.addAll(new PieChart.Data("female", rs1.getInt("COUNT(admission_number)")),
                        new PieChart.Data("male", rs.getInt("COUNT(admission_number)")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pieChart1.setData(pieData1);
    }

    //this method loops through the resultSet while adding data to the ObservableList
    private void loopResults(ResultSet rs, ResultSet rs1, ObservableList<PieChart.Data> pieData) throws SQLException {
        pieData.removeAll(pieData);
        while (rs.next() && rs1.next()){
            pieData.addAll( new PieChart.Data("female",rs1.getInt("COUNT(admission_number)")),
                    new PieChart.Data("male",rs.getInt("COUNT(admission_number)")));

            fGirlsPopulation.setText(String.valueOf(rs1.getInt("COUNT(admission_number)")));
            fTotalPopulation.setText(String.valueOf(rs.getInt("COUNT(admission_number)") + rs1.getInt("COUNT(admission_number)")));
            fBoysPopulation.setText(String.valueOf(rs.getInt("COUNT(admission_number)")));
        }
    }
}
package sample.controllers.admission;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import sample.dataAccessObject.DBConnector;
import sample.dataAccessObject.admission.SchoolStatisticsDao;

import java.net.URL;
import java.sql.Connection;
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
   public Label PopulationLabelLeft;
   public Label PopulationLabelRight;

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

      pieChart.setData(pieData);
      pieChart1.setData(pieData1);
      form.setValue("All");
      Stream.setValue("EAST");
      loadPieChart();

   }

   //this method is used to add items to the form comboBox
   private void loadFormComboBox(ComboBox form){
      listForm.removeAll(listForm);
      String[] a={"1","2","3","4","All"};
      Collections.addAll(listForm,a);
      form.getItems().addAll(listForm);
   }

   //This method is used to Add items to the stream ComboBox
   private void loadStreamComboBox(ComboBox Stream){
      stream.removeAll( stream );

      String[] a={"WEST","EAST"};
      Collections.addAll(stream,a);
      Stream.getItems().addAll(stream);

   }

   private void loadPieChart(){
      if (form.getValue().toString().matches("All") && Stream.getValue().toString().matches("EAST"))
      {
         loadChart();
      }

   }


   //this method is used to load the pie Charts
   public void loadChart() {

      /**this if else conditions are used load the pieChart with data for the
       * whole school of boys and girls population for the first pieChart
       */
      Connection connection= DBConnector.getConnection();
      try {
         if (form.getValue().toString().matches("All")){

            ResultSet rs= schoolStatisticsDao.GenderSchoolStatistics("male",connection);
            ResultSet rs1= schoolStatisticsDao.GenderSchoolStatistics("female",connection);
            while (rs.next() && rs1.next()){
               pieData.removeAll(pieData);
               pieData.addAll(
                       new PieChart.Data("male",rs.getInt("COUNT(admission_number)")),
                       new PieChart.Data("female",rs1.getInt("COUNT(admission_number)"))
               );

               PopulationLabelLeft.setText("TOTAL SCHOOL POPULATION"); //for setting the left pieChart Title

               fBoysPopulation.setText("Boys Population : "+String.valueOf(rs.getInt("COUNT(admission_number)")));
               fTotalPopulation.setText("Total population : "+String.valueOf(rs.getInt("COUNT(admission_number)") + rs1.getInt("COUNT(admission_number)")));
               fGirlsPopulation.setText("Girls Population : " +String.valueOf(rs1.getInt("COUNT(admission_number)")));
            }

            pieChart.setData(pieData);
         } else if (!form.getValue().toString().matches("All")){
            loadFormChart(connection);
         }

         //this if else conditions are used to load the pieChart with the data for the each particular stream
         if (form.getValue().toString().matches("All") && Stream.getValue().toString().matches("EAST"))
         {
            loadStreamChart(connection);
         }
         else if (form.getValue().toString().matches("All") && Stream.getValue().toString().matches("WEST"))
         {
            loadStreamChart(connection);
         }
         else if (!form.getValue().toString().matches("All"))
         {
            loadFormPerStream(connection);
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
   }

   //this method loads the chart data for the stream of each form used in the second chart
   private void loadFormPerStream(Connection connection) {
      try {
         ResultSet rs2= schoolStatisticsDao.StreamGenderFormStatistics(Integer.parseInt(form.getValue().toString()),"female",Stream.getValue().toString(),connection);
         ResultSet rs= schoolStatisticsDao.StreamGenderFormStatistics(Integer.parseInt(form.getValue().toString()),"male",Stream.getValue().toString(),connection);
         pieData1.removeAll(pieData1);
         while(rs.next() && rs2.next()){
            pieData1.addAll(new PieChart.Data("male",rs.getInt("COUNT(admission_number)"))
                    ,new PieChart.Data("female",rs2.getInt("COUNT(admission_number)")));

            sGirlsPopulation.setText("Girls Population : " +String.valueOf(rs2.getInt("COUNT(admission_number)")));
            sTotalPopulation.setText("Total population : "+String.valueOf(rs.getInt("COUNT(admission_number)")+ rs2.getInt("COUNT(admission_number)")));
            sBoysPopulation.setText("Boys Population : "+String.valueOf(rs.getInt("COUNT(admission_number)")));

            PopulationLabelRight.setText("Population For Form "+form.getValue()+" "+Stream.getValue()); //used to set the title for the right pieChart
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      pieChart1.setData(pieData1);
   }

   //this method loads the chart for the forms data of boys and girls in the first chart
   private void loadFormChart(Connection connection) {
      try {
         ResultSet rs= schoolStatisticsDao.GenderFormStatistics(Integer.parseInt(form.getValue().toString()),"male",connection);
         ResultSet rs1= schoolStatisticsDao.GenderFormStatistics(Integer.parseInt(form.getValue().toString()),"female",connection);
         loopResults(rs, rs1, pieData);

      } catch (SQLException e) {
         e.printStackTrace();
      }
      PopulationLabelLeft.setText("Population for Form "+form.getValue());

      pieChart.setData(pieData);
   }

   //this method loads the second chart with the streams data for both boys and girls
   private void loadStreamChart(Connection connection) {
      try {
         ResultSet rs= schoolStatisticsDao.StreamGenderSchoolStatistics(Stream.getValue().toString(),"male",connection);
         ResultSet rs1= schoolStatisticsDao.StreamGenderSchoolStatistics(Stream.getValue().toString(),"female",connection);
         pieData1.removeAll(pieData1);

         while (rs.next() && rs1.next()) {
            sBoysPopulation.setText("Boys Population : "+ String.valueOf(rs.getInt("COUNT(admission_number)")));
            sGirlsPopulation.setText("Girls Population : " +String.valueOf(rs1.getInt("COUNT(admission_number)")));
            sTotalPopulation.setText("Total population : "+String.valueOf(rs.getInt("COUNT(admission_number)")
                    +  rs1.getInt("COUNT(admission_number)")));

            pieData1.addAll(new PieChart.Data("female", rs1.getInt("COUNT(admission_number)")),
                    new PieChart.Data("male", rs.getInt("COUNT(admission_number)")));
            PopulationLabelRight.setText("Total School Population For Stream "+ Stream.getValue());
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
         pieData.addAll( new PieChart.Data("male",rs.getInt("COUNT(admission_number)")),
                 new PieChart.Data("female",rs1.getInt("COUNT(admission_number)"))
         );

         fTotalPopulation.setText("Total population : "+String.valueOf(rs.getInt("COUNT(admission_number)") + rs1.getInt("COUNT(admission_number)")));
         fBoysPopulation.setText("Boys Population : "+ String.valueOf(rs.getInt("COUNT(admission_number)")));
         fGirlsPopulation.setText("Girls Population : " +String.valueOf(rs1.getInt("COUNT(admission_number)")));
      }
   }
}
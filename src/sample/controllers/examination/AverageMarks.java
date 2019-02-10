package sample.controllers.examination;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dataAccessObject.examination.AverageMarksDao;
import sample.model.examination.AverageModelTable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AverageMarks implements Initializable {

    public TableColumn<AverageModelTable,Integer> admissionNumber;
    public TableColumn<AverageModelTable,Integer> tMath;
    public TableColumn<AverageModelTable,Integer> tEnglish;
    public TableColumn<AverageModelTable,Integer> tKiswahili;
    public TableColumn<AverageModelTable,Integer> tChemistry;
    public TableColumn<AverageModelTable,Integer> tPhysics;
    public TableColumn<AverageModelTable,Integer> tBiology;
    public TableColumn<AverageModelTable,Integer> tCre;
    public TableColumn<AverageModelTable,Integer> tHistory;
    public TableColumn<AverageModelTable,Integer> tGeography;
    public TableColumn<AverageModelTable,Integer> tAgriculture;
    public TableColumn<AverageModelTable,Integer> tBusinessStudies;
    public TableColumn<AverageModelTable,Integer> tComputerScience;
    public TableColumn<AverageModelTable,Integer> tForm;
    public TableColumn<AverageModelTable,Integer> tTerm;
    public TableColumn<AverageModelTable,Integer> tDate;
    public TableColumn<AverageModelTable,Integer> tTime;
    public TableColumn<AverageModelTable,Integer> tTotal;
    public TableColumn<AverageModelTable,Integer> tStreamPosition;
    public TableColumn<AverageModelTable,Integer> tOverallPosition;
    public TableColumn<AverageModelTable,String> tStream;
    public TableView<AverageModelTable> table;


    public TextField searchTextField;
    public ChoiceBox SearchForms;
    private AverageMarksDao averageMarksDao=new AverageMarksDao();
    private ObservableList listTable= FXCollections.observableArrayList();
    CatMarks catMarks=new CatMarks();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        catMarks.fillChoiceBox(SearchForms);
        SearchForms.setValue("All");
        loadTable();
    }

    //this method is used to search using admission Number
    public void search(ActionEvent actionEvent) {
        try {
            ResultSet rs;
            if(searchTextField.getText().isEmpty()){
                rs= averageMarksDao.ReadAverageMarks();
            }else{
            rs=averageMarksDao.Search(Integer.parseInt(searchTextField.getText()));
            }
            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to load the table view with all the avearge marks from database
    public void loadTable(){
        try {
            ResultSet rs;
            rs= averageMarksDao.ReadAverageMarks();
            fillTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to search the average table using form
    public void loadFormTable(ActionEvent actionEvent) {
        try {
            if (SearchForms.getValue().toString().matches("All")){
                fillTable(averageMarksDao.ReadAverageMarks());
            }else{
                fillTable( averageMarksDao.SearchForm(Integer.parseInt(SearchForms.getValue().toString())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this method is used to fill the table when called
    private void fillTable(ResultSet rs) throws SQLException {
        listTable.removeAll(listTable);

        while(rs.next()){
            listTable.add(new AverageModelTable(rs.getInt("admission_number"),rs.getInt("maths"),
                    rs.getInt("english"),rs.getInt("kiswahili"),rs.getInt("biology"),rs.getInt("physics"),
                    rs.getInt("chemistry"),rs.getInt("history"),rs.getInt("geography"),rs.getInt("cre"),
                    rs.getInt("business_studies"),rs.getInt("computer_studies"),rs.getInt("agriculture"),rs.getInt("average_marks_id"),
                    rs.getInt("term"),rs.getInt("form"),rs.getDate("date"),rs.getTime("time")
                    ,rs.getInt("total"),rs.getInt("stream_position"),rs.getInt("overall_position"),
                    rs.getString("stream")));
        }

        tBiology.setCellValueFactory(new PropertyValueFactory<>("Biology"));
        tEnglish.setCellValueFactory(new PropertyValueFactory<>("English"));
        admissionNumber.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
        tMath.setCellValueFactory(new PropertyValueFactory<>("Maths"));
        tKiswahili.setCellValueFactory(new PropertyValueFactory<>("Kiswahili"));
        tPhysics.setCellValueFactory(new PropertyValueFactory<>("Physics"));
        tCre.setCellValueFactory(new PropertyValueFactory<>("Cre"));
        tChemistry.setCellValueFactory(new PropertyValueFactory<>("Chemistry"));
        tHistory.setCellValueFactory(new PropertyValueFactory<>("History"));
        tAgriculture.setCellValueFactory(new PropertyValueFactory<>("agriculture"));
        tTerm.setCellValueFactory(new PropertyValueFactory<>("term"));
        tBusinessStudies.setCellValueFactory(new PropertyValueFactory<>("BusinessStudies"));
        tComputerScience.setCellValueFactory(new PropertyValueFactory<>("ComputerStudies"));
        tGeography.setCellValueFactory(new PropertyValueFactory<>("Geography"));
        tForm.setCellValueFactory(new PropertyValueFactory<>("form"));
        tTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tStreamPosition.setCellValueFactory(new PropertyValueFactory<>("streamPosition"));
        tOverallPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
        tStream.setCellValueFactory(new PropertyValueFactory<>("stream"));

        table.setItems(listTable);
    }
}

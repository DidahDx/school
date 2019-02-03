package sample.model.examination;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class ExamModelTable {

    int admissionNumber,Maths,English,Kiswahili,Biology,Physics,
            Chemistry,History,Geography,Cre,BusinessStudies,ComputerStudies,agriculture,endTermId,averageMarksId,CatId,term,form;
    Date date; Time time;


    public ExamModelTable(int admissionNumber, int Maths, int English, int Kiswahili, int Biology, int Physics,
                          int Chemistry, int History, int Geography , int Cre, int BusinessStudies,
                          int ComputerStudies, int agriculture, int endTermId, int term, int form, Date date, Time time){

        this.admissionNumber=admissionNumber;
        this.Maths=Maths;
        this.English=English;
        this.Kiswahili=Kiswahili;
        this.Biology=Biology;
        this.Physics=Physics;
        this.Chemistry=Chemistry;
        this.History=History;
        this.Geography=Geography;
        this.Cre=Cre;
        this.BusinessStudies=BusinessStudies;
        this.ComputerStudies=ComputerStudies;
        this.agriculture=agriculture;
        this.endTermId=endTermId;
        this.CatId=endTermId;
        this.averageMarksId=endTermId;
       this.term=term;
       this.form=form;
       this.date=date;
       this.time=time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getEndTermId() {
        return endTermId;
    }

    public void setEndTermId(int endTermId) {
        this.endTermId = endTermId;
    }

    public int getAverageMarksId() {
        return averageMarksId;
    }

    public void setAverageMarksId(int averageMarksId) {
        this.averageMarksId = averageMarksId;
    }

    public int getCatId() {
        return CatId;
    }

    public void setCatId(int catId) {
        CatId = catId;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public int getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(int admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public int getMaths() {
        return Maths;
    }

    public void setMaths(int maths) {
        Maths = maths;
    }

    public int getEnglish() {
        return English;
    }

    public void setEnglish(int english) {
        English = english;
    }

    public int getKiswahili() {
        return Kiswahili;
    }

    public void setKiswahili(int kiswahili) {
        Kiswahili = kiswahili;
    }

    public int getBiology() {
        return Biology;
    }

    public void setBiology(int biology) {
        Biology = biology;
    }

    public int getPhysics() {
        return Physics;
    }

    public void setPhysics(int physics) {
        Physics = physics;
    }

    public int getChemistry() {
        return Chemistry;
    }

    public void setChemistry(int chemistry) {
        Chemistry = chemistry;
    }

    public int getHistory() {
        return History;
    }

    public void setHistory(int history) {
        History = history;
    }

    public int getGeography() {
        return Geography;
    }

    public void setGeography(int geography) {
        Geography = geography;
    }

    public int getCre() {
        return Cre;
    }

    public void setCre(int cre) {
        Cre = cre;
    }

    public int getBusinessStudies() {
        return BusinessStudies;
    }

    public void setBusinessStudies(int businessStudies) {
        BusinessStudies = businessStudies;
    }

    public int getComputerStudies() {
        return ComputerStudies;
    }

    public void setComputerStudies(int computerStudies) {
        ComputerStudies = computerStudies;
    }

    public int getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(int agriculture) {
        this.agriculture = agriculture;
    }

}
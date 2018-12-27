package sample.database;

public class modelTable {

    String firstName,secondName,stream,gender;
    int form,admissionNumber;

    public modelTable(String firstName, String secondName, String stream, int form,String gender, int admissionNumber){
        this.firstName= firstName;
        this.secondName= secondName;
        this.stream= stream;
        this.gender= gender;
        this.form= form;
        this.admissionNumber=admissionNumber;
    }

    public int getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(int admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

}
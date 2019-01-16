package sample.model.admission;

public class GuardianModelTable {

    String guardianFirstName,guardianLastName,email,phoneNumber;
    int admissionNumber;
    public GuardianModelTable(String guardianFirstName,String guardianLastName,String email,String phoneNumber,int admissionNumber){
        this.guardianFirstName=guardianFirstName;
        this.guardianLastName=guardianLastName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.admissionNumber=admissionNumber;
    }

    public void setGuardianFirstName(String guardianFirstName){
        this.guardianFirstName=guardianFirstName;
    }
    public String getGuardianFirstName(){
        return guardianFirstName;
    }

    public void setGuardianLastName(String guardianLastName){
        this.guardianLastName=guardianLastName;
    }
    public String getGuardianLastName(){
        return  guardianLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(int admissionNumber) {
        this.admissionNumber = admissionNumber;
    }
}

package sample.model.finance;

import java.sql.Date;
import java.sql.Time;

public class FinanceModelTable {
    int admissionNumber, term;
    String bankTransactionId,bankName;
    double feesPaid, feeBalance, feesExpected;
    Time timeOfPayment;
    Date dateOfPayment;
    int financeId,form;


    public FinanceModelTable(int  admissionNumber, String bankTransactionId, double feesExpected, double feesPaid,
                             double feeBalance, int term, Time timeOfPayment, Date dateOfPayment, int financeId, int form, String bankName) {
        this.admissionNumber = admissionNumber;
        this.feeBalance = feeBalance;
        this.feesExpected = feesExpected;
        this.feesPaid = feesPaid;
        this.bankTransactionId = bankTransactionId;
        this.term = term;
        this.dateOfPayment=dateOfPayment;
        this.timeOfPayment=timeOfPayment;
        this.financeId=financeId;
        this.form=form;
        this.bankName=bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getFinanceId() {
        return financeId;
    }

    public void setFinanceId(int financeId) {
        this.financeId = financeId;
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

    public Time getTimeOfPayment() {
        return timeOfPayment;
    }

    public void setTimeOfPayment(Time timeOfPayment) {
        this.timeOfPayment = timeOfPayment;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public void setAdmissionNumber(int admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(String bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public double getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(double feesPaid) {
        this.feesPaid = feesPaid;
    }

    public double getFeeBalance() {
        return feeBalance;
    }

    public void setFeeBalance(double feeBalance) {
        this.feeBalance = feeBalance;
    }

    public double getFeesExpected() {
        return feesExpected;
    }

    public void setFeesExpected(double feesExpected) {
        this.feesExpected = feesExpected;
    }
}

package com.villagebanking.BOObjects;

import java.util.ArrayList;

public class BOLoanHeader extends BOBase {
    private BOKeyValue person;
    private BOKeyValue group;
    private BOKeyValue loanType;
    private double loanAmount;
    private double otherAmount;
    private double charges;
    private String transDate;
    private String remarks;
    private BOKeyValue reference1;
    private double repayAmount;

    public BOKeyValue getPerson() {
        if (person == null) person = new BOKeyValue();
        return person;
    }

    public void setPerson(BOKeyValue person) {
        this.person = person;
    }

    public BOKeyValue getGroup() {
        if (group == null) group = new BOKeyValue();
        return group;
    }

    public void setGroup(BOKeyValue group) {
        this.group = group;
    }

    public BOKeyValue getLoanType() {
        if (loanType == null) loanType = new BOKeyValue();
        return loanType;
    }

    public void setLoanType(BOKeyValue loanType) {
        this.loanType = loanType;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(double otherAmount) {
        this.otherAmount = otherAmount;
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public BOKeyValue getReference1() {
        if (reference1 == null)
            reference1 = new BOKeyValue();
        return reference1;
    }

    public void setReference1(BOKeyValue reference1) {
        this.reference1 = reference1;
    }

    public double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(double repayAmount) {
        this.repayAmount = repayAmount;
    }


    private ArrayList<BOLoanDetail> paySummary;

    public ArrayList<BOLoanDetail> getPaySummary() {
        if (paySummary == null) paySummary = new ArrayList<>();
        return paySummary;
    }

    public void setPaySummary(ArrayList<BOLoanDetail> paySummary) {
        this.paySummary = paySummary;
    }

}

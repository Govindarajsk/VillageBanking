package com.villagebanking.BOObjects;

import java.util.ArrayList;

public class BOLoanHeader extends BOBase {
    private long personKey;
    private long groupKey;
    private long loanType;
    private double loanAmount;
    private double otherAmount;
    private double charges;
    private String transDate;
    private String remarks;

    public long getPersonKey() {
        return personKey;
    }

    public void setPersonKey(long personKey) {
        this.personKey = personKey;
    }

    public long getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(long groupKey) {
        this.groupKey = groupKey;
    }

    public long getLoanType() {
        return loanType;
    }

    public void setLoanType(long loanType) {
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


    private ArrayList<BOLoanDetail> paySummary;

    public ArrayList<BOLoanDetail> getPaySummary() {
        if (paySummary == null) paySummary = new ArrayList<>();
        return paySummary;
    }

    public void setPaySummary(ArrayList<BOLoanDetail> paySummary) {
        this.paySummary = paySummary;
    }

}

package com.villagebanking.BOObjects;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

public class BOGroup extends BOBase {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfPerson() {
        return noOfPerson;
    }

    public void setNoOfPerson(int noOfPerson) {
        this.noOfPerson = noOfPerson;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.DOWN);
        this.amount =  amount;
    }

    public long getStartPeriodKey() {
        return startPeriodKey;
    }

    public void setStartPeriodKey(long startPeriodKey) {
        this.startPeriodKey = startPeriodKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBondCharge() {
        return bondCharge;
    }

    public void setBondCharge(double bondCharge) {
        this.bondCharge = bondCharge;
    }

    public long getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(long periodKey) {
        this.periodKey = periodKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String name;
    private int noOfPerson;
    private double amount;
    private long startPeriodKey;
    private String type;
    private double bondCharge;
    private long periodKey;
    private String status;
}

package com.villagebanking.BOObjects;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

public class BOGroup {

    public int getKeyID() {
        return keyID;
    }

    public void setKeyID(int keyID) {
        this.keyID = keyID;
    }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public String getOccurnece() {
        return occurnece;
    }

    public void setOccurnece(String occurnece) {
        this.occurnece = occurnece;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private int keyID;
    private String name;
    private int noOfPerson;
    private double amount;
    private Date startDate;
    private String type;
    private double bondCharge;
    private String occurnece;
    private String status;
}

package com.villagebanking.BOObjects;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

public class BOGroup extends BOBase {

    private String name;
    private int noOfPerson;
    private double amount;
    private String type;
    private double bondCharge;
    private String status;

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
        this.amount =  amount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    private BOKeyValue periodDetail;
    public BOKeyValue getPeriodDetail() {
        if(periodDetail==null)
            periodDetail=new BOKeyValue();
        return periodDetail;
    }

    public void setPeriodDetail(BOKeyValue periodDetail) {
        this.periodDetail = periodDetail;
    }
}

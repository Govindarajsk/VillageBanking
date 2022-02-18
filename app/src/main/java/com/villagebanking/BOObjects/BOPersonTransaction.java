package com.villagebanking.BOObjects;

import android.text.format.DateUtils;

import java.util.Date;

public class BOPersonTransaction {
    private int keyID;
    private String transDate= new Date().toString();
    private int gp_key;
    private Double amount=0.00;
    private Double calcAmount=0.00;
    private String status;
    private char linktype;
    private int linkId;
    private String linkName;

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public int getKeyID() {
        return keyID;
    }

    public void setKeyID(int keyID) {
        this.keyID = keyID;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public int getGp_key() {
        return gp_key;
    }

    public void setGp_key(int gp_key) {
        this.gp_key = gp_key;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCalcAmount() {
        return calcAmount;
    }

    public void setCalcAmount(Double calcAmount) {
        this.calcAmount = calcAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public char getLinktype() {
        return linktype;
    }

    public void setLinktype(char linktype) {
        this.linktype = linktype;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }
}

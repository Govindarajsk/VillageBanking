package com.villagebanking.BOObjects;

import java.util.ArrayList;

public class BOTransHeader extends BOBase {
    private long periodKey;
    private String tableName;
    private long tableLinkKey;
    private String remarks;
    private String transDate;
    private double totalAmount;
    private double paidAmount;
    private double balanceAmount;

    private BOKeyValue linkDetail1;
    private BOKeyValue linkDetail2;

    public boolean IsNew;
    private ArrayList<BOTransDetail> transDetails;


    public long getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(long periodKey) {
        this.periodKey = periodKey;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getTableLinkKey() {
        return tableLinkKey;
    }

    public void setTableLinkKey(long tableLinkKey) {
        this.tableLinkKey = tableLinkKey;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BOKeyValue getLinkDetail1() {
        if (linkDetail1 == null) linkDetail1 = new BOKeyValue();
        return linkDetail1;
    }

    public void setLinkDetail1(BOKeyValue linkDetail1) {
        this.linkDetail1 = linkDetail1;
    }

    public BOKeyValue getLinkDetail2() {
        if (linkDetail2 == null) linkDetail2 = new BOKeyValue();
        return linkDetail2;
    }

    public void setLinkDetail2(BOKeyValue linkDetail2) {
        this.linkDetail2 = linkDetail2;
    }

    public ArrayList<BOTransDetail> getTransDetails() {
        if (transDetails == null) transDetails = new ArrayList<>();
        return transDetails;
    }

    public void setTransDetails(ArrayList<BOTransDetail> transDetails) {
        this.transDetails = transDetails;
    }
}

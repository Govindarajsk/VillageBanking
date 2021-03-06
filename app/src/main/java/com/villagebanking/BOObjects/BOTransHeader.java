package com.villagebanking.BOObjects;

import java.util.ArrayList;

/*
TRANSACTION_HEADER
    "ID INTEGER PRIMARY KEY," +
    "PERIOD_KEY INTEGER, " +
    "TABLE_NAME TEXT," +
    "TABLE_LINK_KEY INTEGER," +
    "REMARKS TEXT," +
    "DATE TEXT," +
    "TOTAL_AMOUNT DECIMAL," +
    "PAID_AMOUNT DECIMAL," +
    "BALANCE_AMOUNT DECIMAL"
 */
public class BOTransHeader extends BOBase {
    private long periodKey;
    private String tableName;
    private long tableLinkKey;
    private String remarks;
    private String transDate;
    private double totalAmount;
    private double paidAmount;
    private double balanceAmount;
    private ArrayList<BOTransDetail> transDetails;

    public boolean IsNew;
    public long link1Key;
    public String link1Detail;
    public long link2Key;
    public String link2Detail;

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

    public ArrayList<BOTransDetail> getTransDetails() {
        if(transDetails==null)transDetails=new ArrayList<>();
        return transDetails;
    }

    public void setTransDetails(ArrayList<BOTransDetail> transDetails) {
        this.transDetails = transDetails;
    }
}

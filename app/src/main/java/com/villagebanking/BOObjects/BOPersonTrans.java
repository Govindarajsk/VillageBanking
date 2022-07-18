package com.villagebanking.BOObjects;

import java.util.ArrayList;

public class BOPersonTrans extends BOBase {
    private BOPeriod period_detail;
    private String tableName;
    private String remarks;

    public BOPeriod getPeriod_detail() {
        if (period_detail == null) period_detail = new BOPeriod();
        return period_detail;
    }

    public void setPeriod_detail(BOPeriod period_detail) {
        this.period_detail = period_detail;
    }

    private long forien_key;
    private long table_link_key;
    private String transDate;
    private Double actualAmount;

    public long getTable_link_key() {
        return table_link_key;
    }

    public void setTable_link_key(long table_link_key) {
        this.table_link_key = table_link_key;
    }

    public Double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(Double newAmount) {
        this.newAmount = newAmount;
    }

    private Double newAmount;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getForien_key() {
        return forien_key;
    }

    public void setForien_key(long forien_key) {
        this.forien_key = forien_key;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public Double getActualAmount() {
        //if(newAmount>0) actualAmount=actualAmount-(newAmount);
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private ArrayList<BOPersonTrans> subTransList;

    public ArrayList<BOPersonTrans> getSubTransList() {
        if (subTransList == null) subTransList = new ArrayList<BOPersonTrans>();
        return subTransList;
    }

    public void setSubTransList(ArrayList<BOPersonTrans> subTransList) {
        this.subTransList = subTransList;
    }

    private long parentKey;

    public long getParentKey() {
        return parentKey;
    }

    public void setParentKey(long parentKey) {
        this.parentKey = parentKey;
    }

    private BOKeyValue detail1;

    public BOKeyValue getDetail1() {
        if (detail1 == null) detail1 = new BOKeyValue(0, "");
        return detail1;
    }

    public void setDetail1(BOKeyValue detail1) {
        this.detail1 = detail1;
    }

    public BOKeyValue getDetail2() {
        if (detail2 == null) detail2 = new BOKeyValue(0, "");
        return detail2;
    }

    public void setDetail2(BOKeyValue detail2) {
        this.detail2 = detail2;
    }

    private BOKeyValue detail2;
}

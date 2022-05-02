package com.villagebanking.BOObjects;

import android.text.format.DateUtils;

import java.util.Date;

public class BOPersonTransaction extends BOBase {
    private BOPeriod period_detail;
    private BOPerson person_detail;
    private String tableName;
    private String remarks;

    public BOPerson getPerson_detail() {
        if (person_detail == null) person_detail = new BOPerson();
        return person_detail;
    }

    public BOPeriod getPeriod_detail() {
        if (period_detail == null) period_detail = new BOPeriod();
        return period_detail;
    }

    public void setPeriod_detail(BOPeriod period_detail) {
        this.period_detail = period_detail;
    }

    public void setPerson_detail(BOPerson person_detail) {
        this.person_detail = person_detail;
    }

    private long forien_key;
    private String transDate;
    private Double actualAmount;

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
}

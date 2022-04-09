package com.villagebanking.BOObjects;

import java.util.Date;

public class BOPeriod extends BOBase {
    private int periodType;
    private String periodName;
    private Date actualDate;
    private String periodRemarks;

    public int getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public String getPeriodRemarks() {
        return periodRemarks;
    }

    public void setPeriodRemarks(String periodRemarks) {
        this.periodRemarks = periodRemarks;
    }
}

package com.villagebanking.BOObjects;

public class BOPeriod extends BOBase {
    private long periodType;
    private String periodName;
    private String actualDate;
    private String periodRemarks;
    private long periodValue;
    private String periodStatus;

    public long getPeriodType() {
        return periodType;
    }

    public void setPeriodType(long periodType) {
        this.periodType = periodType;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getActualDate() {
        return actualDate;
    }

    public void setActualDate(String actualDate) {
        this.actualDate = actualDate;
    }

    public String getPeriodRemarks() {
        return periodRemarks;
    }

    public void setPeriodRemarks(String periodRemarks) {
        this.periodRemarks = periodRemarks;
    }

    public long getPeriodValue() {
        return periodValue;
    }

    public void setPeriodValue(long periodValue) {
        this.periodValue = periodValue;
    }

    public String getPeriodStatus() {
        return periodStatus;
    }

    public void setPeriodStatus(String periodStatus) {
        this.periodStatus = periodStatus;
    }
}

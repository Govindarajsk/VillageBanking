package com.villagebanking.BOObjects;

public class BOPeriod extends BOBase {
    private int periodType;
    private String periodName;
    private String actualDate;
    private String periodRemarks;
    private long periodValue;

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
}

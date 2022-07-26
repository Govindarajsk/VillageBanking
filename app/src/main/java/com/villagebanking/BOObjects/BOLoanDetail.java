package com.villagebanking.BOObjects;

public class BOLoanDetail extends BOBase {
    private BOKeyValue periodInfo;
    private long loanHeaderKey;
    private double emiAmount;
    private long emiNo;
    private String emiStatus;


    public long getLoanHeaderKey() {
        return loanHeaderKey;
    }

    public void setLoanHeaderKey(long loanHeaderKey) {
        this.loanHeaderKey = loanHeaderKey;
    }
    public long getEmiNo() {
        return emiNo;
    }

    public void setEmiNo(long installment) {
        this.emiNo = installment;
    }

    public BOKeyValue getPeriodInfo() {
        if (periodInfo == null) periodInfo = new BOKeyValue();
        return periodInfo;
    }

    public void setPeriodInfo(BOKeyValue periodInfo) {
        this.periodInfo = periodInfo;
    }

    public double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(double emiAmount) {
        this.emiAmount = emiAmount;
    }

    public String getEmiStatus() {
        return emiStatus;
    }

    public void setEmiStatus(String emiStatus) {
        this.emiStatus = emiStatus;
    }
}

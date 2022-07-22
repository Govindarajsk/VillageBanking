package com.villagebanking.BOObjects;

public class BOLoanDetail extends BOBase {
    private BOKeyValue periodInfo;
    private double amount;
    private long installment;

    public long getInstallment() {
        return installment;
    }

    public void setInstallment(long installment) {
        this.installment = installment;
    }

    public BOKeyValue getPeriodInfo() {
        if (periodInfo == null) periodInfo = new BOKeyValue();
        return periodInfo;
    }

    public void setPeriodInfo(BOKeyValue periodInfo) {
        this.periodInfo = periodInfo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}

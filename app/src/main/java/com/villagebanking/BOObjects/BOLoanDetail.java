package com.villagebanking.BOObjects;

public class BOLoanDetail extends BOBase{
    private long periodKey;
    private double amount;
    private double charges;

    public long getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(long periodKey) {
        this.periodKey = periodKey;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

}

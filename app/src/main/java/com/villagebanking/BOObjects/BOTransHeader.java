package com.villagebanking.BOObjects;

public class BOTransHeader extends BOBase {
    private String tableName;
    private long tableLinkKey;
    private long periodKey;
    private long forignKey;
    private double totalAmount;
    private double paidAmount;
    private double balanceAmount;
    private BOPeriod periodDetail;

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

    public long getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(long periodKey) {
        this.periodKey = periodKey;
    }

    public long getForignKey() {
        return forignKey;
    }

    public void setForignKey(long forignKey) {
        this.forignKey = forignKey;
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

    public BOPeriod getPeriodDetail() {
        if (periodDetail == null) periodDetail = new BOPeriod();
        return periodDetail;
    }

    public void setPeriodDetail(BOPeriod periodDetail) {
        this.periodDetail = periodDetail;
    }
}

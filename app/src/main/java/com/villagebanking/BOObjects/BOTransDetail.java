package com.villagebanking.BOObjects;

public class BOTransDetail extends BOBase{
    private long headerKey;
    private Double amount;
    private String transDate;
    private String narration;

    public long getHeaderKey() {
        return headerKey;
    }

    public void setHeaderKey(long headerKey) {
        this.headerKey = headerKey;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}

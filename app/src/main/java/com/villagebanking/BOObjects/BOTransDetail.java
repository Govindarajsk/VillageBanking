package com.villagebanking.BOObjects;

public class BOTransDetail extends BOTransHeader {
    private long headerKey;
    private long parentKey;
    private long childKey;
    private Double amount;

    public long getHeaderKey() {
        return headerKey;
    }

    public void setHeaderKey(long headerKey) {
        this.headerKey = headerKey;
    }

    public long getParentKey() {
        return parentKey;
    }

    public void setParentKey(long parentKey) {
        this.parentKey = parentKey;
    }

    public long getChildKey() {
        return childKey;
    }

    public void setChildKey(long childKey) {
        this.childKey = childKey;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

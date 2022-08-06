package com.villagebanking.BOObjects;

import java.sql.Blob;

public class BOPerson extends BOBase {

    private String fName;
    private String lName;
    private long numMobile;
    private String accountNo;
    private long score;
    private BOKeyValue reference1;
    private BOKeyValue reference2;

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public long getMobile() {
        return numMobile;
    }

    public void setMobile(long numMobile) {
        this.numMobile = numMobile;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public BOKeyValue getReference1() {
        if (reference1 == null) reference1 = new BOKeyValue();
        return reference1;
    }

    public void setReference1(BOKeyValue reference1) {
        this.reference1 = reference1;
    }

    public BOKeyValue getReference2() {
        if (reference2 == null) reference2 = new BOKeyValue();
        return reference2;
    }

    public void setReference2(BOKeyValue reference2) {
        this.reference2 = reference2;
    }

    public String getFullName() {
        return getFName() + "." + getLName();
    }
}

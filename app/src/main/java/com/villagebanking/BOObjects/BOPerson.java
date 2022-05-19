package com.villagebanking.BOObjects;

import java.sql.Blob;

public class BOPerson extends BOBase {

    public String getStrFName() {
        return strFName;
    }

    public void setStrFName(String strFName) {
        this.strFName = strFName;
    }

    public String getStrLName() {
        return strLName;
    }

    public void setStrLName(String strLName) {
        this.strLName = strLName;
    }

    public long getNumMobile() {
        return numMobile;
    }

    public void setNumMobile(long numMobile) {
        this.numMobile = numMobile;
    }

    public int getReference1() {
        return reference1;
    }

    public void setReference1(int reference1) {
        this.reference1 = reference1;
    }

    public int getReference2() {
        return reference2;
    }

    public void setReference2(int reference2) {
        this.reference2 = reference2;
    }

    public Blob getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Blob profilePic) {
        this.profilePic = profilePic;
    }

    public Blob getPersonSign() {
        return personSign;
    }

    public void setPersonSign(Blob personSign) {
        this.personSign = personSign;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    private String strFName;
    private String strLName;
    private long numMobile;
    private int reference1;
    private int reference2;
    private Blob profilePic;
    private Blob personSign;
    private String accountNo;

    public  String getFullName(){
        return getStrFName()+"."+getStrLName();
    }
}

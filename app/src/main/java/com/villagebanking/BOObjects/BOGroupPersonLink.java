package com.villagebanking.BOObjects;

public class BOGroupPersonLink {
    public int getKeyID() {
        return keyID;
    }

    public void setKeyID(int keyID) {
        this.keyID = keyID;
    }

    public int getGroup_key() {
        return group_key;
    }

    public void setGroup_key(int group_key) {
        this.group_key = group_key;
    }

    public int getPerson_key() {
        return person_key;
    }

    public void setPerson_key(int person_key) {
        this.person_key = person_key;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public String getPerson_role() {
        return person_role;
    }

    public void setPerson_role(String person_role) {
        this.person_role = person_role;
    }

    private int keyID;
    private int group_key;
    private int person_key;
    private int orderBy;
    private String person_role;
}

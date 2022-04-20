package com.villagebanking.BOObjects;

public class BOGroupPersonLink extends BOBase {

    public long getGroup_key() {
        return group_key;
    }

    public void setGroup_key(long group_key) {
        this.group_key = group_key;
    }

    public long getPerson_key() {
        return person_key;
    }

    public void setPerson_key(long person_key) {
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

    private long group_key;
    private long person_key;
    private int orderBy;
    private String person_role;
}

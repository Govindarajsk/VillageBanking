package com.villagebanking.BOObjects;

public class BOGroupPersonLink extends BOBase {

    private long group_Key;
    private long person_Key;
    private int orderBy;
    private String person_role;

    public long getGroup_Key() {
        return group_Key;
    }

    public void setGroup_Key(long group_Key) {
        this.group_Key = group_Key;
    }

    public long getPerson_Key() {
        return person_Key;
    }

    public void setPerson_Key(long person_Key) {
        this.person_Key = person_Key;
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



    private BOKeyValue person;
    private BOKeyValue group;
    public BOKeyValue getPerson() {
        if (person == null) person = new BOKeyValue();
        return person;
    }

    public void setPerson(BOKeyValue person) {
        this.person = person;
    }

    public BOKeyValue getGroup() {
        if (group == null) group = new BOKeyValue();
        return group;
    }

    public void setGroup(BOKeyValue group) {
        this.group = group;
    }

}

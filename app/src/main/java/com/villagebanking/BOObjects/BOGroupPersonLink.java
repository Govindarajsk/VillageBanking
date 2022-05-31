package com.villagebanking.BOObjects;

public class BOGroupPersonLink extends BOBase {

    public BOGroup getGroup_Detail() {
        if(group_detail==null)group_detail=new BOGroup();
        return group_detail;
    }
    public void setGroup_Detail(BOGroup group_detail) {
        this.group_detail = group_detail;
    }

    public BOPerson getPerson_Detail() {
        if(person_detail==null)person_detail=new BOPerson();
        return person_detail;
    }
    public void setPerson_Detail(BOPerson person_detail) {
        this.person_detail = person_detail;
    }

    private BOGroup group_detail;
    private BOPerson person_detail;
    private int orderBy;

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

    private String person_role;
}

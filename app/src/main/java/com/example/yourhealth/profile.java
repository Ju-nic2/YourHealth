package com.example.yourhealth;

public class profile {
    private String name;
    private String sex;
    private String purpose;
    private String where;
    private String userphothurl;

    public profile(String name,String sex,String purpose,String where, String userphothurl){
        this.name = name;
        this.sex = sex;
        this.purpose = purpose;
        this.where = where;
        this.userphothurl = userphothurl;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSex(){
        return this.sex;
    }
    public void setSex(String sex){
        this.sex = sex;
    }

    public String getPurpose(){
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getWhere(){
        return this.where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
    public String getUserphothurl(){
        return this.userphothurl;
    }

    public void setUserphothurl(String userphothurl) {
        this.userphothurl = userphothurl;
    }
}

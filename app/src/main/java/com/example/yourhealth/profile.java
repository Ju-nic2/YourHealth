package com.example.yourhealth;

import java.util.ArrayList;

public class profile {

    private String name;
    private String job;
    private String sex;
    private String purpose;
    private String where;
    private String userphothurl;
    private ArrayList<String> storage = new ArrayList<String>();
    private Routine routine;

    public profile(String name,String sex,String purpose,String where, String userphothurl,String job, ArrayList<String> storage,Routine routine){
        this.name = name;
        this.sex = sex;
        this.purpose = purpose;
        this.where = where;
        this.userphothurl = userphothurl;
        this.job = job;
        this.storage = storage;
        this.routine = routine;
    }
    public profile(){}

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

    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }

    public Routine getRoutine() {
        return routine;
    }
    public void setRoutine(Routine routine) {
        this.routine = routine;
    }


}

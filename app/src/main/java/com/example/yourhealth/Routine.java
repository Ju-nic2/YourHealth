package com.example.yourhealth;

import java.util.ArrayList;

public class Routine {

    private String title;
    private String userUid;
    private int last;
    private ArrayList<diary_data_box> routine = new ArrayList<diary_data_box>();

    public Routine(String title,String userUid,ArrayList<diary_data_box> routine,int last){
        this.title = title;
        this.userUid = userUid;
        this.routine=routine;
        this.last = last;
    }
    public Routine(){}

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserUid(){
        return this.userUid;
    }
    public void setYserUid(String userUid) {
        this.userUid = userUid;
    }

    public ArrayList<diary_data_box> getRoutine(){
        return this.routine;
    }
    public void setRoutine(ArrayList<diary_data_box> routine) {
        this.routine = routine;
    }

    public int getLast(){
        return this.last;
    }
    public void setLast(int title) {
        this.last = last;
    }

    public diary_data_box getCurRoutine(int cur){
        return routine.get(cur);
    }


}

package com.example.yourhealth;

import java.io.Serializable;

public class postContent implements Serializable
{
    private String title;
    private String content;
    private int place;
    private int difficulty;
    private int sex;
    private int frequency;
    private int time;
    private Boolean completed;
    private String photo;
    private Routine routine;
    private String userID;
    private String category1;
    private String category2;
    private String category3;
    private String category4;
    private int num_heart;




    private int postID;
    final int PLACE_FITNESSCENTER = 0;
    final int PLACE_HOME = 1;
    final int PLACE_OUTDOOR = 2;
    final int DIFFICULTY_NOVICE = 3;
    final int DIFFICULTY_INTERMEDIATE = 4;
    final int DIFFICULTY_ADVANCE = 5;
    final int SEX_MALE = 6;
    final int SEX_FEMALE = 7;
    final int FREQUENCY_TOTHREE = 8;
    final int FREQUENCY_FOURFIVE = 9;
    final int FREQUENCY_SIXTOSEVEN = 10;
    final int TIME_TWENTYTOFORTY = 11;
    final int TIME_FORTYTOSEVENTY = 12;
    final int TIME_ABOVESEVENTY = 13;
    public postContent(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }
    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public String getCategory4() {
        return category4;
    }

    public void setCategory4(String category4) {
        this.category4 = category4;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getNum_heart() {
        return num_heart;
    }

    public void setNum_heart(int num_heart) {
        this.num_heart = num_heart;
    }
}

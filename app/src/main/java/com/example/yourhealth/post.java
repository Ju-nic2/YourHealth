package com.example.yourhealth;

public class post {
    private String title;
    private String name;
    private int resId;
    private int postID;

    public post(String title,String name,int resId, int postID){
        this.title = title;
        this.name = name;

        this.resId = resId;
        this.postID = postID;
    }
    public post(){}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
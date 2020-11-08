package com.example.yourhealth;


public class postBox {
    private String category = "카테고리";
    private post post1;
    private post post2;
    private post post3;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public post getPost1() {
        return post1;
    }

    public void setPost1(post post1){
        this.post1 = post1;
    }

    public post getPost2() {
        return post2;
    }

    public void setPost2(post post2){
        this.post2 = post2;
    }

    public post getPost3() {
        return post3;
    }

    public void setPost3(post post3){
        this.post3 = post3;
    }


}
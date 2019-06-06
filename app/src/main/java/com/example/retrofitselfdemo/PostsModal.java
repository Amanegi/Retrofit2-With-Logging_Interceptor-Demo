package com.example.retrofitselfdemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//POJO representation of Json response copied using jsonschema2pojo.com
//here we are using get method so we don't need setters

public class PostsModal {
    //SerializedName annotation is used if we change the key of the json
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;

    //we don't add id because the current api we are using assign it itself
    public PostsModal(Integer userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}

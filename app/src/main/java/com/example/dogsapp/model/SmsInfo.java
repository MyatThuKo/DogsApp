package com.example.dogsapp.model;

public class SmsInfo {

    public String to;
    public String text;
    public String imageURL;

    public SmsInfo(String to, String text, String imageURL) {
        this.to = to;
        this.text = text;
        this.imageURL = imageURL;
    }
}

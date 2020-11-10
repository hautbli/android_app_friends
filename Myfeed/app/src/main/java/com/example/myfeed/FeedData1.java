package com.example.myfeed;

import android.graphics.Bitmap;

public class FeedData1 {
    private String imageview;
    private String textview;
    private Bitmap bitmapimageview;
    private String uid;
    private int currenttime;

    public int getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(int currenttime) {
        this.currenttime = currenttime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Bitmap getBitmapimageview() {
        return bitmapimageview;
    }

    public void setBitmapimageview(Bitmap bitmapimageview) {
        this.bitmapimageview = bitmapimageview;
    }

    public String getImageview() {
        return imageview;
    }

    public void setImageview(String imageview) {
        this.imageview = imageview;
    }

    public String getTextview() {
        return textview;
    }

    public void setTextview(String textview) {
        this.textview = textview;
    }
    public FeedData1(){
    }

    public FeedData1(String textview, String imageview , String uid, int currenttime
    ){
        this.imageview = imageview;
        this.textview = textview;
        this.uid=uid;
        this.currenttime=currenttime;

    }



}

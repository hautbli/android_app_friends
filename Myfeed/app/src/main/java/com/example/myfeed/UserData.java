package com.example.myfeed;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class UserData {
    private String profile_imageview;
    private String profile_textview;
    private String profile_name;
    private Bitmap profile_bti_imageview;
    private ArrayList<StopwatchData> stopwatchData;
    private ArrayList<Integer> currenttimeData;

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }




    public String getProfile_imageview() {

        return profile_imageview;
    }

    public void setProfile_imageview(String profile_imageview) {
        this.profile_imageview = profile_imageview;
    }

    public String getProfile_textview() {

        return profile_textview;
    }

    public void setProfile_textview(String profile_textview) {
        this.profile_textview = profile_textview;
    }

    public Bitmap getProfile_bti_imageview() {
        return profile_bti_imageview;
    }

    public void setProfile_bti_imageview(Bitmap profile_bti_imageview) {
        this.profile_bti_imageview = profile_bti_imageview;
    }

    public ArrayList<StopwatchData> getStopwatchData() {
        return stopwatchData;
    }

    public void setStopwatchData(ArrayList<StopwatchData> stopwatchData) {
        this.stopwatchData = stopwatchData;
    }

    public ArrayList<Integer> getCurrenttimeData() {
        return currenttimeData;
    }

    public void setCurrenttimeData(ArrayList<Integer> currenttimeData) {
        this.currenttimeData = currenttimeData;
    }

    public UserData(String profile_imageview, String profile_textview, String profile_name, Bitmap profile_bti_imageview, ArrayList<StopwatchData> stopwatchData, ArrayList<Integer> currenttimeData) {
        this.profile_imageview = profile_imageview;
        this.profile_textview = profile_textview;
        this.profile_bti_imageview = profile_bti_imageview;
        this.stopwatchData = stopwatchData;
        this.currenttimeData = currenttimeData;
        this.profile_name = profile_name;

    }

    public UserData() {


    }
    public UserData(String profile_imageview, String profile_name, String profile_textview) {
        this.profile_name=profile_name;
        this.profile_imageview = profile_imageview;
        this.profile_textview = profile_textview;

    }



}

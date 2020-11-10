package com.example.myfeed;

public class StopwatchData {
    private String currenttime_fin,currenttime_start,stroll_time,date_text;

    public StopwatchData(String currenttime_fin, String currenttime_start, String stroll_time, String date_text) {
        this.currenttime_fin = currenttime_fin;
        this.currenttime_start = currenttime_start;
        this.stroll_time = stroll_time;
        this.date_text = date_text;
    }

    public String getCurrenttime_fin() {
        return currenttime_fin;
    }

    public void setCurrenttime_fin(String currenttime_fin) {
        this.currenttime_fin = currenttime_fin;
    }

    public String getCurrenttime_start() {
        return currenttime_start;
    }

    public void setCurrenttime_start(String currenttime_start) {
        this.currenttime_start = currenttime_start;
    }

    public String getStroll_time() {
        return stroll_time;
    }

    public void setStroll_time(String stroll_time) {
        this.stroll_time = stroll_time;
    }

    public String getDate_text() {
        return date_text;
    }

    public void setDate_text(String date_text) {
        this.date_text = date_text;
    }
}

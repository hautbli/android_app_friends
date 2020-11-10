package com.example.myfeed;

public class WeatherData {
    private String region;
    private String my_imgUrl;
    private  String region_name;

    public WeatherData(String region_name, String region, String my_imgUrl) {
        this.region_name = region_name;
        this.region = region;
        this.my_imgUrl = my_imgUrl;

    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMy_imgUrl() {
        return my_imgUrl;
    }

    public void setMy_imgUrl(String my_imgUrl) {
        this.my_imgUrl = my_imgUrl;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}

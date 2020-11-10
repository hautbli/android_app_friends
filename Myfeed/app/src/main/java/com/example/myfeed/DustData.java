package com.example.myfeed;

public class DustData {
    private String region;
    private String dust_h;
    private String dust_n;

    public DustData(String region, String dust_h, String dust_n) {
        this.region = region;
        this.dust_h = dust_h;
        this.dust_n = dust_n;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDust_h() {
        return dust_h;
    }

    public void setDust_h(String dust_h) {
        this.dust_h = dust_h;
    }

    public String getDust_n() {
        return dust_n;
    }

    public void setDust_n(String dust_n) {
        this.dust_n = dust_n;
    }
}

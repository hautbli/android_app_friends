package com.example.myfeed;

public class NewsData {
    private String title_news;
    private String contents_news;
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NewsData(String title_news, String contents_news, String url) {
        this.title_news = title_news;
        this.contents_news = contents_news;
        this.url = url;
    }

    public NewsData(){}

    public String getTitle_news() {
        return title_news;
    }

    public void setTitle_news(String title_news) {
        this.title_news = title_news;
    }

    public String getContents_news() {
        return contents_news;
    }

    public void setContents_news(String contents_news) {
        this.contents_news = contents_news;
    }
}

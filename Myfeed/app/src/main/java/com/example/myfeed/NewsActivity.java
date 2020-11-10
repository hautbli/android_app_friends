package com.example.myfeed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    private ArrayList<NewsData> nlist = new ArrayList<>();
    private NewsAdapter news_adapter;
    NewsData newsData;
    String keyword;
    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ActionBar ab = getSupportActionBar();
        ab.hide();
imm = (InputMethodManager)getSystemService(NewsActivity.this.INPUT_METHOD_SERVICE);
        Button btn = (Button) findViewById(R.id.searchBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView searchText = (TextView) findViewById(R.id.searchText);
                //final TextView searchResult = (TextView) findViewById(R.id.searchResult);
                keyword = searchText.getText().toString();


                Thread thread = new Thread(() -> {
                    try {
                        getNaverSearch(keyword);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();

//                // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
//                //feedAdapter.notifyItemInserted(0);
                buildRecyclerView();

                //   dialogPath = null;

                // 이미지 좋아요 값 디폴트 저장



                //searchResult.setText(str);

           imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(btn.getWindowToken(), 0);
            }
        });
    }

    public ArrayList<NewsData> getNaverSearch(String keyword) {


        String clientID = "5uIDEsoUJTph_ollBNkw";
        String clientSecret = "GQjpSu4AOp";
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();


        try {
            String text = URLEncoder.encode(keyword, "UTF-8");

            String apiURL = "https://openapi.naver.com/v1/search/news.xml?query=" + text + "&display=10" + "&start=1";

            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", clientID);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            String tag;
            //inputStream으로부터 xml값 받기
            xpp.setInput(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            xpp.next();
            int eventType = xpp.getEventType();

            NewsData newsData = new NewsData();
            //ArrayList<NewsData> newsList = new ArrayList<>();
            int xx=0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                xx++;
                if (eventType == XmlPullParser.START_TAG) {
                    tag = xpp.getName(); //태그 이름 얻어오기

                    switch (tag) {
                        case "title":
                            newsData = new NewsData();
                            xpp.next();
//                        sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            String Title = xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
                            String Title1 = Title.replaceAll("(&quot;)","");
                            newsData.setTitle_news(Title1);
                            break;
                        case "description":
                            xpp.next();
//                        sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            String Contents = xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
                            String Contents1 = Contents.replaceAll("(&quot;)","");

                            newsData.setContents_news(Contents1);
                            break;
                        case "link":
                            xpp.next();
//                        sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));

                            String link = xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");
                            newsData.setUrl(link);
                            nlist.add(newsData);

                            break;
                    }
                }
                eventType = xpp.next();
            }
            System.out.println(xx);
            nlist.remove(0);
            return nlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    private void buildRecyclerView() {
        // 빌드 리사이클러뷰
        RecyclerView news_RecyclerView = findViewById(R.id.news_recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        news_adapter = new NewsAdapter(this, nlist); // 어댑터 받고!
        news_RecyclerView.setLayoutManager(mLinearLayoutManager);
        news_RecyclerView.setAdapter(news_adapter);
        news_adapter.notifyDataSetChanged();
    }
}

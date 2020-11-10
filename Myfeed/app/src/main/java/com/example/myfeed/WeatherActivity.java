package com.example.myfeed;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<WeatherData> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        //AsyncTask 작동시킴(파싱)
        new Description().execute();
    }


    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//
//            //진행다일로그 시작
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("잠시 기다려 주세요.");
//            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc;
                doc = Jsoup.connect("https://weather.naver.com/rgn/cityWetrMain.nhn").get();
                Elements mElementDataSize = doc.select("tbody").select("tr"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
//
//                      String region = elem.select(" td ul[class=text]").text();
//                      String my_imgUrl = elem.select(" td p[class=icon] img").attr("src");

                    String region_name = elem.select(" th a").text(); // 지역 이름
                    String region = elem.select(" td[class=line]").text(); // 오후만
                    String my_imgUrl = elem.select(" td[class=line] p[class=icon] img").attr("src");


                    //  String my_imgUrl = elem.select("li div[class=thumb] a img").attr("src"); 영화 이미지

//                    String my_link = elem.select("li div[class=thumb] a").attr("href");
//
//                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.
//                    Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
//                    String my_release = rElem.select("dd").text();
//
//                    Element dElem = elem.select("dt[class=tit_t2]").next().first();
//                    String my_director = "감독: " + dElem.select("a").text();

                    //ArrayList에 계속 추가한다.

                    if(list.size()<12) { // 5개 null값이 들어와서 리스트 사이즈 제한함..
                        list.add(new WeatherData(region_name, region, my_imgUrl));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            WeatherAdapter weatherAdapter = new WeatherAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(weatherAdapter);

          //  progressDialog.dismiss();
        }

    }
}

package com.example.myfeed;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class DustActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DustData> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust);

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
                doc = Jsoup.connect("https://search.daum.net/search?nil_suggest=btn&w=tot&DA=SBC&q=%EB%AF%B8%EC%84%B8%EB%A8%BC%EC%A7%80+").get();
                Elements mElementDataSize = doc.select("div[class=map_region]").select("li"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.


                      String region = elem.select("li [class=tit_region]").text();
                      String dust_h = elem.select("li [class=screen_out]").text();
                      String dust_n = elem.select("li [class=txt_state]").text();

//                    String my_link = elem.select("li div[class=thumb] a").attr("href");
//                    String my_imgUrl = elem.select("li div[class=thumb] a img").attr("src");
//
//                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.
//                    Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
//                    String my_release = rElem.select("dd").text();
//
//                    Element dElem = elem.select("dt[class=tit_t2]").next().first();
//                    String my_director = "감독: " + dElem.select("a").text();

                    //ArrayList에 계속 추가한다.
                  list.add(new DustData(region,dust_h,dust_n));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.

             int numberOfColumns = 2;

            DustAdapter dustAdapter = new DustAdapter(list);
            recyclerView.setLayoutManager(new GridLayoutManager(DustActivity.this, numberOfColumns));
            recyclerView.setAdapter(dustAdapter);

          //  progressDialog.dismiss();
        }

    }
}

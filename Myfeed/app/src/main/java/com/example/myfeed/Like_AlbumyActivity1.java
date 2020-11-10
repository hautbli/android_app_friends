package com.example.myfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Like_AlbumyActivity1 extends AppCompatActivity {

    // 좋아요 사진 있는 앨범
    // 리사이클러뷰 사용

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<FeedData1> feedList;
    String MY_DATA = "MY_DATA";
    String MY_FEED = "MY_FEED";
    Button slideshow_btn;
    ArrayList<String> listOfAllImages = new ArrayList<String>();
    FeedData1 feedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_album);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        final int numberOfColumns = 3;

        // use a linear layout manager

        recyclerView = findViewById(R.id.gallery_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mAdapter = new LikeAlbumAdapter(this, getImagesPath());
        recyclerView.setAdapter(mAdapter);


        // 슬라이드쇼 버튼 누르면
        slideshow_btn = findViewById(R.id.slideshow_btn);
        slideshow_btn.setOnClickListener(v -> {

            Intent intent = new Intent(this, SlideshowActivity.class); // 좋아요 앨범 이미지 리스트를 슬라이드쇼에 보낸당, 액티비티 전환
            intent.putStringArrayListExtra("slideshow_img", listOfAllImages);
            startActivity(intent);

        });

    }
    // ...


//제이슨을 불러서 거기서 이미지만 스트링 불러오면 끝.


    public ArrayList<String> getImagesPath() { // 저장한 거 불러오기!!!

        SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(MY_FEED, null); // 키 값 , 기본값
        Type type = new TypeToken<ArrayList<FeedData1>>() { // 처음에 이게 아래있어서 이미지 안됐음 ...
        }.getType();
        feedList = gson.fromJson(json, type);
        String json1 = prefs.getString(MyphotoActivity3.uid, null); // 키 값 , 기본값
        UserData userData = gson.fromJson(json1, UserData.class);
        ArrayList<Integer> timelist;
        timelist = userData.getCurrenttimeData();

        // 유저데이타.타임리스트에 있는게 피드데이터의 current에 있으면
        // 피드데이타에 이미지뷰 가져옴.
        for (int i = 0; i < feedList.size(); i++) {
            feedData = feedList.get(i);
            if (timelist.contains(feedData.getCurrenttime())) { // 짜증나 위에서 i 6번 반복했음..
                listOfAllImages.add(feedData.getImageview());
            }
        }
        return listOfAllImages;
    }
}


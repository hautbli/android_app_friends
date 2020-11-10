package com.example.myfeed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class FeedEditActivity extends AppCompatActivity {
    private Activity activity;
    private ArrayList<FeedData1> feedList;
    private String dialogPath;
    private String ad_dialogPath;
    private int mychoose;
    String MY_DATA = "MY_DATA";
    String MY_FEED = "MY_FEED";

    Button ButtonSubmit;
    EditText editText;
    Button imagechange;
    ImageView dialogimageview;
    boolean like_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_edit);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        ButtonSubmit = findViewById(R.id.upload_btn);
        editText = findViewById(R.id.context_edittext);
        imagechange = findViewById(R.id.change_btn);
        dialogimageview = findViewById(R.id.dialogimageview);

        //  해당 줄에 입력되어 있던 데이터를 불러와서 다이얼로그에 보여준다
        SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
        Gson gson = new Gson();
        String json_feedList_get = prefs.getString(MY_FEED, "");
        feedList = gson.fromJson(json_feedList_get, new TypeToken<ArrayList<FeedData1>>() {
        }.getType());


        mychoose = getIntent().getIntExtra("mychoose", -1); // 리사이클러뷰에서 선택한 position 값

        FeedData1 mfeedData = feedList.get(mychoose);

        editText.setText(mfeedData.getTextview()); //  수정 누르면 원래 있던 값 나옴
        // editimageview.setImageBitmap(feedList.get(getAdaptermychoose()).getBitmapimageview()); //  수정 누르면 원래 있던 값 나옴

        Bitmap dbmp = BitmapFactory.decodeFile(mfeedData.getImageview());
        dialogimageview.setImageBitmap(dbmp);

        //비트맵으로 하거나 경로로 하거나
//        Glide.with(this)
//                .load(mfeedData.getImageview()).override(1000)
//                .into(dialogimageview); // load.에서 처음엔 바로 데이터를 받았는데 그렇게 안하고 그 데이터에서
//        //  겟이미지뷰를 해서 사진이 불러와졌음! 흠.. 어려웠따...
        // 다이얼로그에서 사진 변경 누르면 !!!

        // 사진변경 버튼 갤러리액티비티로 감
        imagechange.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivityForResult(intent, 0);
        });

        // 수정다하고 완료버튼 누르면..
        // 올리기 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로
        ButtonSubmit.setOnClickListener(v -> {
            if (dialogPath == null) {
                //Toast.makeText(this, "이미지를 업로드해야합니다", Toast.LENGTH_SHORT).show();
                String contents = editText.getText().toString();

                mfeedData.setTextview(contents);
//                FeedData1 feedData = new FeedData1(contents, mfeedData.getImageview(),like_value);

                feedList.set(mychoose, mfeedData);

                //  feedAdapter.notifyDataSetChanged();

                saveData();

                //   feedAdapter.notifyItemRangeChanged(mychoose, feedList.size());
                finish();

            }else {
                String contents = editText.getText().toString();
                // 8. ListArray에 있는 데이터를 변경하고

                mfeedData.setTextview(contents);
                mfeedData.setImageview(dialogPath);

                feedList.set(mychoose, mfeedData);

                //  feedAdapter.notifyDataSetChanged();

                saveData();

             //   feedAdapter.notifyItemRangeChanged(mychoose, feedList.size());
                finish();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    dialogPath = data.getStringExtra("dialogPath"); //지정된 인덱스에 해당하는 사진 경로를 얻는다
                    Bitmap dbmp = BitmapFactory.decodeFile(dialogPath); // 사진 경로로 비트맵을 얻는다
                    dialogimageview.setImageBitmap(dbmp);// dialogImageVIew가 지역변수 뭐시기 땜에 계속 NULL값이 떴는데 디버깅하면서 찾았음.
                    // 그리고 다이얼로그랑 이 액티비티의 레이아웃이랑 다른데 그걸 온크리에이트 위에서 해서 오류쓰.. !!!
                }
                break;
        }
    }

    private void saveData() {
        SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(feedList);
        editor.putString(MY_FEED, json);
        editor.commit();
    }
}

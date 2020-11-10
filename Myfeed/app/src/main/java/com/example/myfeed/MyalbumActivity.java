package com.example.myfeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyalbumActivity extends AppCompatActivity { // 좋아요 사진 있는 앨범
//    ArrayList <FeedData> feedList;
//    String MY_DATA ="MY_DATA";
//    String MY_FEED ="MY_FEED";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_myalbum);
//
//
//        //findViewById(R.id.slideshow_btn).setOnClickListener(onClickListener);
//        ActionBar ab = getSupportActionBar();
//        ab.hide();
//
//
//        final GridView gv = (GridView) findViewById(R.id.gridview1);
//        Mygridadapter mygridadapter = new Mygridadapter(this);
//        gv.setAdapter(mygridadapter);
//
//    }
//
//    public class Mygridadapter extends BaseAdapter{
//        Context context;
//        public  Mygridadapter(Context c){
//            context = c;
//        }
//
//        @Override
//        public int getCount() { // 그리드뷰에 보여질 이미지의 개수를 반환
//            return photoid.length;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//       int photoid []= new int[]{ //좋아요 그림들 모여있는 집합!!!
//                R.drawable.photopup, R.drawable.photopup2
//        };
//
//        @Override
//        public View getView(int position, View view, ViewGroup viewGroup) { // 각 그리드뷰의 칸마다 이미지 뷰를 생성해서 보여준다
//
//            ImageView imageView = new ImageView(context);
//            imageView.setLayoutParams (new GridView.LayoutParams(300,300));
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            imageView.setPadding(3,3,3,3);
//
//            imageView.setImageResource(photoid[position]);
//
//            final int pos = position;
//            imageView.setOnClickListener(new View .OnClickListener(){// 이미지  클릭하면 다이얼로그로 크게 볼 수 있음
//                public void onClick (View v){
//                    View dialogView = (View) View.inflate(MyalbumActivity.this, R.layout.myalbum_dialog, null);
//                    AlertDialog.Builder dlg = new AlertDialog.Builder(MyalbumActivity.this);
//                    ImageView heartphoto = (ImageView) dialogView.findViewById(R.id.heartphoto);
//                    heartphoto.setImageResource(photoid[pos]);
//                    //dlg.setTitle("");
//                    dlg.setView(dialogView);
//                    dlg.show();
//                }
//            });
//
//            return imageView;
//        }
//    }
//
//
//    //View.OnClickListener onClickListener = new View.OnClickListener() {
//
//
//       // @Override
//        //public void onClick(View v) {
//          //  switch (v.getId()){
//             //   case R.id.slideshow_btn:
//                    //나중에 연결하면 사용
//                    // mystartactivity(SlideshowActivity.class);
//                 //   break;
//
//            //}
//        //}
//    //};
//    private void mystartactivity(Class c) {
//
//        Intent intent = new Intent(this,c);
//
//        startActivity(intent);
//    }
//    public void loadData() { // 저장한 거 불러오기!!!
//        SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
//        Gson gson = new Gson();
//
//        // Glide.with(MyphotoActivity2.this).load(dialogPath).centerCrop().override(500).into(dialogImageVIew);
//        String json = prefs.getString(MY_FEED, null); // 키 값 , 기본값
//
//        //  json 패싱.. !!!!!! 저장된걸 가져와가져와..
//        String json_list = gson.toJson(feedList);
//
//        StringBuffer sb1 = new StringBuffer();//그림
//        StringBuffer sb2 = new StringBuffer();//글
//
//        String str = json_list; //  이미지와 글이 채워진거..!!! 입력한거 없으면 null이지 뭐
//        try {
//            JSONArray jarray = new JSONArray(str);
//            for (int i = 0; i < jarray.length(); i++) {
//
//                JSONObject jObgect = jarray.getJSONObject(i);
//
//                String imageview = jObgect.getString("imageview"); // 이렇게 써있는거 옆에 값을 가져와랑
//                String textview = jObgect.getString("textview");
//
//                sb1.append(
//                        imageview
//                );
//                sb2.append(
//                        textview
//                );
//                //
//                String imagePath = sb1.toString();
//                Intent intent = new Intent();
//                intent.putExtra("dialogPath", imagePath);
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//
//        Type type = new TypeToken<ArrayList<FeedData>>() {
//        }.getType();
//
//        feedList = gson.fromJson(json, type);
//
//        if (feedList == null) {
//            feedList = new ArrayList<>();
//        }
//    }

}

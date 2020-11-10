package com.example.myfeed;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyphotoActivity3 extends AppCompatActivity {

// 내 게시글 피드 화면
    //   프로필 수정 -  이미지 / 글
    //  게시글 업로드   이미지 /글 수정 삭제 추가

    Button upload_btn, profile_edit_btn, logout_btn;
    ImageView Heart_btn, List_btn;
    private ImageView dialogImageVIew;
    private String dialogPath;
    private String profilePath_pre;
    ArrayList<FeedData1> feedList;
    Adapter1 adapter;
    String MY_NAME = "MY_NAME";
    String MY_CONTENTS = "MY_CONTENTS";
    String PROFILEIMAGE = "PROFILEIMAGE";
    String MY_DATA = "MY_DATA";
    String MY_FEED = "MY_FEED";
    private Context mContext;
    private int position;
    Button ButtonSubmit;
    EditText editText;
    Button imagechange;
    ImageView editimageview,Home_btn;
    int currenttime;
    ArrayList<Integer> timelist;
    static String uid;
    private UserData userData;
    private AdView mAdView;
    RecyclerView feed_RecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myphoto);

        ad();
        ActionBar ab = getSupportActionBar();
        ab.hide();

        if(uid==null) {
            uid = getIntent().getStringExtra("id");

        }
//        if(FirebaseAuth.getInstance().getCurrentUser() == null){
//            myStartActivity(SplashActivity.class);
//        }
        List_btn = findViewById(R.id.List_btn);
        List_btn.setOnClickListener((view) -> {
            Intent intent = new Intent(MyphotoActivity3.this, MenuActivity.class);
            startActivity(intent);
        });

        Home_btn = findViewById(R.id.Home_btn);
        Home_btn.setOnClickListener((view )-> {
            Intent intent = new Intent(MyphotoActivity3.this, NewsActivity.class);
            startActivity(intent);
        });
//
//
        profile_edit_btn = findViewById(R.id.profile_edit_btn);

        //프로필 편집으로 고고
        profile_edit_btn.setOnClickListener((view) -> {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity1.class);
            startActivity(intent);
        });

        Heart_btn = findViewById(R.id.Heart_btn);

        // 좋아요 앨범 가기
        Heart_btn.setOnClickListener((view) -> {
            //  Intent intent = new Intent(getApplicationContext(), MyalbumActivity.class);
            Intent intent = new Intent(getApplicationContext(), Like_AlbumyActivity1.class);
            startActivity(intent);
        });
//
//        logout_btn = findViewById(R.id.logout_btn);

        //>>>>>>>>>셰어드프리퍼런스 사용 - 프로필입력 ----------한 값을 저장한걸 불러옴  <<<<<<<<<<<<

        loadData();

        SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
        buildRecyclerView();

        SharedPreferences.Editor editor = prefs.edit();
//        feed_RecyclerView = findViewById(R.id.recycler1);
//        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MyphotoActivity2.this);
//        adapter = new Adapter(this, feedList, getSharedPreferences(MY_DATA, MODE_PRIVATE), userData, timelist,uid);
//        feed_RecyclerView.setLayoutManager(mLinearLayoutManager);
//        feed_RecyclerView.setAdapter(adapter);
//
//        editor.clear(); // 셰어드 저장 데이터 삭~제
//        editor.apply();

        // loadData();

//        if (feedList == null) {
//            feedList = new ArrayList<>();
//        }

        // buildRecyclerView();

        // S/F 저장한거 불러오기  (key, dafault values)jf


        String name = userData.getProfile_name();
        String contents = userData.getProfile_textview();
        String profilePath = userData.getProfile_imageview();




        //S/F에서 불러온 거 텍스트 뷰에 넣어서 SET해 놓는다.

        if (name == null || name.equals("")){
            ((TextView) findViewById(R.id.name)).setText("name");
        } else{
            ((TextView) findViewById(R.id.name)).setText(name);
        }

        if (contents == null || contents.equals("")){
            ((TextView) findViewById(R.id.textView)).setText("contents");

        } else{
            ((TextView) findViewById(R.id.textView)).setText(contents);

        }

        if (profilePath == null || profilePath.equals("")){
            ((ImageView) findViewById(R.id.puppyimage)).setImageDrawable(getResources().getDrawable(R.drawable.photo));
        }else{
            Bitmap bmp = BitmapFactory.decodeFile(profilePath);
            Bitmap bmp_pre = BitmapFactory.decodeFile(profilePath_pre);
            ((ImageView) findViewById(R.id.puppyimage)).setImageBitmap(bmp);
        }

//
//         새 게시글 업로드하기
        Button buttonInsert = (Button) findViewById(R.id.upload_btn);
        buttonInsert.setOnClickListener(v -> {
            final Dialog dlg = new Dialog(MyphotoActivity3.this);

            dlg.setContentView(R.layout.activity_photo_upload2);

            dlg.show();

            ButtonSubmit = dlg.findViewById(R.id.upload_btn);
            editText = dlg.findViewById(R.id.context_edittext);
            imagechange = dlg.findViewById(R.id.change_btn);
            editimageview = dlg.findViewById(R.id.dialogimageview);

            // 사진 변경 버튼 누르면 갤러리 불러옴!!!!!!!
            imagechange.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(MyphotoActivity3.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(((Activity) MyphotoActivity3.this),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                        if (ActivityCompat.shouldShowRequestPermissionRationale((MyphotoActivity3.this),
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions((MyphotoActivity3.this),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        } else {
                            ActivityCompat.requestPermissions((MyphotoActivity3.this),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                            Toast.makeText(MyphotoActivity3.this, "권한을 허용해 주세요g", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(MyphotoActivity3.this, GalleryActivity.class);
                        (MyphotoActivity3.this).startActivityForResult(intent, 0);
                    }
                }
            });

            // 3. 다이얼로그에 있는 업로드하기 올리기 버튼을 클릭하면
            ButtonSubmit.setOnClickListener(v1 -> {

                // 이미지 값이 없으면 업로드 못하게 하기
                if (dialogPath == null) {
                    Toast.makeText(MyphotoActivity3.this, "이미지를 업로드해야합니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 4. 사용자가 입력한 내용을 가져와서
                String contents1 = editText.getText().toString(); // 여기서 입력한 걸 아래에서 리사이클러뷰에 추가한다

                boolean like_value = false;


                long time_current_sv = System.currentTimeMillis();
                currenttime = (int) (time_current_sv / 1000); // 서비스 시작


                // 5. ArrayList에 추가하고
                FeedData1 feedData = new FeedData1(contents1, dialogPath, uid, currenttime); // My feedlist 둘다 String 이미지 , 텍스트

                feedList.add(0, feedData); //첫번째 줄에 삽입됨

//                // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                buildRecyclerView();


                //   dialogPath = null;

                // 이미지 좋아요 값 디폴트 저장

                saveData();
                adapter.notifyDataSetChanged();
                dlg.dismiss();
            });
            dlg.show();
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myStartActivity(GalleryActivity.class);
                } else {
                    startToast("권한을 허용해 주세요");
                }
            }
        }
    }

    @Override
    protected void onResume() {
        loadData();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    public void startToast(String mes) {
        Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(MyphotoActivity3.this, c);
        startActivityForResult(intent, 0);
    }

    private void saveData() {
        SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(feedList);
        editor.putString(MY_FEED, json);
        editor.commit();
    }

    public void loadData() { // 저장한 거 불러오기!!!
        SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
        Gson gson = new Gson();

        // Glide.with(MyphotoActivity2.this).load(dialogPath).centerCrop().override(500).into(dialogImageVIew);
        String json = prefs.getString(MY_FEED, null); // 키 값 , 기본값

        //  json 패싱.. !!!!!! 저장된걸 가져와가져와..
        String json_list = gson.toJson(feedList);

        StringBuffer sb1 = new StringBuffer();//그림
        StringBuffer sb2 = new StringBuffer();//글
        StringBuffer sb3 = new StringBuffer();//좋아요 값

        String str = json_list; //  이미지와 글이 채워진거..!!! 입력한거 없으면 null이지 뭐

        try {
            JSONArray jarray = new JSONArray(str);
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jObgect = jarray.getJSONObject(i);

                String imageview = jObgect.getString("imageview"); // 이렇게 써있는거 옆에 값을 가져와랑
                String textview = jObgect.getString("textview");

                sb1.append(
                        imageview
                );
                sb2.append(
                        textview
                );

                String imagePath = sb1.toString(); // 지역변수 생성
                Intent intent = new Intent();
                intent.putExtra("dialogPath", imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<ArrayList<FeedData1>>() {
        }.getType();

        feedList = gson.fromJson(json, type);

        if (feedList == null) {
            feedList = new ArrayList<>();
        }

        String json1 = prefs.getString(MyphotoActivity3.uid, null);

        // 유저데이터랑 피드데이터 비교해서 같은거있으면
        //피드데이터에 좋아요를 트루로 다르면 FALSE로 SET함 그래서 이걸로
        // 로드데이터 할 때 별이 표시되게 한다. (사용자마다 별 표시가 달라야하기 때문에
        //유저데이터 값이랑 비교함!!
        userData = gson.fromJson(json1, UserData.class);
        if (userData == null) {
            userData = new UserData();
        }

        timelist = userData.getCurrenttimeData();// 이게 있어야 uid에 저장된 currenttime이 어댑터에서 비교됨..

        buildRecyclerView();//데이터가 바뀐것을 알려줌
    }


    private void buildRecyclerView() {
        // 리사이클러뷰 시작 ( 나의 게시글!!)
        feed_RecyclerView = findViewById(R.id.recycler1);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MyphotoActivity3.this);
        adapter = new Adapter1(this, feedList, getSharedPreferences(MY_DATA, MODE_PRIVATE), userData, timelist , uid);
        feed_RecyclerView.setLayoutManager(mLinearLayoutManager);
        feed_RecyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    dialogPath = data.getStringExtra("dialogPath"); //지정된 인덱스에 해당하는 사진 경로를 얻는다
                    Bitmap dbmp = BitmapFactory.decodeFile(dialogPath); // 사진 경로로 비트맵을 얻는다
                    editimageview.setImageBitmap(dbmp);// dialogImageVIew가 지역변수 뭐시기 땜에 계속 NULL값이 떴는데 디버깅하면서 찾았음.
                    // 그리고 다이얼로그랑 이 액티비티의 레이아웃이랑 다른데 그걸 온크리에이트 위에서 해서 오류쓰.. !!!
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // 뒤로가기 버튼 못누르게 하기
    }

    public void ad(){



        MobileAds.initialize(this, getString(R.string.admob_app_id));


        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);


        // 광고가 제대로 로드 되는지 테스트 하기 위한 코드입니다.

        mAdView.setAdListener(new AdListener() {

            @Override

            public void onAdLoaded() {

                // Code to be executed when an ad finishes loading.

                // 광고가 문제 없이 로드시 출력됩니다.


            }


            @Override

            public void onAdFailedToLoad(int errorCode) {

                // Code to be executed when an ad request fails.

                // 광고 로드에 문제가 있을시 출력됩니다.


            }


            @Override

            public void onAdOpened() {

                // Code to be executed when an ad opens an overlay that

                // covers the screen.

            }


            @Override

            public void onAdClicked() {

                // Code to be executed when the user clicks on an ad.

            }


            @Override

            public void onAdLeftApplication() {

                // Code to be executed when the user has left the app.

            }


            @Override

            public void onAdClosed() {

                // Code to be executed when the user is about to return

                // to the app after tapping on an ad.

            }

        });

    }
}
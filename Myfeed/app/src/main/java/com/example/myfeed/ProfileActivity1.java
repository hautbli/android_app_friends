package com.example.myfeed;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;


public class ProfileActivity1 extends AppCompatActivity {
    //프로필 수정 클래스

    private EditText name_edittext;
    private EditText context_edittext;
    private SharedPreferences prefs;
    private String profilePath;

    private ImageView profileImageVIew;
    private Button changebtn;
    private Button profile_edit_btn;

    String MY_CONTENTS = "MY_CONTENTS";
    String PROFILEIMAGE = "PROFILEIMAGE";
    String MY_DATA ="MY_DATA";
    UserData userData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        loadData();

        ActionBar ab = getSupportActionBar();
        ab.hide();



     //   UID = getIntent().getStringExtra("id");



        changebtn=findViewById(R.id.change_btn);
        changebtn.setOnClickListener(onClickListener);
        profileImageVIew = findViewById(R.id.profileImageView);
        profile_edit_btn = findViewById(R.id.profile_edit_btn);
        profile_edit_btn.setOnClickListener(onClickListener);

        //셰어드프리퍼런스 준비단계..

        prefs = getSharedPreferences( MY_DATA, MODE_PRIVATE); // S/F의 객체 생성!! : name은 해당 S/F의 이름이고 이 이름으로 xml파일이 생성된다
        // mode는 읽고 쓰고 권한 관련된 것  일단 저걸로 고고띵


        String name = userData.getProfile_name();
        String contents = userData.getProfile_textview();
        profilePath = userData.getProfile_imageview();

        name_edittext = findViewById(R.id.name_edittext);
        context_edittext = findViewById(R.id.context_edittext);

       //입력해놓은 거 다시 프로필로 돌아왔을 때 볼 수 있음!!
       name_edittext.setText(name);
       context_edittext.setText(contents);

       Bitmap bmp = BitmapFactory.decodeFile(profilePath);
       profileImageVIew.setImageBitmap(bmp);
    }

    // 갤러리에서 선택한 이미지 불러오기 1!!!
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    profilePath = data.getStringExtra("profilePath"); //지정된 인덱스에 해당하는 사진 경로를 얻는다
                    Bitmap bmp = BitmapFactory.decodeFile(profilePath); // 사진 경로로 비트맵을 얻는다
                    profileImageVIew.setImageBitmap(bmp);
                }
                break;
            }
        }
    }

    public void savedata () {// 셰여드프리퍼런스 메소드!! (글)
        // get input text
        String name = name_edittext.getText().toString();
        String contents= context_edittext.getText().toString();




        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        if (userData == null) {
            UserData userData = new UserData(profilePath, name, contents);
        }
        userData.setProfile_textview(contents);
        userData.setProfile_imageview(profilePath);
        userData.setProfile_name(name);

        //타임리스트를 데이타로
        String json1 = gson.toJson(userData);
        editor.putString(MyphotoActivity3.uid, json1);
        editor.commit();

        Intent intent = new Intent(this,MyphotoActivity3.class);

     //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 뒤로가기 막아놓기!

       // intent.putExtra("image", bmp); // 비트맵 이미지를 인텐트로 넘기기 위해서



    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.change_btn:
                    if (ContextCompat.checkSelfPermission(ProfileActivity1.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProfileActivity1.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                        if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity1.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions(ProfileActivity1.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);

                        } else {
                            ActivityCompat.requestPermissions(ProfileActivity1.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                            startToast("권한을 허용해 주세요");
                        }
                    } else {
                        myStartActivity(GalleryActivity.class);
                                       }
                        break;

                case R.id.profile_edit_btn:


                    savedata();

                    Intent intent = new Intent(getApplicationContext(),MyphotoActivity3.class); // 화면 전환

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 뒤로가기 막아놓기!

                    startActivity(intent);

                    startToast("프로필 수정이 완료됐습니다");

                    }
            }
        };
        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            switch (requestCode) {
                case 1: {
                    if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        myStartActivity(GalleryActivity.class);
                    } else {
                        startToast("권한을 허용해 주세요");
                    }
                }
            }
        }


    private void startToast(String mes){
      Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();

    }
            private void myStartActivity(Class c) {
                Intent intent = new Intent(this, c);
                startActivityForResult(intent, 0);
            }
    public void loadData() { // 저장한 거 불러오기!!!
        SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
        Gson gson = new Gson();
        // Glide.with(MyphotoActivity2.this).load(dialogPath).centerCrop().override(500).into(dialogImageVIew);
        String json = prefs.getString(MyphotoActivity3.uid, null); // 키 값 , 기본값

        userData = gson.fromJson(json, UserData.class);

        if (userData == null) {
            userData = new UserData(null, null , null);
        }
    }

}

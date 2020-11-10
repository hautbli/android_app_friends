package com.example.myfeed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SlideshowActivity extends AppCompatActivity {
    ViewFlipper v_fllipper;
    ArrayList<String> listOfAllImages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        // 좋아요 앨범에서 생성되는 이미지를 받음
        ArrayList<String> listOfAllImages = getIntent().getStringArrayListExtra("slideshow_img");

        v_fllipper = findViewById(R.id.image_slide);

        for (String  image : listOfAllImages) {

            fllipperImages(image);
        }
    }
    private void fllipperImages(String image) {

        ImageView imageView = new ImageView(this);

        imageView.setImageURI(Uri.parse(image));

        v_fllipper.addView(imageView);      // 이미지 추가
        v_fllipper.setFlipInterval(4000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_fllipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
}

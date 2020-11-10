package com.example.myfeed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar ab = getSupportActionBar();
        ab.hide();


        LottieAnimationView animationView = findViewById(R.id.lottieAnimView);
        setUpAnimation(animationView);


        // 핸들러
        Handler splash_hd = new Handler();
        splash_hd.postDelayed(new splashhandler(), null, 3000);

//        //gif이미지
//        ImageView gif = (ImageView)findViewById(R.id.gif_imageview);
//        Glide.with(this).load(R.drawable.splash_puppy).into(gif);
    }

    private void setUpAnimation(LottieAnimationView animationView) {
        // 재생할 애니메이션 넣어준다.
        animationView.setAnimation("dog.json");
        // 반복횟수를 무한히 주고 싶을 땐 LottieDrawable.INFINITE or 원하는 횟수
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        // 시작
        animationView.playAnimation();
    }

    private class splashhandler implements Runnable {
        public void run() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // 뒤로가기 버튼 못누르게 하기
    }
}

package com.example.myfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);


        ImageView imageView;

        Intent intent = getIntent();
        imageView = findViewById(R.id.fullimageview);
        imageView.setImageResource(intent.getIntExtra("imageView", 0));

//        imageView= (ImageView)findViewById(R.id.imageView2) ;
//
//        Bundle bundle = getIntent().getExtras();
//        if(bundle != null){
//            int resid= bundle.getInt("image_url");
//            imageView.setImageResource(resid);
//
//
    }
}

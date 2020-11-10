package com.example.myfeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        findViewById(R.id.hotphoto_btn).setOnClickListener(onClickListener);
        findViewById(R.id.news_btn).setOnClickListener(onClickListener);
        findViewById(R.id.logout_btn).setOnClickListener(onClickListener);
        findViewById(R.id.wetaher_btn).setOnClickListener(onClickListener);
        findViewById(R.id.stopwatch_btn).setOnClickListener(onClickListener);
        ActionBar ab = getSupportActionBar();
        ab.hide();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.hotphoto_btn:
//                     mystartactivity(NewsActivity.class);
//                    break;

                case R.id.logout_btn:
                    FirebaseAuth.getInstance().signOut();
                    mystartactivity(MainActivity.class);
                    MyphotoActivity3.uid = null;

//                    getIntent().addFlags(FLAG_ACTIVITY_NEW_TASK);

                    break;
                case R.id.news_btn:
                    mystartactivity(DustActivity.class);
                    break;
                case R.id.wetaher_btn:
                    mystartactivity(WeatherActivity.class);
                    break;
                case R.id.stopwatch_btn:
                    mystartactivity(StopwatchActivity1.class);
                    break;

            }
        }
    };

    private void mystartactivity(Class c) {

        Intent intent = new Intent(this, c);
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);// 뒤로가기 꺼지기하려하는데 안됨..
        startActivity(intent);
    }
}

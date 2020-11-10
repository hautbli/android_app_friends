package com.example.myfeed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button Button_sign_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button_sign_in = findViewById(R.id.Button_sign_in);

        Button_sign_in.setOnClickListener((view)->{
            Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
            startActivity(intent);
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseFirestore db = FirebaseFirestore.getInstance(); //초기화

        ActionBar ab = getSupportActionBar();
        ab.hide();
    }
    @Override
    public void onBackPressed(){
        // 뒤로가기 버튼 못누르게 하기
    }

}

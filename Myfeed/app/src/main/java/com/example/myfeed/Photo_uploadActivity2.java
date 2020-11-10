package com.example.myfeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Photo_uploadActivity2 extends AppCompatActivity {

    private static final String TAG = "Photo_uploadActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload2);

        findViewById(R.id.upload_btn).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener= new View.OnClickListener(){
        @Override
        public void onClick (View v){  // 업로드 버튼 누르면
            switch (v.getId()){
                case R.id.change_btn :// 사진 변경 버튼 누르면

                        Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                        startActivity(intent);

                    break;
            }
        }
    };
    private void startToast(String mes){
        Toast.makeText(this, mes,Toast.LENGTH_SHORT).show();

    }

}

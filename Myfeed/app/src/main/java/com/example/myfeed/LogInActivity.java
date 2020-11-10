package com.example.myfeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ActionBar ab = getSupportActionBar();
        ab.hide();


        findViewById(R.id.Button_log_in).setOnClickListener(onClickListener);
        findViewById(R.id.Button_sign_in).setOnClickListener(onClickListener);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


    }
    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Button_log_in:
                    login();
                    break;

                case R.id.Button_sign_in:
                    Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };



    private void login() {
        email = ((EditText) findViewById(R.id.EditText_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.EditText_password)).getText().toString();

        Intent intent = new Intent(this,MyphotoActivity3.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 뒤로가기 꺼지기
        intent.putExtra("id", email);

        if (email.length() > 0 && password.length() > 0 ) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인이 완료되었습니다");
                               // mystartactivity(MyphotoActivity3.class);
                                startActivity(intent);




                            } else {
                                if (task.getException() != null) {//6글자 이하거나
                                    // If sign in fails, display a message to the user.
                                    startToast("이메일 또는 비밀번호를 잘못 입력하셨습니다");
                                }
                            }
                        }
                    });
        } else{
            startToast("이메일 또는 비밀번호를 입력하세요");
        }
    }
    private void startToast(String mes){
        Toast.makeText(this, mes,Toast.LENGTH_SHORT).show();

    }

    private void mystartactivity(Class c) {

        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 뒤로가기 꺼지기
        intent.putExtra("id", email);
        startActivity(intent);


    }
}
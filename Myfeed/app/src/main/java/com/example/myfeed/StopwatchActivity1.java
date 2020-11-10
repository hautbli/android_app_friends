package com.example.myfeed;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StopwatchActivity1 extends AppCompatActivity {

    private Button mStartBtn, mStopBtn;
    private TextView mTimeTextView;
    private timeThread timeThread;
    private Boolean isRunning = true;
    public  static String currenttime_start;
    String MY_DATA = "MY_DATA";
    String MY_RECORD = "MY_RECORD";
    String DESTROY_DATA = "DESTROY_DATA";
    private ArrayList<StopwatchData> slist;
    private StopwatchAdapter stopwathch_adapter;
    private UserData userData;

    private  boolean just_start; // 서비스 한 번 돌고 왔다가 다시 시작할 때 계속 서비스 시작 시간 값이 와서 이걸 만듦..

    int finaltime;
    //서비스
    private Intent serviceIntent;

    int msgTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        ActionBar ab = getSupportActionBar();
        ab.hide();


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#4ea1d3"));
        }

        mStartBtn = (Button) findViewById(R.id.btn_start);
        mStopBtn = (Button) findViewById(R.id.btn_stop);
        mTimeTextView = (TextView) findViewById(R.id.timeView); //위에 경과 시간

        // 리얼서비스 인텐트 값 = 널이면 리얼서비스 클래스로 고고
        if (RealService.serviceIntent == null) {
//            serviceIntent = new Intent(getApplicationContext(), RealService.class);
//            startService(serviceIntent);

        } else {
            // 널 아니면 서비스 종료하고 쉐어드랑 서비스 커렌트값 더한 거에서 카운트 시작  !!
           mStartBtn.setVisibility(View.GONE);
            mStopBtn.setVisibility(View.VISIBLE);

            serviceIntent = RealService.serviceIntent;//getInstance().getApplication();
//            Toast.makeText(getApplicationContext(), "서비스 종료하고 셰어드랑 커렌트 값 더함", Toast.LENGTH_LONG).show();

            //서비스 종료..
            RealService.finalstop = true;

            stopService(serviceIntent);

            /////
            SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
            int time_prefs = prefs.getInt(DESTROY_DATA,0);

            Log.e("액티비티 재시작!!!","service_runtime: "+ RealService.service_runtime);
            Log.e("액티비티 재시작!!!","time_prefs: "+ time_prefs);
            finaltime =  time_prefs + RealService.service_runtime;
            if (timeThread != null) timeThread.interrupt();
            timeThread = new timeThread();
            timeThread.start();

        }





        //시작버튼을 누르면
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                mStopBtn.setVisibility(View.VISIBLE);

                //시작 시간
                Calendar currentalCal_start = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("HH시 mm분 ss");
                currenttime_start = dateFormat.format(currentalCal_start.getTime());
                //

                just_start = true ;

                if (timeThread != null) timeThread.interrupt();
                timeThread = new timeThread();
                timeThread.start();

                // 시작했을 때 인텐트에 값 준다
                serviceIntent = new Intent(getApplicationContext(), RealService.class);
               // startService(serviceIntent);

            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //시작시간이랑 종료 시간
                Calendar currenta_fin = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("HH시 mm분 ss");
           //     currenta_fin.add(Calendar.MILLISECOND, -1090); // 0.2초 오차 생겨서 보정함..
                String currenttime_fin = dateFormat.format(currenta_fin.getTime());

                // 산책 시간
                String stroll_time = mTimeTextView.getText().toString();

                //날짜
                Date currentTime = Calendar.getInstance().getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);

                v.setVisibility(View.GONE);
                mStartBtn.setVisibility(View.VISIBLE);
                //    mRecordTextView.setText("");
                timeThread.interrupt();

                if(!just_start){ // 서비스를 다녀왔으면 서비스에서 돌다 온 시작 시간을 받고
                    currenttime_start = RealService.currenttime_start;
                    Log.e("액티비티 재시작!!!","service_runtime: "+ RealService.service_runtime);

                }                                          // 서비스를 안다녀왔으면 커렌트 타임이 널이다 이건 온크레에이트 메소드에 있음
                StopwatchData stopwatchData = new StopwatchData(currenttime_fin, currenttime_start, stroll_time, date_text);

                //     slist = new ArrayList<>(); 로드 데이타 없을 때 이거해야함

                slist.add(0, stopwatchData);//첫번째 줄에 삽입됨



                buildRecyclerView();

                saveData();

                stopwathch_adapter.notifyDataSetChanged();

                finaltime = 0;
                serviceIntent = null;   //시작을 누르고 종료하고 액티비티 디스트로이 되면   serviceintent값이 이미 있어서 서비스가 알아서 시작됐는데 이걸로 해결
            }
        });
    }

    @Override
    protected void onStop() {

//        Log.e("onDestroy","serviceIntent != null");
//        showToast(getApplication(), "onStop");


        super.onStop();
        finish();
    }

    //[서비스] 앱이 종료됐을 때...
    @Override
    protected void onDestroy() {
//        Log.e("onDestroy","serviceIntent != null");
        //showToast(getApplication(), "onDestroy");

        if (serviceIntent != null) { // 시작 버튼을 누른채로 앱을 종료한다면


            Log.e("onDestroy","serviceIntent != null");

            // 경과 시간을 셰어드에 저장한다.
            SharedPreferences prefs = getSharedPreferences(MY_DATA, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(DESTROY_DATA, msgTime);
            editor.commit();

            //// 서비스 !!
            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
            boolean isWhiteListing = false;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                isWhiteListing = pm.isIgnoringBatteryOptimizations(getApplicationContext().getPackageName());
            }
            if (!isWhiteListing) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivity(intent);
            }
            Log.e("onDestroy","onDestroy");

//            serviceIntent = new Intent(getApplicationContext(), RealService.class);
            startService(serviceIntent);
            Log.e("onDestroy_stRT","onDestroy");
        }
        super.onDestroy();
    }

    @SuppressLint("HandlerLeak")
  public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // int mSec = msg.arg1 % 100;
            msgTime = msg.arg1/100;
            int sec = (msg.arg1/100) % 60;
            int min = (msg.arg1/100) / 60;
            int hour = (msg.arg1/100) / 3600; //msg.arg1은 1부터 10,000초까지 계속 숫자를 세고 단위는 내가 나눠준당
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d", hour, min, sec); //mSec있었는데 지움

            mTimeTextView.setText(result);
        }
    };


    public void showToast(final Application application, final String msg) {
        Handler h = new Handler(application.getMainLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(application, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public class timeThread extends Thread {

        @Override
        public void run() {

            int i = finaltime;

            Log.e("dd","final"+i);
            while (true) {//1초마다 증가
                //  while (isRunning) { //일시정지를 누르면 멈춤
                Message msg = new Message();
                msg.arg1 = (i)*100;

                handler.sendMessage(msg); // message객체의 arg1 필드에 i 의 값을 1을 증가시키고
                // sendmessage() 메소드를 이용해 message객체를 전달한다. handler가 textvieew 값을 변화시킴
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTimeTextView.setText("00:00:00"); //쓰레드 중지했을 때 SET!!
                        }
                    });
                    return; // 인터럽트 받을 경우 return
                }
            }
        }
    }

    private void saveData() {
        SharedPreferences prefs = getSharedPreferences(MyphotoActivity3.uid, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        userData.setStopwatchData(slist);
        String json = gson.toJson(userData);
        editor.putString(MyphotoActivity3.uid, json);
        editor.commit();
    }

    @Override
    protected void onResume() {
        loadData();
//        stopwathch_adapter.notifyDataSetChanged();
        buildRecyclerView();

        super.onResume();
    }

    public void loadData() { // 저장한 거 불러오기!!!
        SharedPreferences prefs = getSharedPreferences(MyphotoActivity3.uid, MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString(MyphotoActivity3.uid, null); // 키 값 , 기본값
        userData = gson.fromJson(json, UserData.class);

        if (userData==null){
            userData = new UserData();
        }

        Type type = new TypeToken<ArrayList<StopwatchData>>() {
        }.getType();

        if (userData.getStopwatchData()==null){
            slist = new ArrayList<>();
        }
        slist = userData.getStopwatchData();

        if (slist == null) {
            slist = new ArrayList<>();
        }

//        //  json 패싱.. !!!!!! 저장된걸 가져와가져와..
//        String json_list = gson.toJson(slist);
//
//        StringBuffer sb1 = new StringBuffer();//날짜
//        StringBuffer sb2 = new StringBuffer();//시작시간
//        StringBuffer sb3 = new StringBuffer();//끝난시간
//        StringBuffer sb4 = new StringBuffer();//산책 시간
//
//        String str = json_list; //  이미지와 글이 채워진거..!!! 입력한거 없으면 null이지 뭐
//
//        try {
//            JSONArray jarray = new JSONArray(str);
//            for (int i = 0; i < jarray.length(); i++) {
//
//                JSONObject jObgect = jarray.getJSONObject(i);
//
//                String date_text = jObgect.getString("date_text"); // 이렇게 써있는거 옆에 값을 가져와랑
//                String currenttime_start = jObgect.getString("currenttime_start");
//                String currenttime_fin = jObgect.getString("currenttime_fin"); //
//                String stroll_time = jObgect.getString("stroll_time"); //
//
//                sb1.append(
//                        date_text
//                );
//                sb2.append(
//                        currenttime_start
//                );
//                sb3.append(
//                        currenttime_fin
//                );
//                sb4.append(
//                        stroll_time
//                );
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    private void buildRecyclerView() {
        // 빌드 리사이클러뷰
        RecyclerView stopwatch_RecyclerView = findViewById(R.id.stopwatch_recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        stopwathch_adapter = new StopwatchAdapter(this, slist, getSharedPreferences(MyphotoActivity3.uid, MODE_PRIVATE),userData); // 어댑터 받고!
        stopwatch_RecyclerView.setLayoutManager(mLinearLayoutManager);
        stopwatch_RecyclerView.setAdapter(stopwathch_adapter);
    }

}

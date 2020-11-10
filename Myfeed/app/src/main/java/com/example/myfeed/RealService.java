package com.example.myfeed;

import android.app.AlarmManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RealService extends Service { // 리얼 서비스 인텐트 값이 널이면 들어온다!!
    private Thread mainThread;
    public static Intent serviceIntent = null;
    public static boolean finalstop ;
    int start_sv;
    public static int service_runtime;
    static String currenttime_start ;

    public RealService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceIntent = intent;
        currenttime_start = StopwatchActivity1.currenttime_start;


        showToast(getApplication(), "스탑워치 실행중");

        long time_current_sv = System.currentTimeMillis();
        start_sv = (int) (time_current_sv/1000); // 서비스 시작

        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {



                SimpleDateFormat sdf = new SimpleDateFormat("aa hh:mm");
                boolean run = true;
                while (run) {
                    try {
                        Thread.sleep(1000); // 1 minute
                        showToast(getApplication(), "스탑워치 실행중");
                        Date date = new Date();
                        //showToast(getApplication(), sdf.format(date));
//                        sendNotification(sdf.format(date));

                        long time_current_sv_fn = System.currentTimeMillis();
                        int final_sv = (int) (time_current_sv_fn/1000); // 서비스 종료

                        service_runtime = final_sv - start_sv + 1;

                        Log.e("서비스 종료 런타임! ","service_runtime: "+ service_runtime);

                        Log.e("서비스 종료 런타임! ","final_sv: "+ final_sv);
                        Log.e("서비스 종료 런타임! ","start_sv: "+ start_sv);
                        //액티비티가 시작되면 서비스 종료



                    } catch (InterruptedException e) {
                        run = false;
                        e.printStackTrace();
                    }
                }
            }
        });
        mainThread.start();

        return START_NOT_STICKY;
    }

    @Override
    public  void onDestroy() {
        super.onDestroy();
        serviceIntent = null;
        if(!finalstop) { // 막 종료 됐을 때 알람줘서 계속 감
            setAlarmTimer();
        }

        Thread.currentThread().interrupt(); // finalstop true일 때.. 서비스 종료 !!!
        if (mainThread != null) {
            mainThread.interrupt();
            mainThread = null;

        }



    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void showToast(final Application application, final String msg) {
        Handler h = new Handler(application.getMainLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(application, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void setAlarmTimer() {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 1);
        Intent intent = new Intent(this, AlarmRecever.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0,intent,0);

        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, StopwatchActivity1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "fcm_default_channel";//getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)//drawable.splash)
                        .setContentTitle("Service test")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,"Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

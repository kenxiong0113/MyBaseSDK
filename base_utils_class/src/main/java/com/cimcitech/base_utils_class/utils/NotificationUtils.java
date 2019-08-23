package com.cimcitech.base_utils_class.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.cimcitech.base_utils_class.R;
import com.cimcitech.base_utils_class.base.BaseLibrary;

import static android.support.v4.app.NotificationCompat.VISIBILITY_SECRET;
import static android.support.v4.app.NotificationManagerCompat.IMPORTANCE_HIGH;

/**
 * 通知栏封装类
 *
 * @author by ken on 2018.12.14
 */
public class NotificationUtils extends ContextWrapper {
    private static final String TAG = "NotificationUtils";
    public static final String CHANNEL_ID = "com.cimcitech.pushId";
    private static final String CHANNEL_NAME = "com.cimcitech.pushName";
    private static final String CHANNEL_DESCRIPTION = "this is default channel!";
    private NotificationManager mManager;
    boolean isSound;
    boolean isShock;

    /**
     * 是否开启声音/震动
     */
    public NotificationUtils(Context context, boolean sound, boolean shock) {
        super(context);
        this.isSound = sound;
        this.isShock = shock;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(sound, shock);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(boolean sound, boolean shock) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.canBypassDnd();//是否绕过请勿打扰模式
        channel.enableLights(true);//闪光灯
        channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
        channel.setLightColor(Color.RED);//闪关灯的灯光颜色
        channel.canShowBadge();//桌面launcher的消息角标
        channel.enableVibration(true);//是否允许震动
        channel.getAudioAttributes();//获取系统通知响铃声音的配置
        channel.getGroup();//获取通知取到组
        channel.setBypassDnd(true);//设置可绕过  请勿打扰模式
        channel.setVibrationPattern(new long[]{200, 300, 400});//设置震动模式
        channel.shouldShowLights();//是否会有灯光
        channel.setSound(channel.getSound(), channel.getAudioAttributes());
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }


    /**
     * 设备系统版本大于8.0
     * 设置推送通知栏
     */
    public NotificationCompat.Builder getNotificationCompat(String title, String content, Class cls) {
        Log.e(TAG, "getNotificationCompat: 8.0推送通知");
        Intent intent = new Intent(this, cls);
        PendingIntent intentPend = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setPriority(IMPORTANCE_HIGH);
        builder.setContentTitle(title);
        builder.setShowWhen(true);
        builder.setContentText(content);
        builder.setSmallIcon(BaseLibrary.mIcon);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), BaseLibrary.mIcon));
        //点击自动删除通知
        builder.setAutoCancel(true);

        builder.setContentIntent(intentPend);
        return builder;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("WrongConstant")
    public Notification.Builder getNotification(String title, String content, Class cls) {
        Intent intent = new Intent(this, cls);
        PendingIntent intentPend = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(this)
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setShowWhen(true)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(BaseLibrary.mIcon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), BaseLibrary.mIcon))
                //点击自动删除通知
                .setAutoCancel(true)
                .setContentIntent(intentPend);


        return builder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void sendNotification(int notifyId, String title, String content, Class cls) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder = getNotificationCompat(title, content, cls);
            getManager().notify(notifyId, builder.build());
        } else {
//            AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//            final int ringerMode = am.getRingerMode();
            Log.e(TAG, "sendNotification: ==========>" + Build.VERSION.SDK_INT);
            Notification.Builder builder = getNotification(title, content, cls);
            Notification notification = builder.build();
            if (isSound) {
                notification.defaults = Notification.DEFAULT_SOUND;
            }

            if (isShock) {
                notification.defaults |= Notification.DEFAULT_VIBRATE;// 震动
                long v1[] = {0, 100, 200, 300}; // 震动频率
                notification.vibrate = v1;
            }
            Log.e(TAG, "sendNotification: " + notifyId);
            getManager().notify(0, notification);
        }

    }


}
package com.tastegood.distribute.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.tastegood.distribute.receiver.NotificationEntity;


/**
 * Created by surandy on 2016/11/14.
 */

public class NotificationUtil {

    protected static long lastNotifiyTime;

    /**
     * 手机震动和声音提示
     */
    public static void viberateAndPlayTone(Context appContext) {

        if (System.currentTimeMillis() - lastNotifiyTime < 1000) {
            // received new messages within 2 seconds, skip play ringtone
            return;
        }
        try {
            lastNotifiyTime = System.currentTimeMillis();
            AudioManager audioManager = (AudioManager) appContext
                    .getSystemService(Context.AUDIO_SERVICE);
            Vibrator vibrator = (Vibrator) appContext
                    .getSystemService(Context.VIBRATOR_SERVICE);
            // 判断是否处于静音模式
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                return;
            }
            long[] pattern = new long[]{0, 180, 80, 120};
            vibrator.vibrate(pattern, -1);

            Uri notificationUri = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            final Ringtone ringtone = RingtoneManager.getRingtone(appContext,
                    notificationUri);
            if (ringtone == null) {
                return;
            }

            if (!ringtone.isPlaying()) {
                String vendor = Build.MANUFACTURER;
                ringtone.play();
                // for samsung S3, we meet a bug that the phone will
                // continue ringtone without stop
                // so add below special handler to stop it after 3s if
                // needed
                if (vendor != null && vendor.toLowerCase().contains("samsung")) {
                    Thread ctlThread = new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(3000);
                                if (ringtone.isPlaying()) {
                                    ringtone.stop();
                                }
                            } catch (Exception e) {
                            }
                        }
                    };
                    ctlThread.run();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 发送通知
     *
     * @param entity     通知体
     * @param appContext
     * @param
     */
    public static void sendNotification(NotificationEntity entity, Context appContext) {
        if (entity == null || appContext == null)
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                appContext);
        int iconId = entity.getSmallIconId();
        if (iconId == 0) {
            ApplicationInfo info = appContext.getApplicationInfo();
            if (info == null) return;
            iconId = info.icon;
        }
        if (!TextUtils.isEmpty(entity.getTicker())) {
            mBuilder.setTicker(entity.getTicker());
        }
        mBuilder.setSmallIcon(iconId);
        mBuilder.setWhen(System.currentTimeMillis());
        Bitmap bigIcon = entity.getBigIcon();
        if (bigIcon != null) {
            mBuilder.setLargeIcon(bigIcon);
        }
        if (!TextUtils.isEmpty(entity.getContentTitle())) {
            mBuilder.setContentTitle(entity.getContentTitle());
        }
        if (!TextUtils.isEmpty(entity.getContentText())) {
            mBuilder.setContentText(entity.getContentText());
        }
        if (entity.getContentIntent() != null) {
            mBuilder.setContentIntent(entity.getContentIntent()).setAutoCancel(true);
        }
        if (entity.getDeleteIntent() != null) {
            mBuilder.setDeleteIntent(entity.getDeleteIntent());
        }
        NotificationManager mNotificationManager = (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(entity.getNotifyId());
        Notification notification = mBuilder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        mNotificationManager.notify(entity.getNotifyId(), notification);
    }

}

package com.tastegood.distribute.receiver;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tastegood.distribute.R;
import com.tastegood.distribute.order.domain.model.JpushMessageNewOrder;
import com.tastegood.distribute.util.NotificationUtil;

/**
 * Created by surandy on 2016/11/14.
 */

public class JpushMessageOperater {

    /***
     * 传送消息类型数据的key值
     */
    public static final String MESSAGE_TYPE = "messageType";
    public static final String MESSAGE_DATE = "messagedate";
    /***
     * 1.新订单消息
     */
    public static final String CONTENT_TYPE_NEWORDER = "10";

    private static int notifyId;

    /***
     * 处理极光推送的消息
     *
     * @param extra
     * @param contentType
     */
    public static void operaterPushMessage(Context context, String extra, String contentType) {
        if (TextUtils.isEmpty(extra) || TextUtils.isEmpty(contentType) || context == null) {
            return;
        }
        Gson gson = new Gson();
        // 新订单消息
        if (CONTENT_TYPE_NEWORDER.equals(contentType)) {
            notifyId++;

            JpushMessageNewOrder message = gson.fromJson(extra, JpushMessageNewOrder.class);
            Intent intent = new Intent(context, NotificationBroadcast.class);
            intent.setAction(NotificationBroadcast.JPUSH_ACTION);
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE_TYPE, contentType);
            bundle.putString(MESSAGE_DATE, extra);
            intent.putExtras(bundle);
            PendingIntent contentIntent = PendingIntent.getBroadcast(context, notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            sendNotification(context, message.getTitle(), contentIntent, null, notifyId);
        }
    }

    /***
     * 创建并发送通知
     *
     * @param contentText
     * @param contentIntent
     * @param notifyId
     */
    private static void sendNotification(Context context, String contentText, PendingIntent contentIntent, PendingIntent deleteIntent, int notifyId) {
        int smallIconId = context.getApplicationInfo().icon;
        String contentTitle = context.getResources().getString(R.string.app_name);
        NotificationEntity entity = new NotificationEntity(smallIconId,
                contentTitle, contentText, contentIntent, contentText, notifyId);
        if (deleteIntent != null) {
            entity.setDeleteIntent(deleteIntent);
        }
        NotificationUtil.sendNotification(entity, context);

    }

}

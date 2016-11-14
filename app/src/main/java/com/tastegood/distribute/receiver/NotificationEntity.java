package com.tastegood.distribute.receiver;

import android.app.PendingIntent;
import android.graphics.Bitmap;

/***
 * 显示的通知消息体
 *
 */
public class NotificationEntity {
    
    private int smallIconId;
    private String contentTitle;
    private CharSequence contentText;
    private PendingIntent contentIntent;
    private CharSequence ticker;
    private int notifyId;
    private Bitmap bigIcon;
    private PendingIntent deleteIntent;
    
    public NotificationEntity(int smallIconId, String contentTitle,
                              CharSequence contentText, PendingIntent contentIntent, CharSequence ticker,
                              int notifyId) {
        this.smallIconId = smallIconId;
        this.contentTitle = contentTitle;
        this.contentText = contentText;
        this.contentIntent = contentIntent;
        this.ticker = ticker;
        this.notifyId = notifyId;
    }
    
    
    public NotificationEntity(int smallIconId, String contentTitle,
                              String contentText, int notifyId) {
        super();
        this.smallIconId = smallIconId;
        this.contentTitle = contentTitle;
        this.contentText = contentText;
        this.notifyId = notifyId;
    }


    public int getSmallIconId() {
        return smallIconId;
    }
    public void setSmallIconId(int smallIconId) {
        this.smallIconId = smallIconId;
    }
    public String getContentTitle() {
        return contentTitle;
    }
    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }
    public CharSequence getContentText() {
        return contentText;
    }
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
    public PendingIntent getContentIntent() {
        return contentIntent;
    }
    public void setContentIntent(PendingIntent contentIntent) {
        this.contentIntent = contentIntent;
    }
    public CharSequence getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public int getNotifyId() {
        return notifyId;
    }
    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }


    public Bitmap getBigIcon() {
        return bigIcon;
    }


    public void setBigIcon(Bitmap bigIcon) {
        this.bigIcon = bigIcon;
    }


    public PendingIntent getDeleteIntent() {
        return deleteIntent;
    }


    public void setDeleteIntent(PendingIntent deleteIntent) {
        this.deleteIntent = deleteIntent;
    }
    
    

}

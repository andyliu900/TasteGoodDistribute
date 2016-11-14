package com.tastegood.distribute.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by surandy on 16/4/27.
 */
public class ViewCompactUtils {

    /**
     * 设置按钮是否可用
     */
    public static void setButtonClickable(Context context, Button button, boolean flag, int useFulBgRes) {
        if (flag) {
            button.setEnabled(true);
            ViewCompactUtils.setViewAlphaBG(button, 255);
            if (useFulBgRes > 0) {
                ViewCompactUtils.setViewBG(context, button, useFulBgRes);
            }
        } else {
            button.setEnabled(false);
            ViewCompactUtils.setViewAlphaBG(button, 128);
        }
    }

    /**
     * 设置控件透明度
     * @param view
     * @param alpha
     */
    public static void setViewAlphaBG(View view, int alpha) {
        view.getBackground().setAlpha(alpha);
    }

    /**
     * 设置控件背景
     * @param view
     * @param bgRes
     */
    public static void setViewBG(Context context, View view, int bgRes) {
        Resources res = context.getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, bgRes, null);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    /**
     * 设置TextView字体颜色
     * @param context
     * @param textView
     */
    public static void setTextViewColor(Context context, TextView textView, int colorRes){
        int color = ContextCompat.getColor(context, colorRes);
        textView.setTextColor(color);
    }

    /**
     * 获取颜色
     * @param context
     * @param id
     * @return
     */
    public static int getColor(Context context, int id) {
        int color = ContextCompat.getColor(context, id);
        return color;
    }

}

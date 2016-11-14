package com.tastegood.distribute.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tastegood.distribute.R;
import com.tastegood.distribute.view.GlideCircleTransform;

/**
 *
 * Created by surandy on 2016/11/2.
 */

public class GlideImageManager {

    public static GlideImageManager glideManager = null;

    public static GlideImageManager getInstance() {
        if (glideManager == null) {
            glideManager = new GlideImageManager();
        }
        return glideManager;
    }

    public DrawableRequestBuilder getCommUserGlide(Object contetType, String url) {
        if (contetType instanceof Context) {
            return Glide.with((Context)contetType)
                    .load(url)
                    .error(R.mipmap.img_defult_avatar)
                    .placeholder(R.mipmap.img_defult_avatar);
        } else if (contetType instanceof Activity) {
            return Glide.with((Activity)contetType)
                    .load(url)
                    .error(R.mipmap.img_defult_avatar)
                    .placeholder(R.mipmap.img_defult_avatar);
        } else if (contetType instanceof FragmentActivity) {
            return Glide.with((FragmentActivity)contetType)
                    .load(url)
                    .error(R.mipmap.img_defult_avatar)
                    .placeholder(R.mipmap.img_defult_avatar);
        } else if (contetType instanceof Fragment) {
            return Glide.with((Fragment)contetType)
                    .load(url)
                    .error(R.mipmap.img_defult_avatar)
                    .placeholder(R.mipmap.img_defult_avatar);
        } else {
            return Glide.with((Context)contetType)
                    .load(url)
                    .error(R.mipmap.img_defult_avatar)
                    .placeholder(R.mipmap.img_defult_avatar);
        }

    }

    private RequestListener<String, GlideBitmapDrawable> mRequestListener = new RequestListener<String, GlideBitmapDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideBitmapDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideBitmapDrawable resource, String model, Target<GlideBitmapDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

    public void loaderUserImage(Object conn,Context context, String imgurl, ImageView imgView, boolean isRound) {
        imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (TextUtils.isEmpty(imgurl)) {
            imgView.setImageResource(R.mipmap.img_defult_avatar);
        } else {
            if (isRound) {
                getCommUserGlide(conn, imgurl)
                        .transform(new GlideCircleTransform(context))
                        .crossFade()
                        .listener(mRequestListener)
                        .into(imgView);
            } else {
                getCommUserGlide(conn, imgurl)
                        .crossFade()
                        .listener(mRequestListener)
                        .into(imgView);
            }
        }
    }

}

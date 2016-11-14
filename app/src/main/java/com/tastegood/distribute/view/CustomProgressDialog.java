package com.tastegood.distribute.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tastegood.distribute.R;

/**
 * Created by surandy on 2016/10/18.
 */

public class CustomProgressDialog extends Dialog {

    private ProgressBar mProgress;
    private TextView mMessageView;

    private CharSequence mMessage;

    public CustomProgressDialog(Context context) {
        this(context, R.style.show_Progress_Dialog);
    }

    private CustomProgressDialog(Context context, int theme) {
        super(context, R.style.show_Progress_Dialog);
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(false);
    }

    public static CustomProgressDialog show(Context context) {
        return show(context, null, null);
    }

    public static CustomProgressDialog show(Context context, CharSequence msg,
                                            OnCancelListener cancelListener) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context mContext = getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_floatlayer);
        View view = inflater.inflate(R.layout.custom_progress_dialog, null,
                false);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        mMessageView = (TextView) view.findViewById(R.id.p_message);
        getWindow().setContentView(view);
        if (mMessage != null) {
            setMessage(mMessage);
        }
        super.onCreate(savedInstanceState);
    }

    public void setMessage(CharSequence message) {
        if (mProgress != null) {
            if (mMessageView.getVisibility() == View.GONE) {
                mMessageView.setVisibility(View.VISIBLE);
            }
            mMessageView.setText(message);
        } else {
            mMessage = message;
        }
    }

    public void resetMessage(CharSequence message) {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
            if (mMessageView.getVisibility() == View.GONE) {
                mMessageView.setVisibility(View.VISIBLE);
            }
            mMessageView.setText(message);
        } else {
            mMessage = message;
        }
    }

    public void setMessageDrawable(Drawable drawable) {
        if (mProgress != null) {
            mProgress.setVisibility(View.GONE);
            mMessageView.setCompoundDrawablePadding(12);
            mMessageView.setCompoundDrawablesWithIntrinsicBounds(null,
                    drawable, null, null);
        }
    }

}

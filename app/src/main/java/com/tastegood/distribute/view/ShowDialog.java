package com.tastegood.distribute.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tastegood.distribute.R;
import com.tastegood.distribute.util.ViewCompactUtils;


/**
 * 自定义菜单对话框
 *
 * @author Administrator
 */
public class ShowDialog extends Dialog {

    private DialogListener listener;
    private TextView title;
    private TextView content;
    private TextView pro_t;
    private TextView line;
    private TextView sure_t;

    public ShowDialog(Context context, DialogListener listener) {
        super(context, R.style.AlertDialogStyle);
        this.listener = listener;
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_showdialog, null, false);
        title = (TextView) view.findViewById(R.id.title);
        content = (TextView) view.findViewById(R.id.content);
        line = (TextView) view.findViewById(R.id.line);
        pro_t = (TextView) view.findViewById(R.id.pro_t);
        sure_t = (TextView) view.findViewById(R.id.sure_t);
        pro_t.setOnClickListener(onclick);
        sure_t.setOnClickListener(onclick);
        setContentView(view);
        setCancelable(false);
    }

    View.OnClickListener onclick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.sure_t) {
                if (listener != null) {
                    listener.SureListener();
                }
            } else if (id == R.id.pro_t) {
                if (listener != null) {
                    listener.ProListener();
                }
            }
            dismiss();
        }

    };

    public void show(String titles, String conStrings, String[] items) {
        try {
            if (TextUtils.isEmpty(titles)) {
                title.setVisibility(View.GONE);
            } else {
                title.setText(titles);
            }
            content.setText(conStrings);
            content.setGravity(Gravity.CENTER);
            if (items.length > 1) {
                pro_t.setText(items[0]);
                sure_t.setText(items[1]);
                pro_t.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
            } else {
                pro_t.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                ViewCompactUtils.setViewBG(getContext(), sure_t, R.drawable.dialog_bottomitem_bg_selector);
                sure_t.setText(items[0]);
            }
            if (!isShowing()) {
                show();
            }
        } catch (Exception e) {

        }

    }

    public void show(String titles, String conStrings, String[] items, boolean isleft) {
        try {
            if (TextUtils.isEmpty(titles)) {
                title.setVisibility(View.GONE);
            } else {
                title.setText(titles);
            }
            content.setText(conStrings);
            if (isleft) {
                content.setGravity(Gravity.LEFT);
            } else {
                content.setGravity(Gravity.CENTER);
            }
            if (items.length > 1) {
                pro_t.setText(items[0]);
                sure_t.setText(items[1]);
                pro_t.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
            } else {
                pro_t.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                ViewCompactUtils.setViewBG(getContext(), sure_t, R.drawable.dialog_bottomitem_bg_selector);
                sure_t.setText(items[0]);
            }
            if (!isShowing()) {
                show();
            }
        } catch (Exception e) {

        }

    }

    public interface DialogListener {

        public void ProListener();

        public void SureListener();
    }
}

package com.tastegood.distribute.gobal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tastegood.distribute.R;

/**
 * 带Toolbar的Activity基类
 *
 * Created by surandy on 2016/10/12.
 */

public abstract class BaseToolbarActivity extends BaseActivity implements ToolbarActionListener {

    protected Toolbar toolbar;
    protected TextView toolbarTitle;
    protected ImageButton rightImg;
    protected TextView rightText;
    private onNavigationOnClickListener navigationListener;
    private onRightImgOnClickListener rightImgListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (findViewById(R.id.toolbar) != null) {
            toolbar = (Toolbar)findViewById(R.id.toolbar);
            toolbar.setTitle("");
            toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
            rightImg = (ImageButton)findViewById(R.id.rightImg);
            rightText = (TextView)findViewById(R.id.rightText);
            setSupportActionBar(toolbar);
        }

        initViews();
        initDatas();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void initToolbar(String titleStr) {
        initToolbar(titleStr, 0, null, 0, null);
    }

    @Override
    public void initToolbar(String titleStr, int leftIconRes, onNavigationOnClickListener navigationOnClickListener) {
        this.navigationListener = navigationOnClickListener;
        initToolbar(titleStr, leftIconRes, navigationListener, 0, null);
    }

    @Override
    public void initToolbar(String titleStr, int leftIconRes, onNavigationOnClickListener navigationOnClickListener, int rightIconRes, onRightImgOnClickListener rightImgOnClickListener) {
        if (toolbarTitle != null) {
            toolbarTitle.setText(titleStr);
        }
        if (leftIconRes != 0 && toolbar != null) {
            toolbar.setNavigationIcon(leftIconRes);
            if (navigationOnClickListener != null) {
                this.navigationListener = navigationOnClickListener;
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (navigationListener != null) {
                            navigationListener.setOnNavigationOnClickListener();
                        }
                    }
                });
            }
        }
        if (rightIconRes != 0 && toolbar != null) {
            rightImg.setImageResource(rightIconRes);
            if (rightImgOnClickListener != null) {
                this.rightImgListener = rightImgOnClickListener;
                rightImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rightImgListener != null) {
                            rightImgListener.setOnRightImgOnClickListener();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void initToolbar(String titleStr, int leftIconRes, onNavigationOnClickListener navigationOnClickListener, String rightStr, onRightImgOnClickListener rightImgOnClickListener) {
        if (toolbarTitle != null) {
            toolbarTitle.setText(titleStr);
        }
        if (leftIconRes != 0 && toolbar != null) {
            toolbar.setNavigationIcon(leftIconRes);
            if (navigationOnClickListener != null) {
                this.navigationListener = navigationOnClickListener;
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (navigationListener != null) {
                            navigationListener.setOnNavigationOnClickListener();
                        }
                    }
                });
            }
        }
        if (rightStr != null && toolbar != null) {
            rightText.setText(rightStr);
            if (rightImgOnClickListener != null) {
                this.rightImgListener = rightImgOnClickListener;
                rightText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rightImgListener != null) {
                            rightImgListener.setOnRightImgOnClickListener();
                        }
                    }
                });
            }
        }
    }

    @Override
    public TextView getToolbarTitle() {
        if (toolbarTitle == null) {
            return null;
        }
        return toolbarTitle;
    }

    @Override
    public void setToolbatTitleText(String title) {
        if (toolbarTitle == null) {
            return;
        }
        toolbarTitle.setText(title);
    }

    @Override
    public void setToolbarRightTextVisable(int visableType) {
        if (rightText == null) {
            return;
        }
        rightText.setVisibility(visableType);
    }

    @Override
    public void setToolbarRightTextContent(String rightContent) {
        if (rightText == null) {
            return;
        }
        rightText.setText(rightContent);
    }

}

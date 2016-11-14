package com.tastegood.distribute.gobal;

import android.widget.TextView;

/**
 * Toolbar的操作事件接口
 * 
 * Created by surandy on 2016/10/12.
 */

public interface ToolbarActionListener {
    
    public void initToolbar(String titleStr);

    public void initToolbar(String titleStr, int leftIconRes, onNavigationOnClickListener navigationOnClickListener);

    public void initToolbar(String titleStr, int leftIconRes, onNavigationOnClickListener navigationOnClickListener,
                            int rightIconRes, onRightImgOnClickListener rightImgOnClickListener);

    public void initToolbar(String titleStr, int leftIconRes, onNavigationOnClickListener navigationOnClickListener,
                            String rightStr, onRightImgOnClickListener rightImgOnClickListener);

    public TextView getToolbarTitle();

    public void setToolbatTitleText(String title);

    public void setToolbarRightTextVisable(int visableType);

    public void setToolbarRightTextContent(String rightContent);

    public interface onNavigationOnClickListener {
        public void setOnNavigationOnClickListener();
    }

    public interface onRightImgOnClickListener {
        public void setOnRightImgOnClickListener();
    }
    
}

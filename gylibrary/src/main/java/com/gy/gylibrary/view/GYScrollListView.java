package com.gy.gylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2018/11/28
 * @Describe
 */
public class GYScrollListView extends ListView {
    public GYScrollListView(Context context) {
        super(context);
    }

    public GYScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量的大小由一个32位的数字表示，前两位表示测量模式，后30位表示大小，这里需要右移两位才能拿到测量的大小
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}



package com.gy.gylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决ScrollView嵌套ListView，ListView显示不全的问题
 * 注意：ScrollView嵌套ListView时，如果ListView很长超出了屏幕的高度，
 * 那么ScrollView会自动滚动到ListView的顶部，
 * 但是我们需要默认在整体页面顶部，所以在初始化的时候就让ScrollView获得焦点，滚动条自然就显示到顶部了。
 * scrollView.setFocusable(true);
 * scrollView.setFocusableInTouchMode(true);
 * scrollView.requestFocus();
 *
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/6/29
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



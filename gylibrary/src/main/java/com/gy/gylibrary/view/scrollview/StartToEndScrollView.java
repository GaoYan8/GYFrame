package com.gy.gylibrary.view.scrollview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 *  监听ScrollView滚动到顶部或者底部 触发事件
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/8/16
 * @Describe
 */
public class StartToEndScrollView extends ScrollView {
    //回调监听接口
    private OnScrollChangeListener mOnScrollChangeListener;
    //标识是否滑动到顶部
    private boolean isScrollToStart = false;
    //标识是否滑动到底部
    private boolean isScrollToEnd = false;
    private static final int CODE_TO_START = 0x001;
    private static final int CODE_TO_END = 0x002;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_TO_START:
                    mOnScrollChangeListener.onScrollToStart();
                    //重置标志“滑动到顶部”时的标志位
                    isScrollToStart = false;
                    break;
                case CODE_TO_END:
                    mOnScrollChangeListener.onScrollToEnd();
                    //重置标志“滑动到底部”时的标志位
                    isScrollToEnd = false;
                    break;
                default:
                    break;
            }
        }
    };
    public StartToEndScrollView(Context context) {
        super(context);
    }
    public StartToEndScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StartToEndScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScroll();
            //滚动到顶部，ScrollView存在回弹效果效应（这里只会调用两次，如果用<=0,会多次触发）
            if (getScrollY() == 0) {
                //过滤操作，优化为一次调用
                if (!isScrollToStart) {
                    isScrollToStart = true;
                    mHandler.sendEmptyMessageDelayed(CODE_TO_START, 300);

                }
            } else {
                View contentView = getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() == (getScrollY() + getHeight())) {
                    //滚动到底部，ScrollView存在回弹效果效应
                    //优化，只过滤第一次
                    if (!isScrollToEnd) {
                        isScrollToEnd = true;
                        mHandler.sendEmptyMessageDelayed(CODE_TO_END, 300);

                    }
                }
            }
        }
    }
    //滑动监听接口
    public interface OnScrollChangeListener {
        //滑动到顶部时的回调
        void onScrollToStart();
        //滑动到底部时的回调
        void onScrollToEnd();
        //滑动时回调
        void onScroll();
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }
}

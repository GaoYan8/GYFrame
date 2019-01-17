package com.gy.gylibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gy.gylibrary.R;

/**
 * @author 高炎
 * @email yan.gao@zarltech.com
 * @create 2019/1/17
 * @Describe
 */
public class LARBAlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView tvTitle;
    private TextView tvMsg;
    private Button btnNeg;
    private Button btnPos;
    private ImageView ivLine;
    private LinearLayout isGroupShow;
    private CheckBox isGroupDelete;
    private TextView isGroupDeleteText;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public LARBAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public LARBAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_larb_alert_dialog_view, null);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        tvTitle = (TextView) view.findViewById(R.id.tv_alert_title);
        tvTitle.setVisibility(View.GONE);
        tvMsg = (TextView) view.findViewById(R.id.tv_alert_msg);
        tvMsg.setVisibility(View.GONE);
        btnNeg = (Button) view.findViewById(R.id.btn_alert_neg);
        btnNeg.setVisibility(View.GONE);
        btnPos = (Button) view.findViewById(R.id.btn_alert_pos);
        btnPos.setVisibility(View.GONE);
        ivLine = (ImageView) view.findViewById(R.id.iv_alert_line);
        ivLine.setVisibility(View.GONE);
        isGroupShow = (LinearLayout) view.findViewById(R.id.ll_alert_is_group_show);
        isGroupShow.setVisibility(View.GONE);
        isGroupDelete = (CheckBox) view.findViewById(R.id.cb_alert_is_group_delete_button);
        isGroupDeleteText = (TextView) view.findViewById(R.id.tv_alert_is_group_delete_text);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ViewAlertDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public LARBAlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            tvTitle.setText("标题");
        } else {
            tvTitle.setText(title);
        }
        return this;
    }

    public LARBAlertDialog setMsg(CharSequence msg) {
        showMsg = true;
        if ("".equals(msg)) {
            tvMsg.setText("内容");
        } else {
            tvMsg.setText(msg);
        }
        return this;
    }

    public LARBAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public LARBAlertDialog setPositiveButton(String text, final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btnPos.setText("确定");
        } else {
            btnPos.setText(text);
        }
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public LARBAlertDialog setNegativeButton(String text, final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btnNeg.setText("取消");
        } else {
            btnNeg.setText(text);
        }
        btnNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }


    /**
     * 设置按钮颜色
     *
     * @return
     */
    public LARBAlertDialog setTitleTextColor(int titleTextColor, int leftButtonTextColor, int rightButtonTextColor) {
        if (0 != titleTextColor) {
            tvTitle.setTextColor(context.getResources().getColor(titleTextColor));
        }
        if (0 != leftButtonTextColor) {
            btnNeg.setTextColor(context.getResources().getColor(leftButtonTextColor));
        }
        if (0 != rightButtonTextColor) {
            btnPos.setTextColor(context.getResources().getColor(rightButtonTextColor));
        }
        return this;
    }

    public LARBAlertDialog setTitleTextColor(String titleTextColor, String leftButtonTextColor, String rightButtonTextColor) {
        if (!TextUtils.isEmpty(titleTextColor)) {
            tvTitle.setTextColor(Color.parseColor(titleTextColor));
        }
        if (!TextUtils.isEmpty(leftButtonTextColor)) {
            btnNeg.setTextColor(Color.parseColor(leftButtonTextColor));
        }
        if (!TextUtils.isEmpty(rightButtonTextColor)) {
            btnPos.setTextColor(Color.parseColor(rightButtonTextColor));
        }
        return this;
    }


    private void setLayout() {
        if (!showTitle && !showMsg) {
            tvTitle.setText("提示");
            tvTitle.setVisibility(View.VISIBLE);
        }
        if (showTitle) {
            tvTitle.setVisibility(View.VISIBLE);
        }
        if (showMsg) {
            tvMsg.setVisibility(View.VISIBLE);
        }
        if (!showPosBtn && !showNegBtn) {
            btnPos.setText("确定");
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.bg_alert_dialog_single_selector);
            btnPos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        if (showPosBtn && showNegBtn) {
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.bg_alert_dialog_right_selector);
            btnNeg.setVisibility(View.VISIBLE);
            btnNeg.setBackgroundResource(R.drawable.bg_alert_dialog_left_selector);
            ivLine.setVisibility(View.VISIBLE);
        }
        if (showPosBtn && !showNegBtn) {
            btnPos.setVisibility(View.VISIBLE);
            btnPos.setBackgroundResource(R.drawable.bg_alert_dialog_single_selector);
        }
        if (!showPosBtn && showNegBtn) {
            btnNeg.setVisibility(View.VISIBLE);
            btnNeg.setBackgroundResource(R.drawable.bg_alert_dialog_single_selector);
        }
    }

    public LARBAlertDialog show() {
        setLayout();
        dialog.show();
        return this;
    }


    public boolean isShowing() {
        if (null != dialog) {
            return dialog.isShowing();
        }
        return false;
    }


    /**
     * 隐藏
     *
     * @return
     */
    public LARBAlertDialog cancel() {
        dialog.cancel();
        return this;
    }

    /**
     * 是否显示  额外功能
     *
     * @return
     */
    public LARBAlertDialog isShowProjectDelete(String title) {
        isGroupDeleteText.setText(title);
        isGroupShow.setVisibility(View.VISIBLE);
        return this;
    }


    /**
     * 返回用户选择的选择状态
     *
     * @return
     */
    public boolean isChecked() {
        return isGroupDelete.isChecked();
    }

    /**
     * 设置用户按压行为
     *
     * @param cancelableTouch
     * @param cancelable
     */
    public void cancelableTouch(boolean cancelableTouch, boolean cancelable) {
        dialog.setCanceledOnTouchOutside(cancelableTouch);
        dialog.setCancelable(cancelable);// 不可以用“返回键”取消

    }
}

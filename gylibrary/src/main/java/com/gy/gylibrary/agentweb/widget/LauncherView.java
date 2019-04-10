/*
 * Copyright 2018 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gy.gylibrary.agentweb.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.gy.gylibrary.R;


/**
 * Created by YanZhenjie on 2018/5/30.
 */
public class LauncherView extends RelativeLayout implements View.OnClickListener {

    private View.OnClickListener mCancelClickListener;

    public LauncherView(Context context) {
        this(context, null, 0);
    }

    public LauncherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.window_launcher, this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(v);
            }
        }
    }

    public void setCancelClickListener(OnClickListener cancelClickListener) {
        this.mCancelClickListener = cancelClickListener;
    }
}
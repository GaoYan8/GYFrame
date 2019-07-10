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
package com.gy.gyframe.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.gy.gylibrary.GYConfig;
import com.gy.gylibrary.agentweb.widget.AlertWindow;
import com.gy.gylibrary.agentweb.widget.LauncherView;
import com.gy.gylibrary.okhttplib.OkHttpUtil;
import com.gy.gylibrary.okhttplib.annotation.CacheType;
import com.gy.gylibrary.okhttplib.annotation.Encoding;
import com.gy.gylibrary.okhttplib.config.HttpConfig;
import com.gy.gylibrary.okhttplib.cookie.PersistentCookieJar;
import com.gy.gylibrary.okhttplib.cookie.cache.SetCookieCache;
import com.gy.gylibrary.okhttplib.cookie.persistence.SharedPrefsCookiePersistor;
import com.gy.gylibrary.okhttplib.interceptor.impl.DefaultExceptionInterceptor;
import com.gy.gylibrary.okhttplib.interceptor.impl.DefaultResultInterceptor;

import java.io.File;


/**
 * Created by YanZhenjie on 2018/5/30.
 */
public class App extends Application {

    private static App _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        GYConfig.initFileDir(this);
        GYConfig.initCrashHandlerUtil(this);
        GYConfig.initOkHttpClient();
        GYConfig.initOkHttp3Client(this, new HttpConfig());

    }

    public static App getInstance() {
        return _instance;
    }

    public void showLauncherView() {
        final AlertWindow alertWindow = new AlertWindow(this);
        LauncherView view = new LauncherView(this);
        view.setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertWindow.dismiss();
            }
        });
        alertWindow.setContentView(view);
        alertWindow.show();
    }


}
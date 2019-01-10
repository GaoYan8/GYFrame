package com.gy.gylibrary.http.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

public abstract class StringCallback extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException {
        String responseCode = response.code() + "";
        String responseStr = response.body().string();
        //Log.e("NEt", "ResponseCode" + responseCode + "---" + responseStr);
        return responseStr;
    }
}

package com.gy.gylibrary.jsbridge;


import com.gy.gylibrary.utils.LogUtils;

public class DefaultHandler implements BridgeHandler{

	String TAG = "DefaultHandler";
	
	@Override
	public void handler(String data, CallBackFunction function) {
		LogUtils.e("com.zarltech.zarlspmis.frame.jsbridge.DefaultHandler");
		if(function != null){
			function.onCallBack("DefaultHandler response data");
		}
	}

}

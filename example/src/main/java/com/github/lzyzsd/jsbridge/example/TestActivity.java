package com.github.lzyzsd.jsbridge.example;

import android.os.Bundle;
import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.tydic.nj.NJActivity;

public class TestActivity extends NJActivity {
    private static final boolean DBG = true;
    private static final String TAG = TestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        BridgeWebView webView = (BridgeWebView) findViewById(R.id.webView);

        super.init(webView);

        loadUrl("file:///android_asset/nj_demo.html");

        // 调用js函数
        webView.callHandler("functionInJavascript", "这是传给js的数据", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d(TAG, "js 返回的结果:" + data);
            }
        });
    }

}

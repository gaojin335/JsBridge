package com.github.lzyzsd.jsbridge.example;

import android.os.Bundle;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.tydic.nj.NJActivity;

public class TestActivity extends NJActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        BridgeWebView bridgeWebView = (BridgeWebView) findViewById(R.id.webView);

        super.init(bridgeWebView);

        loadUrl("file:///android_asset/nj_demo.html");
    }

}

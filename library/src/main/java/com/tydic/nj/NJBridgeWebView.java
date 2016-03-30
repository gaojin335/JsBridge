package com.tydic.nj;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeUtil;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

/**
 * Created by gaobo on 16/3/28.
 */
public class NJBridgeWebView extends BridgeWebView {
    public static final String toLoadNJJs = null;//"NJBridge.js";

    public NJBridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NJBridgeWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NJBridgeWebView(Context context) {
        super(context);
    }

    @Override
    protected BridgeWebViewClient generateBridgeWebViewClient() {
        return new BridgeWebViewClient(this) {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (NJBridgeWebView.toLoadNJJs != null) {
                    BridgeUtil.webViewLoadLocalJs(view, NJBridgeWebView.toLoadNJJs);
                }
            }
        };
    }
}

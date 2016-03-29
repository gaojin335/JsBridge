package com.tydic.nj;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;

import com.github.lzyzsd.jsbridge.BridgeWebView;

import java.util.ArrayList;

public abstract class NJActivity extends Activity {
    private static final boolean DBG = true;
    private static final String TAG = NJActivity.class.getSimpleName();

    protected BridgeWebView mWebView;

    // read from njconfig.xml
    protected ArrayList<NJPluginEntry> mPluginEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init(BridgeWebView webView) {
        this.mWebView = webView;
        this.mWebView.setWebChromeClient(makeChromeClient());
        loadConfig();
        initWebView();
    }

    public void initWebView() {
        if (mWebView == null) {
            Log.e(TAG, "Error BridgeWebView is null,please set a super.mWebView.");
        } else {
            for (NJPluginEntry pluginEntry : mPluginEntries) {
                NJPlugin njPlugin = instantiatePlugin(pluginEntry.pluginClass);
                njPlugin.setActivity(this);
                mWebView.registerHandler(pluginEntry.serviceName, njPlugin);
            }
        }
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    /**
     * 加载配置文件(njconfig.xml)
     */
    protected void loadConfig() {
        NJConfigXmlParser parser = new NJConfigXmlParser();
        parser.parse(this);
        mPluginEntries = parser.getPluginEntries();
    }

    /**
     * Create a plugin based on class name.
     */
    private NJPlugin instantiatePlugin(String className) {
        NJPlugin ret = null;
        try {
            Class<?> c = null;
            if ((className != null) && !("".equals(className))) {
                c = Class.forName(className);
            }
            if (c != null & NJPlugin.class.isAssignableFrom(c)) {
                ret = (NJPlugin) c.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error adding njplugin " + className + ".");
        }
        return ret;
    }

    /**
     * 构造一个默认的WebChromeClient对象,如果想自定义,子类可以重写些方法
     * @return
     */
    protected WebChromeClient makeChromeClient() {
        return new WebChromeClient();
    }

}

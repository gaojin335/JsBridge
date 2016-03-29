package com.tydic.nj;

import android.util.Log;

import com.github.lzyzsd.jsbridge.CallBackFunction;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaobo on 16/3/28.
 * CallBackFunction的包装类
 */
public class NJCallback {
    private static final boolean DBG = true;
    private static final String TAG = NJCallback.class.getSimpleName();

    // 传给js的json数据中的key
    private static final String JK_CALLBACK_NAME = "callbackName";
    private static final String JK_DATA = "data";

    private CallBackFunction mCallBackFunction;

    /**
     * 回调函数选择
     */
    public enum CallbackSwith {
        success, error
    }

    public NJCallback(CallBackFunction mCallBackFunction) {
        this.mCallBackFunction = mCallBackFunction;
    }

    /**
     * 调用成功的js回调函数
     *
     * @param data
     */
    public void success(String data) {
        callback(CallbackSwith.success, data);
    }

    /**
     * 调用失败的js回调函数
     *
     * @param data
     */
    public void error(String data) {
        callback(CallbackSwith.error, data);
    }

    private void callback(CallbackSwith callbackSwith, String data) {
        if (mCallBackFunction == null) {
            Log.e(TAG, "Error JsBridge 传过来的 CallBackFunction is null.");
        } else {
            /* 将数据封装为JSON格式:
            {
                callbackName:”success”, // 回调的函数，包括：”success”、”error"
                data:”” // 原生回传给js的数据，格式为字符串
            }
            */
            try {
                JSONObject dataJO = new JSONObject();
                if (callbackSwith == CallbackSwith.error) {
                    dataJO.put(JK_CALLBACK_NAME, "error");
                } else {
                    dataJO.put(JK_CALLBACK_NAME, "success");
                }
                dataJO.put(JK_DATA, data);
                mCallBackFunction.onCallBack(dataJO.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "json 格式错误.");
            }
        }
    }
}

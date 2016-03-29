package com.tydic.nj;

import android.app.Activity;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gaobo on 16/3/28.
 * Plugins must extend this class and override one of the execute methods.
 */
public class NJPlugin implements BridgeHandler {
    protected Activity mActivity;

    /**
     * Executes the request.
     *
     * @param action   The action to execute.
     * @param args     The exec() arguments.
     * @param callback The callback function used when calling back into JavaScript.
     * @throws JSONException
     */
    public void execute(String action, JSONArray args, NJCallback callback) throws JSONException {

    }

    /**
     * 处理js传过来的数据
     * @param data
     * @param function
     */
    @Override
    public void handler(String data, CallBackFunction function) {
        try {
            JSONObject dataJO = new JSONObject(data);
            String actionName = dataJO.getString("actionName");
            JSONArray argsJA = dataJO.getJSONArray("arguments");
            execute(actionName, argsJA, new NJCallback(function));
        } catch (JSONException e) {
            function.onCallBack("json格式不正确,错误信息:" + e.getMessage());
        }

    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }
}

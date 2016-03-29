package com.github.lzyzsd.jsbridge.example.plugin;

import android.widget.Toast;

import com.tydic.nj.NJCallback;
import com.tydic.nj.NJPlugin;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by gaobo on 16/3/28.
 *
 */
public class TestPlugin extends NJPlugin {

    private final String ACTION_TEST1 = "action1";
    private final String ACTION_TEST2 = "action2";

    @Override
    public void execute(String action, JSONArray args, NJCallback callback) throws JSONException {
        switch (action) {
            case ACTION_TEST1:
                String name = args.getString(0);
                int age = args.getInt(1);
                Toast.makeText(mActivity, name + ";" + age, Toast.LENGTH_SHORT).show();
                callback.success("action1调用成功");
                break;
            case ACTION_TEST2:
                String name1 = args.getString(0);
                int age1 = args.getInt(1);
                Toast.makeText(mActivity, name1 + ";" + age1, Toast.LENGTH_SHORT).show();
                callback.error("action2调用失败");
                break;
            default:
                break;
        }

    }
}

package com.tydic.nj;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gaobo on 16/3/28.
 */
public class NJConfigXmlParser {
    private static final boolean DBG = true;
    private static final String TAG = NJConfigXmlParser.class.getSimpleName();

    private ArrayList<NJPluginEntry> pluginEntries = new ArrayList<NJPluginEntry>();

    // 解析njconfig.xml文件
    public void parse(Activity action) {
        // First checking the class namespace for njconfig.xml
        int id = action.getResources().getIdentifier("njconfig", "xml", action.getClass().getPackage().getName());
        if (id == 0) {
            // If we couldn't find njconfig.xml there, we'll look in the namespace from AndroidManifest.xml
            id = action.getResources().getIdentifier("njconfig", "xml", action.getPackageName());
            if (id == 0) {
                Log.e(TAG, "res/xml/njconfig.xml is missing!");
                return;
            }
        }
        parse(action.getResources().getXml(id));
    }

    // 解析njconfig.xml文件
    public void parse(XmlResourceParser xml) {
        int eventType = -1;
        String service = "", pluginClass = "";
        boolean insideFeature = false;

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                String strNode = xml.getName();
                if (strNode.equals("feature")) {
                    //Check for supported feature sets  aka. plugins (Accelerometer, Geolocation, etc)
                    //Set the bit for reading params
                    insideFeature = true;
                    service = xml.getAttributeValue(null, "name");
                } else if (insideFeature && strNode.equals("param")) {
                    pluginClass = xml.getAttributeValue(null, "value");
                }

            } else if (eventType == XmlResourceParser.END_TAG) {
                String strNode = xml.getName();
                if (strNode.equals("feature")) {
                    pluginEntries.add(new NJPluginEntry(service, pluginClass));

                    service = "";
                    pluginClass = "";
                    insideFeature = false;
                }
            }
            try {
                eventType = xml.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<NJPluginEntry> getPluginEntries() {
        return pluginEntries;
    }
}

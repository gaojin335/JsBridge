package com.tydic.nj;

/**
 * Created by gaobo on 16/3/28.
 * njconfig.xml文件解析出来的model类
 */
public class NJPluginEntry {
    /**
     * The name of the service that this plugin implements
     */
    public String serviceName;

    /**
     * The plugin class name that implements the service.
     */
    public String pluginClass;

    public NJPluginEntry(String serviceName, String pluginClass) {
        this.serviceName = serviceName;
        this.pluginClass = pluginClass;
    }
}

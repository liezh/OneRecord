package com.liezh.commontools.config;

import android.content.res.AssetManager;

import com.liezh.commontools.CommonToolsContext;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    private final static String APP_CONFIG = "appConfig.properties";

    private static AssetManager ASSET_MANAGER;

    private static Properties PROPERTIES;

    static {
        try {
            PROPERTIES = new Properties();
            ASSET_MANAGER = CommonToolsContext.getContext().getApplicationContext().getAssets();
            InputStream inputStream = ASSET_MANAGER.open(APP_CONFIG);
            PROPERTIES.load(new InputStreamReader(inputStream, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取assets路径下的配置文件中的信息
     *
     * @param key
     */
    public static String getStringValue(String key) {
        String val = PROPERTIES.getProperty(key);
        if (val == null) {
            throw new RuntimeException("Can't find this property. key: " + key);
        }
        return val;
    }

    public static String getStringValue(String key, String def) {
        return PROPERTIES.getProperty(key, def);
    }

    public static Integer getIntValue(String key) {
        String val = PROPERTIES.getProperty(key);
        if (val == null) {
            throw new RuntimeException("Can't find this property. key: " + key);
        }
        return Integer.valueOf(val);
    }

    public static Integer getIntValue(String key, Integer def) {
        String val = PROPERTIES.getProperty(key, def.toString());
        return Integer.valueOf(val);
    }



}

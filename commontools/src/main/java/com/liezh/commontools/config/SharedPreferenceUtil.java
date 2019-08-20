package com.liezh.commontools.config;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferenceUtil {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void setPreferences(SharedPreferences preferences) {
        SharedPreferenceUtil.sharedPreferences = preferences;
    }

    /**
     * 存储单个JavaBean对象
     *
     * @param tag
     * @param object
     */
    public static void saveJavaBean(String tag, Object object) {
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(object);
        editor.putString(tag, jsonStr);
        editor.commit();
    }

    /**
     * 返回单个JavaBean对象
     *
     * @param tag
     * @return
     */
    public static Object getJavaBean(String tag) {
        String jsonStr = sharedPreferences.getString(tag, null);
        if (null == jsonStr) return null;
        Gson gson = new Gson();
        Object object = gson.fromJson(jsonStr, Object.class);
        return object;
    }

    /**
     * 保存字符串
     *
     * @param tag
     * @param value
     */
    public static void saveString(String tag, String value) {
        editor = sharedPreferences.edit();
        editor.putString(tag, value);
        editor.commit();
    }

    public static void saveInt(String tag, int value) {
        editor = sharedPreferences.edit();
        editor.putInt(tag, value);
        editor.commit();
    }

    /**
     * 获取字符串的值
     *
     * @param tag
     * @param defualt
     * @return
     */
    public static String getStringByTag(String tag, String defualt) {
        return sharedPreferences.getString(tag, defualt);
    }

    public static Integer getIntByTag(String tag, Integer def) {
        return sharedPreferences.getInt(tag, def);
    }

    /**
     * 清除所有数据
     */
    public static void clearSharedDatas() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 清除指定tag数据
     *
     * @param tag
     */
    public static void clearPreferencesByTags(String tag) {
        editor = sharedPreferences.edit();
        editor.putString(tag, null);
        editor.commit();
    }
}

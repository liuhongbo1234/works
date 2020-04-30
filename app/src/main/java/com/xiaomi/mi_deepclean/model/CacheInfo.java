package com.xiaomi.mi_deepclean.model;

import android.graphics.drawable.Drawable;

public class CacheInfo {
    private String name;
    private String packageName;
    private Drawable icon;
    //应用版本
    private String versionName;
    //应用大小
    private String codeSize;
    //数据大小
    private String dataSize;
    //缓存大小
    private String cacheSize;

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getName() {
        return name;
    }

    public String getDataSize() {
        return dataSize;
    }

    public String getCacheSize() {
        return cacheSize;
    }

    public String getCodeSize() {
        return codeSize;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setCacheSize(String cacheSize) {
        this.cacheSize = cacheSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public void setCodeSize(String codeSize) {
        this.codeSize = codeSize;
    }

    public String getPackageName() {
        return packageName;
    }
}

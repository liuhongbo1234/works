package com.xiaomi.mi_deepclean.model;

import android.graphics.drawable.Drawable;

public class CacheListItem {
    private long mCacheSize;
    private long mDataSize;
    private long mCodeSize;
    private String mPackageName, mApplicationName, mAppVersion;
    private Drawable mIcon;
    private boolean checked = true;

    public CacheListItem(String packageName, String applicationName, String appVersion, Drawable icon, long cacheSize, long dataSize, long codeSize) {
        mCacheSize = cacheSize;
        mPackageName = packageName;
        mApplicationName = applicationName;
        mAppVersion = appVersion;
        mIcon = icon;
        mDataSize = dataSize;
        mCodeSize = codeSize;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Drawable getApplicationIcon() {
        return mIcon;
    }

    public String getApplicationName() {
        return mApplicationName;
    }

    public long getCacheSize() {
        return mCacheSize;
    }

    public long getDataSize() {
        return mDataSize;
    }

    public long getCodeSize() {
        return mCodeSize;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getAppVersion() {
        return mAppVersion;
    }
}

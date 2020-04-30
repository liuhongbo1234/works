package com.xiaomi.mi_deepclean.base;

import android.content.Context;

import com.xiaomi.mi_deepclean.engine.CacheInfoProvider;
import com.xiaomi.mi_deepclean.model.CacheInfo;

import java.util.Vector;

public class Apps {
    private static Vector<CacheInfo> cacheInfos = new Vector<>();

    public static void set(final Context context) {
        new Thread() {
            @Override
            public void run() {
                new CacheInfoProvider(cacheInfos, context);
            }
        }.start();
    }

    public static Vector<CacheInfo> get() {
        return cacheInfos;
    }
}

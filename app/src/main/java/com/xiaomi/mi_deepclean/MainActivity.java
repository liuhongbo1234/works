package com.xiaomi.mi_deepclean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.Log;

import com.xiaomi.mi_deepclean.engine.CacheInfoProvider;
import com.xiaomi.mi_deepclean.model.CacheInfo;
import com.xiaomi.mi_deepclean.utils.ReflectUtil;
import com.xiaomi.mi_deepclean.utils.TextFormater;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        Vector<CacheInfo> cacheInfos = new Vector<>();
        CacheInfoProvider cacheInfoProvider = new CacheInfoProvider(handler, cacheInfos, this);
        final Vector<CacheInfo> v = cacheInfoProvider.getCacheInfos();


//        Method getPackageSizeInfo = null;
//        try {
//            getPackageSizeInfo = PackageManager.class.getClass().getDeclaredMethod("getPackageSizeInfo", String.class, IPackageDataObserver.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        if (getPackageSizeInfo != null) {
//            try {
//                getPackageSizeInfo.invoke(PackageManager.class, v.get(0).getPackageName(), Process.myUid() / 100000, new IPackageStatsObserver.Stub() {
//                    @Override
//                    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
//                        long mCacheSize = pStats.cacheSize;
//                        long mCodeSize = pStats.codeSize;
//                        long mDataSize = pStats.dataSize;
//                        v.get(0).setCacheSize(TextFormater.dataSizeFormat(mCacheSize));
//                        v.get(0).setCodeSize(TextFormater.dataSizeFormat(mCodeSize));
//                        v.get(0).setDataSize(TextFormater.dataSizeFormat(mDataSize));
//                    }
//                });
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
        Log.d("liuhongbo_cache", v.get(2).getPackageName()+":"+v.get(2).getCacheSize());
    }


}

package com.xiaomi.mi_deepclean.engine;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.TextView;

import com.xiaomi.mi_deepclean.model.CacheInfo;
import com.xiaomi.mi_deepclean.utils.ReflectUtil;
import com.xiaomi.mi_deepclean.utils.TextFormater;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class CacheInfoProvider {
    private PackageManager packageManager;
    private Vector<CacheInfo> cacheInfos;
    private int size = 0;
    private Context context;

    public CacheInfoProvider(Vector<CacheInfo> cacheInfos, Context context) {
        // 拿到一个包管理器
        packageManager = context.getPackageManager();
        this.cacheInfos = cacheInfos;
        this.context = context;
        initCacheInfos();
    }

    public void initCacheInfos() {
        // 获取到所有安装了的应用程序的信息，包括那些卸载了的，但没有清除数据的应用程序
        List<PackageInfo> packageInfos = packageManager
                .getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        size = packageInfos.size();
        for (int i = 0; i < size; i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            // 拿到应用程序的信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            // 拿到应用程序的程序名
            String name = applicationInfo.loadLabel(packageManager).toString();
            // 过滤掉一些存储空间占用太小的应用
            if (name.length() > 12) continue;

            CacheInfo cacheInfo = new CacheInfo();
            // 设置程序名称
            cacheInfo.setName(name);
            // 拿到包名
            String packageName = packageInfo.packageName;
            cacheInfo.setPackageName(packageName);
            // 拿到应用程序的图标
            Drawable icon = applicationInfo.loadIcon(packageManager);
            cacheInfo.setIcon(icon);
            String versionName = packageInfo.versionName;
            cacheInfo.setVersionName(versionName);
            // 获取缓存，用户数据，APP大小
            setData(cacheInfo, applicationInfo);
            cacheInfos.add(cacheInfo);
        }
    }


    private void setData(CacheInfo cacheInfo, ApplicationInfo applicationInfo) {
        String packageName = cacheInfo.getPackageName();
        UUID uuid = applicationInfo.storageUuid;
        StorageStatsManager storageStatsManager = (StorageStatsManager) context.getSystemService(Context.STORAGE_STATS_SERVICE);
        int uid = 0;
        StorageStats storageStats = null;
        try {
            uid = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).uid;
            storageStats = storageStatsManager.queryStatsForUid(uuid, uid);
            cacheInfo.setCacheSize(TextFormater.dataSizeFormat(storageStats.getCacheBytes()));
            cacheInfo.setCodeSize(TextFormater.dataSizeFormat(storageStats.getAppBytes()));
            cacheInfo.setDataSize(TextFormater.dataSizeFormat(storageStats.getDataBytes()));
            Log.d("liuhongbo", cacheInfo.getCacheSize() + "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("liuhongbo", e.getMessage() + "");
        } catch (IOException e) {
            Log.d("liuhongbo", e.getMessage() + "");
        }
    }

//    public Vector<CacheInfo> getCacheInfos() {
//        return cacheInfos;
//    }
}

package com.whitefan.jdlite.util;

import android.content.pm.PackageInfo;

import com.whitefan.jdlite.MyApplication;

public class DeviceUtil {

    /**
     * 获取当前app version name
     */
    public static String getAppVersionName() {
        String appVersionName = "-1";
        try {
            PackageInfo packageInfo = MyApplication.getMInstance().getPackageManager()
                    .getPackageInfo(MyApplication.getMInstance().getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (Exception e) {
        }
        return appVersionName;
    }
}

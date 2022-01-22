package com.whitefan.jdlite.util

import android.text.TextUtils
import com.whitefan.jdlite.MyApplication


object CacheUtil {
    private const val PREFS = "page_cache_config"

    fun getString(key: String?): String? {
        return SharedPreferencesUtils.getPrefs(MyApplication.mInstance, PREFS).getString(key, "")
    }

    fun putString(key: String?, value: String?) {
        SharedPreferencesUtils.getEditor(MyApplication.mInstance, PREFS)
            .putString(key, value).commit()
    }

    fun getCKPtPin(key: String): String {
        try {
            val ck = getString(key)
            if (TextUtils.isEmpty(ck)) {
                return ""
            }
            var pin = ck!!.substring(ck.indexOf("pt_pin="))
            pin = pin.substring(0, pin.indexOf(";"))
            return pin
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return key
    }
}
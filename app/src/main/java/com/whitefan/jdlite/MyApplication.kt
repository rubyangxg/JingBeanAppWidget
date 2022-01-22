package com.whitefan.jdlite

import android.app.Application



class MyApplication : Application() {
    companion object {
        @JvmStatic
        lateinit var mInstance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this


    }
}
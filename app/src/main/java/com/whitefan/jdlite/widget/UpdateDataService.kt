package com.whitefan.jdlite.widget

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class UpdateDataService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("====","onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }
}
package com.whitefan.jdlite.util

import android.os.Handler
import android.os.Looper
import com.whitefan.jdlite.widget.WidgetUpdateDataUtil
import com.whitefan.jdlite.widget.WidgetUpdateDataUtil_2


object UpdateTask {
    var widgetUpdateDataUtil = WidgetUpdateDataUtil()
    var widgetUpdateDataUtil1 = WidgetUpdateDataUtil()
    var widgetUpdateDataUtil2 = WidgetUpdateDataUtil()
    var widgetUpdateDataUtil3 = WidgetUpdateDataUtil()
    var widgetUpdateDataUtil4 = WidgetUpdateDataUtil()
    var widgetUpdateDataUtil5 = WidgetUpdateDataUtil()
    var widgetUpdateDataUtil_2 = WidgetUpdateDataUtil_2()
    var widgetUpdateDataUtil1_2 = WidgetUpdateDataUtil_2()
    var widgetUpdateDataUtil2_2 = WidgetUpdateDataUtil_2()
    var widgetUpdateDataUtil3_2 = WidgetUpdateDataUtil_2()
    var widgetUpdateDataUtil4_2 = WidgetUpdateDataUtil_2()
    var widgetUpdateDataUtil5_2 = WidgetUpdateDataUtil_2()

    var handler = Handler(Looper.getMainLooper())

    fun updateAll() {
        handler.post {
            widgetUpdateDataUtil.updateWidget("ck")
        }
        handler.postDelayed({
            widgetUpdateDataUtil_2.updateWidget("ck")
        }, 2000)

        handler.postDelayed({
            widgetUpdateDataUtil1.updateWidget("ck1")
        }, 4000)

        handler.postDelayed({
            widgetUpdateDataUtil1_2.updateWidget("ck1")
        }, 6000)

        handler.postDelayed({
            widgetUpdateDataUtil2.updateWidget("ck2")
        }, 8000)

        handler.postDelayed({
            widgetUpdateDataUtil2_2.updateWidget("ck2")
        }, 10000)

        handler.postDelayed({
            widgetUpdateDataUtil3.updateWidget("ck3")
        }, 12000)

        handler.postDelayed({
            widgetUpdateDataUtil3_2.updateWidget("ck3")
        }, 14000)

        handler.postDelayed({
            widgetUpdateDataUtil4.updateWidget("ck4")
        }, 16000)

        handler.postDelayed({
            widgetUpdateDataUtil4_2.updateWidget("ck4")
        }, 18000)

        handler.postDelayed({
            widgetUpdateDataUtil5.updateWidget("ck5")
        }, 20000)

        handler.postDelayed({
            widgetUpdateDataUtil5_2.updateWidget("ck5")
        }, 22000)

    }
}
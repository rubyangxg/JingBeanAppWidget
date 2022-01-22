package com.whitefan.jdlite.widget

import android.appwidget.AppWidgetProvider
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import com.whitefan.jdlite.R
import com.whitefan.jdlite.util.UpdateTask

class MyAppWidgetProvider1_2 : AppWidgetProvider() {

    /*
     * 每次窗口小部件被更新都调用一次该方法
     */
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appwidgetId in appWidgetIds) {
            val remoteViews = RemoteViews(context.packageName, R.layout.widges_layout2)
 //           remoteViews.setViewPadding(R.id.rootParent, R.dimen.dp_15.dmToPx(), 0, R.dimen.dp_15.dmToPx(), 0)
            val name = ComponentName(context, MyAppWidgetProvider1_2::class.java)
            appWidgetManager.updateAppWidget(name, remoteViews)
        }
    }

    /*
     * 接收窗口小部件点击时发送的广播
     */
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        //这里判断是自己的action，做自己的事情
        if (intent.action == ACTION_APPWIDGET_UPDATE) {
            UpdateTask.widgetUpdateDataUtil1_2.updateWidget("ck1")
        }
    }

    /*
     * 当小部件从备份恢复时调用该方法
     */
    override fun onRestored(context: Context, oldWidgetIds: IntArray, newWidgetIds: IntArray) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }

    /*
     * 每删除一次窗口小部件就调用一次
     */
    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
    }

    /*
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    override fun onEnabled(context: Context) {
//        context.startService(Intent(context, UpdateDataService::class.java))
    }

    /*
     * 当最后一个该窗口小部件删除时调用该方法
     */
    override fun onDisabled(context: Context) {
//        context.stopService(Intent(context, UpdateDataService::class.java))
    }

    /*
     * 当小部件大小改变时
     */
    override fun onAppWidgetOptionsChanged(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, newOptions: Bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}
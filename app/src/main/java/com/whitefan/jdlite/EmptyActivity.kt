package com.whitefan.jdlite

import android.content.Intent
import android.text.TextUtils
import com.whitefan.jdlite.util.ActivityCollector
import com.whitefan.jdlite.util.UpdateTask

class EmptyActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_empty
    }

    override fun initView() {
        val ck = intent.getStringExtra("data")
        dealData(ck)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val ck = intent.getStringExtra("data")
        dealData(ck)
    }

    private fun dealData(ck: String?) {
        if (TextUtils.isEmpty(ck)) return
        when (ck) {
            "ck" -> {
                UpdateTask.widgetUpdateDataUtil.updateWidget(ck)
                UpdateTask.widgetUpdateDataUtil_2.updateWidget(ck)
            }
            "ck1" -> {
                UpdateTask.widgetUpdateDataUtil1.updateWidget(ck)
                UpdateTask.widgetUpdateDataUtil1_2.updateWidget(ck)
            }
            "ck2" -> {
                UpdateTask.widgetUpdateDataUtil2.updateWidget(ck)
                UpdateTask.widgetUpdateDataUtil2_2.updateWidget(ck)
            }
            "ck3" -> {
                UpdateTask.widgetUpdateDataUtil3.updateWidget(ck)
                UpdateTask.widgetUpdateDataUtil3_2.updateWidget(ck)
            }
            "ck4" -> {
                UpdateTask.widgetUpdateDataUtil4.updateWidget(ck)
                UpdateTask.widgetUpdateDataUtil4_2.updateWidget(ck)
            }
            "ck5" -> {
                UpdateTask.widgetUpdateDataUtil5.updateWidget(ck)
                UpdateTask.widgetUpdateDataUtil5_2.updateWidget(ck)
            }
        }
        ActivityCollector.finishAll()
    }

    override fun initData() {
    }

    override fun setEvent() {
    }
}
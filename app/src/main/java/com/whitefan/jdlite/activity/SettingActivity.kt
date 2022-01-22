package com.whitefan.jdlite.activity

import android.content.Context
import android.content.Intent
import android.os.Vibrator
import android.text.TextUtils
import android.widget.Toast
import com.whitefan.jdlite.BaseActivity
import com.whitefan.jdlite.MyApplication
import com.whitefan.jdlite.R
import com.whitefan.jdlite.dialog.InputDialog
import com.whitefan.jdlite.dialog.MenuDialog
import com.whitefan.jdlite.util.CacheUtil
import com.whitefan.jdlite.util.UpdateTask
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    var paddingDataList = ArrayList<String>()
    var douShowTypeList = ArrayList<String>()
    var douShowTypeList2 = ArrayList<String>()


    override fun setLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        setTitle("小组件设置")

        paddingDataList.add("无边距")
        paddingDataList.add("5dp")
        paddingDataList.add("10dp")
        paddingDataList.add("15dp")
        paddingDataList.add("20dp")

        douShowTypeList.add("文字展示")
        douShowTypeList.add("柱状图展示")

        douShowTypeList2.add("京豆展示")
        douShowTypeList2.add("农场进度展示")
    }

    override fun initData() {
        hideTips.isChecked = "1" == CacheUtil.getString("hideTips")

        hideNichen.isChecked = "1" == CacheUtil.getString("hideNichen")

        colorSwitch.isChecked = "1" == CacheUtil.getString("colorSwitch")

        startUpdateService.isChecked = "1" != CacheUtil.getString("startUpdateService")

        hideDivider.isChecked = "1" == CacheUtil.getString("hideDivider")


        val paddingType = CacheUtil.getString("paddingType")
        paddingTip.text = if (TextUtils.isEmpty(paddingType)) {
            "15dp"
        } else {
            paddingType
        }

        val douShowTypeTip = CacheUtil.getString("douShowType")
        douShowType.text = if (TextUtils.isEmpty(douShowTypeTip)) {
            "文字展示"
        } else {
            douShowTypeTip
        }

        val douShowType2Tip = CacheUtil.getString("douShowType2")
        douShowType2.text = if (TextUtils.isEmpty(douShowType2Tip)) {
            "京豆展示"
        } else {
            douShowType2Tip
        }

        var designColorTxt = CacheUtil.getString("designColor")
        if (TextUtils.isEmpty(designColorTxt)) {
            designColorTxt = "#FFFFFF"
        }
        designColor.text = designColorTxt

        var designColorTxt2 = CacheUtil.getString("designColor2")
        if (TextUtils.isEmpty(designColorTxt2)) {
            designColorTxt2 = "#FFFFFF"
        }
        designColor2.text = designColorTxt2
    }

    override fun setEvent() {
        hideTips.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                CacheUtil.putString("hideTips", "1")
            } else {
                CacheUtil.putString("hideTips", "0")
            }
            // 震动
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2) //单位是ms
        }

        hideNichen.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                CacheUtil.putString("hideNichen", "1")
            } else {
                CacheUtil.putString("hideNichen", "0")
            }
            // 震动
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2) //单位是ms
        }

        startUpdateService.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                CacheUtil.putString("startUpdateService", "0")
            } else {
                CacheUtil.putString("startUpdateService", "1")
            }
            // 震动
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2) //单位是ms
        }

        colorSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                CacheUtil.putString("colorSwitch", "1")
            } else {
                CacheUtil.putString("colorSwitch", "0")
            }
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2) //单位是ms
        }


        hideDivider.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                CacheUtil.putString("hideDivider", "1")
            } else {
                CacheUtil.putString("hideDivider", "0")
            }
            // 震动
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2) //单位是ms
        }

        settingFinish.setOnClickListener {
            UpdateTask.updateAll()
            Toast.makeText(this, "小组件状态更新完毕", Toast.LENGTH_SHORT).show()
            // 震动
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(100) //单位是ms
            vibrator.vibrate(1) //单位是ms
            vibrator.vibrate(10) //单位是ms
        }

        douShowType.setOnClickListener {
            var menuDialog = MenuDialog(this, douShowTypeList) {
                CacheUtil.putString("douShowType", it)
                douShowType.text = it
            }
            menuDialog.pop()
        }

        douShowType2.setOnClickListener {
            var menuDialog = MenuDialog(this, douShowTypeList2) {
                CacheUtil.putString("douShowType2", it)
                douShowType2.text = it
            }
            menuDialog.pop()
        }

        paddingTip.setOnClickListener {
            var menuDialog = MenuDialog(this, paddingDataList) {
                CacheUtil.putString("paddingType", it)
                paddingTip.text = it
            }
            menuDialog.pop()
        }

        designColor.setOnClickListener {
            var inputDialog = InputDialog(this)
            inputDialog.onOkClickListener = object : InputDialog.OnOkClickListener {
                override fun ok(str: String) {
                    CacheUtil.putString("designColor", str)
                    designColor.text = str
                }
            }
            inputDialog.pop()
        }
        designColor2.setOnClickListener {
            var inputDialog = InputDialog(this)
            inputDialog.onOkClickListener = object : InputDialog.OnOkClickListener {
                override fun ok(str: String) {
                    CacheUtil.putString("designColor2", str)
                    designColor2.text = str
                }
            }
            inputDialog.pop()
        }

    }
}
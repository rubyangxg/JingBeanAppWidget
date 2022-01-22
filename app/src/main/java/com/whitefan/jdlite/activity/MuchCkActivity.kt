package com.whitefan.jdlite.activity

import android.content.ClipData
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.whitefan.jdlite.BaseActivity
import com.whitefan.jdlite.R
import com.whitefan.jdlite.dialog.NormalInputCKDialog
import com.whitefan.jdlite.util.CacheUtil
import com.whitefan.jdlite.util.UpdateTask
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_much.*
import kotlinx.android.synthetic.main.activity_much.updateCK
import kotlinx.android.synthetic.main.activity_much.updateCK1
import kotlinx.android.synthetic.main.activity_setting.*
import android.content.ClipboardManager
import android.content.Context
import android.os.Vibrator

class MuchCkActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_much
    }


    override fun initView() {
        setTitle("账号设置")
    }

    override fun initData() {


        inputCK0.setText(CacheUtil.getString("ck"))
        inputCK1.setText(CacheUtil.getString("ck1"))
        inputCK2.setText(CacheUtil.getString("ck2"))

        inputCK3.setText(CacheUtil.getString("ck3"))
        inputCK4.setText(CacheUtil.getString("ck4"))
        inputCK5.setText(CacheUtil.getString("ck5"))
    }


    override fun setEvent() {

        updateCK.setOnClickListener {
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(6) //单位是ms
            val normalInputCKDialog = NormalInputCKDialog(this)
            normalInputCKDialog.onOkClickListener = object : NormalInputCKDialog.OnOkClickListener {
                override fun ok(ck: String, remark: String) {
                    if (TextUtils.isEmpty(ck)) {
                        Toast.makeText(this@MuchCkActivity, "CK为空，添加失败", Toast.LENGTH_SHORT).show()
                    } else {
                        CacheUtil.putString("ck", ck)
                        Toast.makeText(this@MuchCkActivity, "CK添加成功", Toast.LENGTH_SHORT).show()
                        UpdateTask.widgetUpdateDataUtil.updateWidget("ck")
                        Log.i("====", "1")
                    }
                }
            }
            normalInputCKDialog.pop()
        }

        inputCK0.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Label", inputCK0.text)
            clipboardManager.setPrimaryClip(clipData)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(20) //单位是ms

            Toast.makeText(this,"已复制CK到剪贴板",Toast.LENGTH_SHORT).show()

        }

        inputCK1.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Label", inputCK1.text)
            clipboardManager.setPrimaryClip(clipData)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(20) //单位是ms
            // Toast 提示
            Toast.makeText(this,"已复制CK到剪贴板",Toast.LENGTH_SHORT).show()

        }

        inputCK2.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Label", inputCK2.text)
            clipboardManager.setPrimaryClip(clipData)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(20) //单位是ms
            // Toast 提示
            Toast.makeText(this,"CK已复制到剪贴板",Toast.LENGTH_SHORT).show()

        }

        inputCK3.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Label", inputCK3.text)
            clipboardManager.setPrimaryClip(clipData)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(20) //单位是ms
            // Toast 提示
            Toast.makeText(this,"已复制CK到剪贴板",Toast.LENGTH_SHORT).show()

        }

        inputCK4.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Label", inputCK4.text)
            clipboardManager.setPrimaryClip(clipData)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(20) //单位是ms
            Toast.makeText(this,"已复制CK到剪贴板",Toast.LENGTH_SHORT).show()

        }

        inputCK5.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Label", inputCK5.text)
            clipboardManager.setPrimaryClip(clipData)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(20) //单位是ms
            // Toast 提示
            Toast.makeText(this,"已复制CK到剪贴板",Toast.LENGTH_SHORT).show()

        }



        updateCK1.setOnClickListener {
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(6) //单位是ms
            val normalInputCKDialog = NormalInputCKDialog(this)
            normalInputCKDialog.onOkClickListener = object : NormalInputCKDialog.OnOkClickListener {
                override fun ok(ck: String, remark: String) {
                    if (TextUtils.isEmpty(ck)) {
                        Toast.makeText(this@MuchCkActivity, "CK为空，添加失败", Toast.LENGTH_SHORT).show()
                    } else {
                        CacheUtil.putString("ck1", ck)
                        Toast.makeText(this@MuchCkActivity, "CK2添加成功", Toast.LENGTH_SHORT).show()
                        UpdateTask.widgetUpdateDataUtil.updateWidget("ck1")
                        Log.i("====", "1")
                    }
                }
            }
            normalInputCKDialog.pop()
        }
        updateCK2.setOnClickListener {
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(6) //单位是ms
            val normalInputCKDialog = NormalInputCKDialog(this)
            normalInputCKDialog.onOkClickListener = object : NormalInputCKDialog.OnOkClickListener {
                override fun ok(ck: String, remark: String) {
                    if (TextUtils.isEmpty(ck)) {
                        Toast.makeText(this@MuchCkActivity, "CK为空，添加失败", Toast.LENGTH_SHORT).show()
                    } else {
                        CacheUtil.putString("ck2", ck)
                        Toast.makeText(this@MuchCkActivity, "CK3添加成功", Toast.LENGTH_SHORT).show()
                        UpdateTask.widgetUpdateDataUtil.updateWidget("ck2")
                        Log.i("====", "1")
                    }
                }
            }
            normalInputCKDialog.pop()
        }
        updateCK3.setOnClickListener {
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(6) //单位是ms
            val normalInputCKDialog = NormalInputCKDialog(this)
            normalInputCKDialog.onOkClickListener = object : NormalInputCKDialog.OnOkClickListener {
                override fun ok(ck: String, remark: String) {
                    if (TextUtils.isEmpty(ck)) {
                        Toast.makeText(this@MuchCkActivity, "CK为空，添加失败", Toast.LENGTH_SHORT).show()
                    } else {
                        CacheUtil.putString("ck3", ck)
                        Toast.makeText(this@MuchCkActivity, "CK4添加成功", Toast.LENGTH_SHORT).show()
                        UpdateTask.widgetUpdateDataUtil.updateWidget("ck3")
                        Log.i("====", "1")
                    }
                }
            }
            normalInputCKDialog.pop()
        }
        updateCK4.setOnClickListener {
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(6) //单位是ms
            val normalInputCKDialog = NormalInputCKDialog(this)
            normalInputCKDialog.onOkClickListener = object : NormalInputCKDialog.OnOkClickListener {
                override fun ok(ck: String, remark: String) {
                    if (TextUtils.isEmpty(ck)) {
                        Toast.makeText(this@MuchCkActivity, "CK为空，添加失败", Toast.LENGTH_SHORT).show()
                    } else {
                        CacheUtil.putString("ck4", ck)
                        Toast.makeText(this@MuchCkActivity, "CK5添加成功", Toast.LENGTH_SHORT).show()
                        UpdateTask.widgetUpdateDataUtil.updateWidget("ck4")
                        Log.i("====", "1")
                    }
                }
            }
            normalInputCKDialog.pop()
        }
        updateCK5.setOnClickListener {
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(6) //单位是ms
            val normalInputCKDialog = NormalInputCKDialog(this)
            normalInputCKDialog.onOkClickListener = object : NormalInputCKDialog.OnOkClickListener {
                override fun ok(ck: String, remark: String) {
                    if (TextUtils.isEmpty(ck)) {
                        Toast.makeText(this@MuchCkActivity, "CK为空，添加失败", Toast.LENGTH_SHORT).show()
                    } else {
                        CacheUtil.putString("ck5", ck)
                        Toast.makeText(this@MuchCkActivity, "CK6添加成功", Toast.LENGTH_SHORT).show()
                        UpdateTask.widgetUpdateDataUtil.updateWidget("ck5")
                        Log.i("====", "1")
                    }
                }
            }
            normalInputCKDialog.pop()
        }


    }
}
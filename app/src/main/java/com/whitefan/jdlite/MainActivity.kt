package com.whitefan.jdlite

import android.app.ProgressDialog
import android.content.Context
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.os.Vibrator
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloader
import com.whitefan.jdlite.activity.AboutActivity
import com.whitefan.jdlite.activity.MuchCkActivity
import com.whitefan.jdlite.activity.MyWebActivity
import com.whitefan.jdlite.activity.SettingActivity
import com.whitefan.jdlite.bean.SimpleFileDownloadListener
import com.whitefan.jdlite.bean.VersionBean
import com.whitefan.jdlite.dialog.NewStyleDialog
import com.whitefan.jdlite.util.*
import com.zhy.base.fileprovider.FileProvider7
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.include_title.*
import java.io.File


class MainActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setTitle("京豆小部件 Lite")
        back?.visibility = View.GONE

        setRightTitle("关于软件")

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i("====data", intent.getStringExtra("data").toString())
    }

    override fun initData() {
        checkAppUpdate()
        startUpdateService()
    }

    private fun startUpdateService() {

        if ("1" != CacheUtil.getString("startUpdateService")) {
            UpdateTask.updateAll()
        }
    }

    private fun checkAppUpdate() {
        HttpUtil.getAppVer(object : StringCallBack {
            override fun onSuccess(result: String) {
                try {
                    var gson = Gson()
                    val versionBean = gson.fromJson(result, VersionBean::class.java)
                    if (DeviceUtil.getAppVersionName().equals(versionBean.release)) {
                        Toast.makeText(this@MainActivity, "当前已是最新版本", Toast.LENGTH_SHORT).show()
                    } else {
                        if ("1" == versionBean.isUpdate) {
                            createDialog("更新日志", versionBean.content, "更新", object : NewStyleDialog.OnRightClickListener {
                                override fun rightClick() {
                                    downLoadApk(versionBean.content_url)
                                }
                            })
                        } else {
                            createDialog("更新日志", versionBean.content, "取消", "更新", object : NewStyleDialog.OnLeftClickListener {
                                override fun leftClick() {
                                    disMissDialog()
                                }
                            }, object : NewStyleDialog.OnRightClickListener {
                                override fun rightClick() {
                                    disMissDialog()
                                    downLoadApk(versionBean.content_url)
                                }
                            })
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {
            }

        })
    }

    private fun downLoadApk(contentUrl: String?) {
        downLoad(contentUrl)
    }

    private lateinit var pd: ProgressDialog


    private fun downLoad(contentUrl: String?) {
        if (TextUtils.isEmpty(contentUrl)) return

        pd = ProgressDialog(this)
        pd.setTitle("提示")
        pd.setMessage("软件版本更新中，请稍后...")
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL) //设置带进度条的

        pd.max = 100
        pd.setCancelable(false)
        pd.show()

        var pathParent = filesDir.path + "/downApk"
        var apkName = pathParent + System.currentTimeMillis() + ".apk"
        val file = File(pathParent)
        if (!file.exists()) {
            file.mkdirs()
        }

        FileDownloader.setup(this)
        FileDownloader.getImpl().create(contentUrl)
            .setPath(apkName)
            .setListener(object : SimpleFileDownloadListener() {
                override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    val per = soFarBytes / (totalBytes / 100)
                    pd.progress = per
                }

                override fun completed(task: BaseDownloadTask) {
                    pd.dismiss()
                    val file = File(apkName)
                    installApk(file)
                }
            }).start()

    }

    private fun installApk(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        FileProvider7.setIntentDataAndType(this, intent, "application/vnd.android.package-archive", file, true)
        startActivity(intent)
    }

    override fun setEvent() {
        setting.setOnClickListener {
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(10) //单位是ms
            Intent().also {
                it.setClass(this,SettingActivity::class.java)
                overridePendingTransition(R.anim.translate,R.anim.anim_action_main)
                startActivity(it)
            }
        }

        muchCK.setOnClickListener {
            val intent = Intent(this, MuchCkActivity::class.java)
            startActivity(intent)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(10) //单位是ms
        }

        about.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(10) //单位是ms
        }

        githublj.setOnClickListener {
            val intent = Intent(this, MyWebActivity::class.java)
            intent.putExtra("url", "https://github.com/baifan97/JingBeanAppWidget")
            intent.putExtra("title", "京豆小部件——GitHub")
            startActivity(intent)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(10)
        }

        loginJd.setOnClickListener {
            val intent = Intent(this, MyWebActivity::class.java)
            intent.putExtra("url", "https://plogin.m.jd.com/login/login")
            intent.putExtra("title", "京东网页版获取CK")
            startActivity(intent)
            val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(10)
        }

        rightTitle.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }


}
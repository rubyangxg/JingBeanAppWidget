package com.whitefan.jdlite.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.widget.Toast
import com.whitefan.jdlite.BaseActivity
import com.whitefan.jdlite.MyApplication
import com.whitefan.jdlite.R
import com.whitefan.jdlite.dialog.NewStyleDialog
import com.whitefan.jdlite.util.HttpUtil
import com.whitefan.jdlite.util.StringCallBack
import com.whitefan.jdlite.webView.CommonWebView
import kotlinx.android.synthetic.main.activity_web.*

class MyWebActivity : BaseActivity() {
    private var type: String? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_web
    }

    override fun initView() {
        type = intent.getStringExtra("type")
        val title = intent.getStringExtra("title")
        if (TextUtils.isEmpty(title)) {
            setTitle("网页在线获取京东CK")
        } else {
            setTitle(title.toString())
        }
        if ("1" == type) {
            tips.visibility = View.VISIBLE
        } else {
            tips.visibility = View.GONE
        }
    }

    override fun initData() {
        removeCookie(this)
        var url = intent.getStringExtra("url")
        mCommonWebView.loadUrl(url)
    }

    override fun setEvent() {
        mCommonWebView.setOnGetCookieListener(object : CommonWebView.OnGetCookie {
            override fun get(ck: String) {
                dealCk(ck)
            }
        })
    }

    private fun dealCk(ck: String) {
        createDialog("已获取到CK", ck, "取消", "复制", object : NewStyleDialog.OnLeftClickListener {
            override fun leftClick() {
                disMissDialog()
            }
        }, object : NewStyleDialog.OnRightClickListener {
            override fun rightClick() {
                copyClipboard(ck)
                disMissDialog()
                finish()
            }
        })
    }



    private fun removeCookie(context: Context) {
        try {
            CookieSyncManager.createInstance(context)
            val cookieManager = CookieManager.getInstance()
            cookieManager.removeAllCookie()
            CookieSyncManager.getInstance().sync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun copyClipboard(content: String?) {
        try {
            val myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val primaryClip = ClipData.newPlainText("text", content)
            myClipboard.setPrimaryClip(primaryClip)
            Toast.makeText(MyApplication.mInstance, "已复制CK到粘贴板", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
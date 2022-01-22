package com.whitefan.jdlite.webView

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.RelativeLayout
import com.whitefan.jdlite.R
import com.whitefan.jdlite.util.dmToPx


class CommonWebView : RelativeLayout {
    private lateinit var webView: WebView
    private lateinit var webProgress: WebProgress
    private var lastProgress = -1
    var isGet = true

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    var getCookie: OnGetCookie? = null

    fun setOnGetCookieListener(onGetCookie: OnGetCookie) {
        getCookie = onGetCookie
    }

    private fun initView() {
        webView = WebView(context)
        val webViewParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        webView.layoutParams = webViewParams
        webView.overScrollMode = View.OVER_SCROLL_NEVER
        initWebView()
        addView(webView)

        webProgress = WebProgress(context)
        val webProgressParams = LayoutParams(LayoutParams.MATCH_PARENT, R.dimen.dp_2.dmToPx())
        webProgress.layoutParams = webProgressParams
//        webProgress.setColor("#4fd922")
        webProgress.setColor("#ff65c7ff", "#ff8174f3")
//        webProgress.setColor("#ff8174f3", "#ff65c7ff")
        webProgress.visibility = View.GONE

        addView(webProgress)
    }

    private fun initWebView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.defaultTextEncodingName = "UTF-8"
        webSettings.domStorageEnabled = true
        webSettings.blockNetworkImage = false
        webSettings.mediaPlaybackRequiresUserGesture = false
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportZoom(true)
        webSettings.allowFileAccess = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.allowFileAccessFromFileURLs = false
        webSettings.allowUniversalAccessFromFileURLs = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        val ua = webSettings.userAgentString
        webSettings.databaseEnabled = true
        webSettings.setGeolocationEnabled(true)

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                val scheme = request.url.scheme
                Log.i("CommonWebView", scheme + ":" + request.url)
                if (TextUtils.isEmpty(scheme)) return false

                if (scheme == "tel") {
                    val intent = Intent(Intent.ACTION_VIEW, request.url)
                    context.startActivity(intent)
                } else if (scheme == "smsto") {
                    val intentSMS = Intent(Intent.ACTION_SENDTO, request.url)
                    context.startActivity(intentSMS)
                } else if (scheme == "sms") {
                    val intentSMS = Intent(Intent.ACTION_SENDTO, request.url)
                    context.startActivity(intentSMS)
                } else if (scheme == "http" || scheme == "https" || scheme == "ftp") {
                    webProgress.show()
                    return false
                } else {
//                    openApp(context, request.url)
                    return true
                }

                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                val cookieManager = CookieManager.getInstance()
                var CookieStr = cookieManager.getCookie(url)
                if (TextUtils.isEmpty(CookieStr)) return
                if (CookieStr.contains("pt_key") && CookieStr.contains("pt_pin") && isGet) {
                    CookieStr = CookieStr.substring(CookieStr.indexOf("pt_key"))
                    val key = CookieStr.substring(0, CookieStr.indexOf("pt_pin"))
                    CookieStr = CookieStr.substring(CookieStr.indexOf("pt_pin"))
                    val pin = CookieStr.substring(0, CookieStr.indexOf(";") + 1)
                    getCookie?.get(key + pin)
                    isGet = false
                }

                super.onPageFinished(view, url)
                Log.i("CommonWebView", "onPageFinished")
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.i("CommonWebView", "onPageStarted")
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (lastProgress != newProgress) {
                    webProgress.setProgress(newProgress)
                    lastProgress = newProgress
                    Log.i("CommonWebView", newProgress.toString())
                }
            }

            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }
        }
        webView.setDownloadListener(object : DownloadListener {
            override fun onDownloadStart(url: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long) {
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
        })
    }

    private fun openApp(context: Context, uri: Uri): Boolean {
        try {
            val packageManager = context.packageManager
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val activities = packageManager.queryIntentActivities(intent, 0)
            val isValid = activities.isNotEmpty()
            return if (isValid) {
                context.startActivity(intent)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun loadUrl(url: String?) {
        if (TextUtils.isEmpty(url)) {
            return
        } else {
            webView.loadUrl(url!!)
            webProgress.show()
        }
    }

    interface OnGetCookie {
        fun get(ck: String)
    }
}
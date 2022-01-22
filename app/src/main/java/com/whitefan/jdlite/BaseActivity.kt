package com.whitefan.jdlite

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.whitefan.jdlite.dialog.NewStyleDialog
import com.whitefan.jdlite.util.ActivityCollector.addActivity
import com.whitefan.jdlite.util.ActivityCollector.removeActivity

/**
 * Created by Administrator on 2018/1/4.
 */
abstract class BaseActivity : AppCompatActivity() {
    private var barView: View? = null
    private var titleTv: TextView? = null
    private var rightTitleTv: TextView? = null
    public var back: LinearLayout? = null
    private lateinit var currentActivity: FragmentActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        addActivity(this)
        barView = findViewById(R.id.barView)
        titleTv = findViewById(R.id.title)
        rightTitleTv = findViewById(R.id.rightTitle)
        back = findViewById(R.id.back)
        currentActivity = this
        immersionBar {
            statusBarView(barView)
            navigationBarColorTransform(R.color.navigationBar)
            navigationBarAlpha(1.0f)
            navigationBarDarkIcon(true)
            statusBarDarkFont(true)
            init()
        }

        back?.setOnClickListener {
            finish()
        }

        initView()
        initData()
        setEvent()
    }

    protected abstract fun setLayoutId(): Int
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun setEvent()

    fun setTitle(str: String) {
        titleTv?.text = str
    }

    fun setRightTitle(str: String) {
        rightTitleTv?.visibility = View.VISIBLE
        rightTitleTv?.text = str
    }


    protected var dialog: NewStyleDialog? = null

    open fun createDialogTip(content: String?) {
        createDialog("温馨提示", content, "确定", null)
    }

    open fun createDialogTip(content: String?, onRightClickListener: NewStyleDialog.OnRightClickListener?) {
        createDialog("温馨提示", content, "确定", onRightClickListener)
    }


    open fun createDialog(
        title: String?,
        content: String?,
        rightBtn: String?,
        onRightClickListener: NewStyleDialog.OnRightClickListener?
    ) {
        createDialog(title, content, "", rightBtn, null, onRightClickListener)
    }

    open fun createDialog(
        title: String?,
        content: String?,
        leftBtn: String?,
        rightBtn: String?,
        onRightClickListener: NewStyleDialog.OnRightClickListener?
    ) {
        createDialog(title, content, leftBtn, rightBtn, null, onRightClickListener)
    }

    open fun createDialog(
        title: String?,
        content: String?,
        leftBtn: String?,
        rightBtn: String?,
        onLeftClickListener: NewStyleDialog.OnLeftClickListener?,
        onRightClickListener: NewStyleDialog.OnRightClickListener?
    ) {
        if (dialog != null) {
            disMissDialog()
        }
        dialog = NewStyleDialog(currentActivity, title, content, leftBtn, rightBtn)
        dialog?.apply {
            setCancelable(false)
            setLeftClickListener(onLeftClickListener)
            setRightClickListener(onRightClickListener)
            pop()
        }
    }

    open fun disMissDialog() {
        if (dialog != null && dialog!!.isShowing()) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeActivity(this)
    }

}
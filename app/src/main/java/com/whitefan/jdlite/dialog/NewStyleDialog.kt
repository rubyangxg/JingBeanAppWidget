package com.whitefan.jdlite.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.whitefan.jdlite.R

class NewStyleDialog : Dialog {
    private lateinit var textView_title: TextView
    private lateinit var textView_content: TextView
    private lateinit var textView_left: TextView
    private lateinit var textView_right: TextView
    var titleText: String? = null
    var contentText: String? = null
    var leftText: String? = null
    var rightText: String? = null

    var onLeftClickListener: OnLeftClickListener? = null
    var onRightClickListener: OnRightClickListener? = null
    var onSureClickListener: OnSureClickListener? = null
    var onKeyClickListener: OnKeyClickListener? = null

    fun setLeftClickListener(onLeftClickListener: OnLeftClickListener?) {
        this.onLeftClickListener = onLeftClickListener
    }

    fun setRightClickListener(onRightClickListener: OnRightClickListener?) {
        this.onRightClickListener = onRightClickListener
    }

    fun setKeyClickListener(onKeyClickListener: OnKeyClickListener?) {
        this.onKeyClickListener = onKeyClickListener
    }

    fun setSureClickListener(onSureClickListener: OnSureClickListener?) {
        this.onSureClickListener = onSureClickListener
    }

    interface OnLeftClickListener {
        fun leftClick()
    }

    interface OnRightClickListener {
        fun rightClick()
    }

    interface OnSureClickListener {
        fun sureClick()
    }

    interface OnKeyClickListener {
        fun keyClick()
    }

    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, titleText: String?, contentText: String?, rightText: String?) : super(context!!) {
        this.titleText = titleText
        this.contentText = contentText
        this.rightText = rightText
        initView()
    }

    constructor(context: Context?, titleText: String?, contentText: String?, leftText: String?, rightText: String?) : super(context!!) {
        this.titleText = titleText
        this.contentText = contentText
        this.leftText = leftText
        this.rightText = rightText
        initView()
    }

    fun initView(): NewStyleDialog {
        setContentView(R.layout.dialog_layout_style1)
        textView_left = findViewById(R.id.dialog_btn_left)
        textView_right = findViewById(R.id.dialog_btn_right)
        textView_title = findViewById(R.id.dialog_title)
        textView_content = findViewById(R.id.dialog_content)
        val viewLine = findViewById<View>(R.id.view_line)
        val viewTitleZhanwei = findViewById<View>(R.id.title_zhanwei)
        if (!TextUtils.isEmpty(titleText)) {
            textView_title.setVisibility(View.VISIBLE)
            viewTitleZhanwei.visibility = View.GONE
            textView_title.setText(titleText)
        } else {
            textView_title.setVisibility(View.GONE)
            viewTitleZhanwei.visibility = View.VISIBLE
        }
        if (!TextUtils.isEmpty(contentText)) {
            textView_content.setText(contentText)
        }
        if (!TextUtils.isEmpty(leftText)) {
            //两个按钮都存在
            textView_left.setVisibility(View.VISIBLE)
            viewLine.visibility = View.VISIBLE
            textView_left.setText(leftText)
            textView_left.setBackgroundResource(R.drawable.dialogstyle1_two_left_selector)
            textView_right.setBackgroundResource(R.drawable.dialogstyle1_two_right_selector)
        } else {
            //只有一个按钮的情况
            viewLine.visibility = View.GONE
            textView_left.visibility = View.GONE
            textView_right.setBackgroundResource(R.drawable.dialogstyle1_one_sure_selector)
        }
        if (!TextUtils.isEmpty(rightText)) {
            textView_right.setText(rightText)
        }
        val context = context
        val divierId = context.resources.getIdentifier("android:id/titleDivider", null, null)
        val divider = findViewById<View>(divierId)
        divider?.setBackgroundColor(Color.TRANSPARENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(context.resources.displayMetrics.widthPixels * 5 / 6, LinearLayout.LayoutParams.WRAP_CONTENT)
        textView_left.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                onLeftClickListener?.leftClick()
            }
        })
        textView_right.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                onRightClickListener?.rightClick()
                onSureClickListener?.sureClick()
            }
        })
        return this
    }

    fun pop() {
        if (!isShowing) {
            try {
                show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun show() {
        try {
            super.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setKey(key: String?) {
        setKey(key, false)
    }

    fun setKey(key: String?, needClick: Boolean) {
        val builder = SpannableStringBuilder(contentText)
        val startIndex = contentText!!.indexOf(key!!) //截取文字开始的下标
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                if (onKeyClickListener != null && needClick) {
                    onKeyClickListener!!.keyClick()
                }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#FF3390E8") //设置文字颜色
                if (needClick) {
                    ds.isUnderlineText = true //设置下划线//根据需要添加
                }
            }
        }, startIndex, startIndex + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView_content.highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明，否则会一直出现高亮
        textView_content.text = builder
        textView_content.movementMethod = LinkMovementMethod.getInstance()
    }

    companion object {
        private var dialog: NewStyleDialog? = null

        @JvmStatic
        fun createDialog(
            context: Context?, title: String?, content: String?, leftbtn: String?, rightBtn: String?,
            onLeftClickListener: OnLeftClickListener?,
            onRightClickListener: OnRightClickListener?
        ) {
            if (dialog != null) {
                disMissDialog()
            }
            dialog = NewStyleDialog(context, title, content, leftbtn, rightBtn)
            dialog!!.setCancelable(false)
            dialog!!.onLeftClickListener = onLeftClickListener
            dialog!!.onRightClickListener = onRightClickListener
            dialog!!.pop()
        }

        @JvmStatic
        fun createDialog(
            context: Context?, title: String?, content: String?, sureBtn: String?,
            onSureClickListener: OnSureClickListener?
        ) {
            if (dialog != null) {
                disMissDialog()
            }
            dialog = NewStyleDialog(context, title, content, "", sureBtn)
            dialog!!.setCancelable(false)
            dialog!!.onSureClickListener = onSureClickListener
            dialog!!.pop()
        }

        fun disMissDialog() {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
                dialog = null
            }
        }
    }
}
package com.whitefan.jdlite.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.whitefan.jdlite.R

class MenuDialog(var mActivity: Activity, private var dataList: ArrayList<String>, var itemClick: (key: String) -> Unit) : Dialog(mActivity!!) {
    private lateinit var mRecyclerView: RecyclerView

    init {
        initView()
    }

    fun initView(): MenuDialog {
        setContentView(R.layout.dialog_layout_style2)
        mRecyclerView = findViewById(R.id.mRecyclerView)
        val divierId = context.resources.getIdentifier("android:id/titleDivider", null, null)
        val divider = findViewById<View>(divierId)
        divider?.setBackgroundColor(Color.TRANSPARENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(context.resources.displayMetrics.widthPixels * 5 / 6, LinearLayout.LayoutParams.WRAP_CONTENT)

        var adapter = MyMenuDialogAdapter(mActivity, itemClick)
        adapter.dataList = dataList
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = adapter
        adapter.onItemClickListener = object : MyMenuDialogAdapter.OnItemClickListener {
            override fun click() {
                dismiss()
            }
        }
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
}
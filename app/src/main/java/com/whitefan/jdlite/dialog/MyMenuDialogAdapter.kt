package com.whitefan.jdlite.dialog

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.whitefan.jdlite.R


class MyMenuDialogAdapter(private val mActivity: Activity, var itemClick: (key: String) -> Unit) : RecyclerView.Adapter<MyMenuDialogAdapter.MenuItem>() {
    var dataList: ArrayList<String>? = null
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMenuDialogAdapter.MenuItem {
        return MenuItem(LayoutInflater.from(mActivity).inflate(R.layout.menu_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyMenuDialogAdapter.MenuItem, position: Int) {
        holder.title.text = dataList?.get(position)

        holder.itemView.setOnClickListener {
            itemClick(dataList?.get(holder.adapterPosition)!!)
            onItemClickListener?.click()
        }
    }

    override fun getItemCount(): Int {
        return if (dataList == null) {
            0
        } else {
            dataList!!.size
        }
    }

    inner class MenuItem(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
    }

    interface OnItemClickListener {
        fun click()
    }
}
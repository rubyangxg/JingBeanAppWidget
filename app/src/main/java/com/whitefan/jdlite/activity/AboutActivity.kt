package com.whitefan.jdlite.activity

import android.content.Context
import com.whitefan.jdlite.BaseActivity
import com.whitefan.jdlite.R
import kotlinx.android.synthetic.main.activity_about.*
import android.content.Intent
import android.net.Uri
import android.os.Vibrator


class AboutActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun initView() {
        setTitle("关于软件")
    }

    override fun initData() {
    }

    override fun setEvent() {


        addQQGroup.setOnClickListener {
            joinQQGroup("KI_oHmcuNm_9xR93cnx-NRbvz2-TSl1j")
        }


    }

    fun joinQQGroup(key: String): Boolean {
        val intent = Intent()
        intent.data =
            Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D$key")
        return try {
            startActivity(intent)
            true
        } catch (e: java.lang.Exception) {
            false
        }
    }

}
package com.whitefan.jdlite.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.whitefan.jdlite.MainActivity
import com.whitefan.jdlite.MyApplication
import com.whitefan.jdlite.bean.JingDouBean
import com.whitefan.jdlite.bean.RedPacket
import com.whitefan.jdlite.bean.UserBean
import com.whitefan.jdlite.bean.VersionBean
import com.whitefan.jdlite.util.*
import com.whitefan.jdlite.util.TimeUtil.getCurrentData
import com.whitefan.jdlite.util.TimeUtil.getCurrentHH
import com.whitefan.jdlite.util.TimeUtil.parseTime
import org.json.JSONObject
import java.lang.Exception

import android.graphics.Color
import com.whitefan.jdlite.R
import java.util.HashMap
import com.bumptech.glide.request.RequestOptions
import com.whitefan.jdlite.EmptyActivity
import java.math.BigDecimal
import java.text.DecimalFormat



class WidgetUpdateDataUtil_2 {
    private lateinit var remoteViews: RemoteViews
    private var gson = Gson()
    private var todayTime: Long = 0
    private var ago4Time: Long = 0
    lateinit var thisKey: String
    private var userBean = UserBean()
    private val keyList = HashMap<String, Int>()
    private lateinit var key1Ago: String
    private lateinit var key2Ago: String
    private lateinit var key3Ago: String
    private lateinit var key4Ago: String



    @Synchronized
    fun updateWidget(key: String) {
        thisKey = key
        val str = HttpUtil.getCK(thisKey)
        if (TextUtils.isEmpty(str)) return

        if (TimeUtil.isFastClick()) {
            Log.i("====", "isFastClick")
            return
        }

        userBean.todayBean = 0;
        userBean.page = 1
        userBean.ago1Bean = 0;

        todayTime = TimeUtil.getTodayMillis(0)
        ago4Time = TimeUtil.getTodayMillis(-4)

        key1Ago = TimeUtil.getYesterDay(-1) + CacheUtil.getCKPtPin(thisKey)
        key2Ago = TimeUtil.getYesterDay(-2) + CacheUtil.getCKPtPin(thisKey)
        key3Ago = TimeUtil.getYesterDay(-3) + CacheUtil.getCKPtPin(thisKey)
        key4Ago = TimeUtil.getYesterDay(-4) + CacheUtil.getCKPtPin(thisKey)

        keyList.clear()

        remoteViews = RemoteViews(MyApplication.mInstance.packageName, R.layout.widges_layout2)
        remoteViews.setViewPadding(R.id.rootParent, 0, 0, 0, 0)
        pullWidget()

        checkUpdate()

        getUserInfo()
        getUserJdFruit()

        getUserInfo1()

        //在这里就进行判断 是否需要取全部数据 还是只取今天的数据
        val ago1 = CacheUtil.getString(key1Ago)
        val ago2 = CacheUtil.getString(key2Ago)
        val ago3 = CacheUtil.getString(key3Ago)
        val ago4 = CacheUtil.getString(key4Ago)
        if (TextUtils.isEmpty(ago1) || TextUtils.isEmpty(ago2) || TextUtils.isEmpty(ago3) || TextUtils.isEmpty(ago4)) {
            Log.i("====", "没有前几天缓存数据")
            getJingBeanData(false)
        } else {
            Log.i("====", "有前几天缓存数据")
            getJingBeanData(true)
        }

        getRedPackge()
    }

    private fun checkUpdate() {
        HttpUtil.getAppVer(object : StringCallBack {
            override fun onSuccess(result: String) {
                try {
                    var gson = Gson()
                    val versionBean = gson.fromJson(result, VersionBean::class.java)
                    if (DeviceUtil.getAppVersionName().equals(versionBean.release)) {
                        userBean.updateTips = ""
                    } else {
                        userBean.updateTips = versionBean.widgetTip
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {
            }

        })
    }

    private fun getRedPackge() {
        HttpUtil.getRedPack(thisKey, "https://m.jingxi.com/user/info/QueryUserRedEnvelopesV2?type=1&orgFlag=JD_PinGou_New&page=1&cashRedType=1&redBalanceFlag=1&channel=1&_=" + System.currentTimeMillis() + "&sceneval=2&g_login_type=1&g_ty=ls", object : StringCallBack {
            override fun onSuccess(result: String) {

                try {
                    userBean.jxRed = BigDecimal.ZERO
                    userBean.jdRed = BigDecimal.ZERO
                    userBean.jsRed = BigDecimal.ZERO
                    userBean.jxRedGQ = BigDecimal.ZERO
                    userBean.jsRedGQ = BigDecimal.ZERO
                    userBean.jdRedGQ = BigDecimal.ZERO
                    val redPacket = gson.fromJson(result, RedPacket::class.java)
                    userBean.hb = redPacket.data.balance
                    userBean.gqhb = redPacket.data.expiredBalance
                    userBean.countdownTime = redPacket.data.countdownTime / 60 / 60
                    val redList = redPacket.data.useRedInfo.redList
                    if (redList != null && redList.size > 0) {
                        val tomorrow = TimeUtil.getTodayMillis(1) / 1000
                        for (red in redList) {
                            when {
                                red.orgLimitStr.contains("京喜") -> {
                                    userBean.jxRed = userBean.jxRed.add(red.balance)
                                    if (red.endTime < tomorrow) {
                                        userBean.jxRedGQ = userBean.jxRedGQ.add(red.balance)
                                    }
                                }
                                red.orgLimitStr.contains("极速版") -> {
                                    userBean.jsRed = userBean.jsRed.add(red.balance)
                                    if (red.endTime < tomorrow) {
                                        userBean.jsRedGQ = userBean.jsRedGQ.add(red.balance)
                                    }
                                }
                                red.orgLimitStr.contains("京东健康") -> {

                                }
                                else -> {
                                    userBean.jdRed = userBean.jdRed.add(red.balance)
                                    if (red.endTime < tomorrow) {
                                        userBean.jdRedGQ = userBean.jdRedGQ.add(red.balance)
                                    }
                                }
                            }
                        }
                    }
                    setData()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {
            }
        })
    }

    private fun getUserInfo1() {
        HttpUtil.getUserInfo1(thisKey, object : StringCallBack {
            override fun onSuccess(result: String) {
                try {
                    val job = JSONObject(result)
                    userBean.jxiang = job.optJSONObject("user").optString("uclass").replace("京享值", "")
                    userBean.beanNum = job.optJSONObject("user").optString("jingBean")
                    userBean.nickName = job.optJSONObject("user").optString("petName")
                    setData()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {

            }
        })
    }

    private fun getUserInfo() {
        HttpUtil.getUserInfo(thisKey, object : StringCallBack {
            override fun onSuccess(result: String) {
                try {
                    val job = JSONObject(result)
                    try {
                        userBean.nickName = job.optJSONObject("data").optJSONObject("userInfo").optJSONObject("baseInfo").optString("nickname")
                        userBean.userLevel = job.optJSONObject("data").optJSONObject("userInfo").optJSONObject("baseInfo").optString("userLevel")
                        userBean.levelName = job.optJSONObject("data").optJSONObject("userInfo").optJSONObject("baseInfo").optString("levelName")
                        userBean.headImageUrl = job.optJSONObject("data").optJSONObject("userInfo").optJSONObject("baseInfo").optString("headImageUrl")
                        userBean.isPlusVip = job.optJSONObject("data").optJSONObject("userInfo").optString("isPlusVip")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    try {
                        userBean.beanNum = job.optJSONObject("data").optJSONObject("assetInfo").optString("beanNum")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    setData()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {
            }

        })
    }

    private fun getUserJdFruit() {
        HttpUtil.getUserJdFruit(thisKey, object : StringCallBack {
            override fun onSuccess(result: String) {
                try {
                    val job = JSONObject(result)
                    try {
                        userBean.farmName = job.optJSONObject("farmUserPro").optString("name")
                        userBean.treeEnergy = job.optJSONObject("farmUserPro").optString("treeEnergy")
                        userBean.treeTotalEnergy = job.optJSONObject("farmUserPro").optString("treeTotalEnergy")
                        userBean.treeImageUrl = job.optJSONObject("farmUserPro").optString("goodsImage")

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    try {
                        userBean.farmName = job.optJSONObject("farmUserPro").optString("name")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    setData()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {
            }

        })
    }


    @Synchronized
    private fun getJingBeanData(isToday: Boolean) {
        Log.i("====page", userBean.page.toString())
        HttpUtil.getJD(thisKey, userBean.page, object : StringCallBack {
            override fun onSuccess(result: String) {
                Log.i("====", "数据:$result")
                try {
                    if (TextUtils.isEmpty(result)) return
                    val jingDouBean = gson.fromJson(result, JingDouBean::class.java)
                    val dataList = jingDouBean.detailList

                    var isFinish = false
                    for (i in dataList.indices) {
                        val detail = dataList[i]
                        val beanDay = parseTime(detail.date)!!
                        val dayName = detail.date.split(" ")[0] + CacheUtil.getCKPtPin(thisKey)
                        if (keyList[dayName] == null) {
                            if (detail.amount > 0 && !detail.eventMassage.contains("退还")) {
                                keyList[dayName] = detail.amount
                            }
                        } else {
                            if (detail.amount > 0 && !detail.eventMassage.contains("退还")) {
                                keyList[dayName] = keyList[dayName]!! + detail.amount
                            }
                        }

                        //取前四天缓存 如果存在前四天缓存 则只用取最新的数据 否则还是要请求网络
                        isFinish = if (isToday) {
                            beanDay < todayTime
                        } else {
                            beanDay < ago4Time
                        }
                    }

                    if (dataList.size == 0) {
                        isFinish = true
                    }

                    if (isFinish) {
                        if (isToday) {

                        } else {
                            if (keyList[key1Ago] == null) {
                                CacheUtil.putString(key1Ago, "0")
                            } else {
                                CacheUtil.putString(key1Ago, keyList[key1Ago].toString())
                            }

                            if (keyList[key2Ago] == null) {
                                CacheUtil.putString(key2Ago, "0")
                            } else {
                                CacheUtil.putString(key2Ago, keyList[key2Ago].toString())
                            }

                            if (keyList[key3Ago] == null) {
                                CacheUtil.putString(key3Ago, "0")
                            } else {
                                CacheUtil.putString(key3Ago, keyList[key3Ago].toString())
                            }

                            if (keyList[key4Ago] == null) {
                                CacheUtil.putString(key4Ago, "0")
                            } else {
                                CacheUtil.putString(key4Ago, keyList[key4Ago].toString())
                            }
                        }


                        if (keyList[TimeUtil.getYesterDay(0) + CacheUtil.getCKPtPin(thisKey)] == null) {
                            userBean.todayBean = 0
                        } else {
                            userBean.todayBean = keyList[TimeUtil.getYesterDay(0) + CacheUtil.getCKPtPin(thisKey)]!!
                        }
                        userBean.ago1Bean = CacheUtil.getString(key1Ago)!!.toInt()
                        userBean.ago2Bean = CacheUtil.getString(key2Ago)!!.toInt()
                        userBean.ago3Bean = CacheUtil.getString(key3Ago)!!.toInt()
                        userBean.ago4Bean = CacheUtil.getString(key4Ago)!!.toInt()
                        setData()
                    } else {
                        userBean.page++
                        getJingBeanData(isToday)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFail() {
            }
        })
    }

    private fun setData() {
        remoteViews.setViewVisibility(R.id.botParent, View.GONE)


        if ("1" == CacheUtil.getString("hideTips")) {
            remoteViews.setViewVisibility(R.id.botParent, View.GONE)
        } else {
            remoteViews.setViewVisibility(R.id.botParent, View.VISIBLE)
        }

        if ("1" == CacheUtil.getString("hideNichen")) {
            remoteViews.setTextViewText(R.id.nickName, "***")
        } else {
            remoteViews.setTextViewText(R.id.nickName, userBean.nickName)
        }

        if ("1" == userBean.isPlusVip) {
            remoteViews.setViewVisibility(R.id.plusIcon, View.VISIBLE)
        } else {
            remoteViews.setViewVisibility(R.id.plusIcon, View.GONE)
        }

        if (TextUtils.isEmpty(userBean.updateTips)) {
            remoteViews.setViewVisibility(R.id.haveNewVersion, View.GONE)
        } else {
            remoteViews.setViewVisibility(R.id.haveNewVersion, View.VISIBLE)
            remoteViews.setTextViewText(R.id.haveNewVersion, userBean.updateTips)
        }

        val paddingType = CacheUtil.getString("paddingType")
        if (TextUtils.isEmpty(paddingType) || "15dp" == paddingType) {
            remoteViews.setViewPadding(R.id.rootParent, 0, 0, 0, 0)
        } else if ("无边距" == paddingType) {
            remoteViews.setViewPadding(R.id.rootParent, 0, 0, 0, 0)
        } else if ("5dp" == paddingType) {
            remoteViews.setViewPadding(R.id.rootParent, 0, 0, 0, 0)
        } else if ("10dp" == paddingType) {
            remoteViews.setViewPadding(R.id.rootParent, 0, 0, 0, 0)
        } else if ("20dp" == paddingType) {
            remoteViews.setViewPadding(R.id.rootParent, 0, 0, 0, 0)
        }

        //used: 16663552, max: 15163200
        val cacheBac1 = CacheUtil.getString(thisKey + "_back")
        if (TextUtils.isEmpty(cacheBac1)) {
            var designColor2 = CacheUtil.getString("designColor2")
            if (TextUtils.isEmpty(designColor2)) {
                designColor2 = "#FFFFFF"
            }
            val bac = BitmapUtil.getColorBitmapLow(designColor2)
            remoteViews.setImageViewBitmap(R.id.background, BitmapUtil.bimapRound(bac, 20f))
        } else {
            val options = RequestOptions()
                .override(200, 200)
            Glide.with(MyApplication.mInstance)
                .load(cacheBac1)
                .apply(options)
                .into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        val bac = BitmapUtil.drawableToBitmap(resource)
                        Log.i("====byteCount", bac.byteCount.toString())
                        remoteViews.setImageViewBitmap(R.id.background, BitmapUtil.bimapRound(bac, 20f))
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                    }
                })
        }


        val colorSwitch = CacheUtil.getString("colorSwitch")
        if ("1" == colorSwitch) {
            remoteViews.setTextColor(R.id.nickName, ColorUtil.transColor("#FF000000"))
            remoteViews.setTextColor(R.id.jingXiang, ColorUtil.transColor("#FF000000"))
            remoteViews.setTextColor(R.id.beanNum, Color.parseColor("#FF4500"))
            remoteViews.setTextColor(R.id.todayBean, ColorUtil.transColor("#008000"))
            remoteViews.setTextColor(R.id.yesterDayTip, ColorUtil.transColor("#333333"))
            remoteViews.setTextColor(R.id.todayTip, ColorUtil.transColor("#333333"))
            remoteViews.setTextColor(R.id.oneAgoBeanNum, ColorUtil.transColor("#FF000000"))
            remoteViews.setTextColor(R.id.todayBeanNum, ColorUtil.transColor("#FF000000"))

            remoteViews.setTextColor(R.id.title1, ColorUtil.transColor("#FF000000"))
            remoteViews.setTextColor(R.id.guoquHb1, ColorUtil.transColor("#333333"))
            remoteViews.setTextColor(R.id.hongbao1, Color.parseColor("#FF4500"))


            remoteViews.setTextColor(R.id.tips, ColorUtil.transColor("#333333"))
            remoteViews.setTextColor(R.id.updateTime, ColorUtil.transColor("#333333"))

        } else {
            remoteViews.setTextColor(R.id.nickName, Color.parseColor("#FF000000"))
            remoteViews.setTextColor(R.id.jingXiang, Color.parseColor("#FF000000"))
            remoteViews.setTextColor(R.id.beanNum, Color.parseColor("#FF0000"))
            remoteViews.setTextColor(R.id.todayBean, Color.parseColor("#008000"))
            remoteViews.setTextColor(R.id.yesterDayTip, Color.parseColor("#333333"))
            remoteViews.setTextColor(R.id.todayTip, Color.parseColor("#333333"))
            remoteViews.setTextColor(R.id.oneAgoBeanNum, Color.parseColor("#FF000000"))
            remoteViews.setTextColor(R.id.todayBeanNum, Color.parseColor("#FF000000"))

            remoteViews.setTextColor(R.id.title1, Color.parseColor("#FF000000"))
            remoteViews.setTextColor(R.id.guoquHb1, Color.parseColor("#333333"))
            remoteViews.setTextColor(R.id.hongbao1, Color.parseColor("#FF0000"))

            remoteViews.setTextColor(R.id.tips, Color.parseColor("#333333"))
            remoteViews.setTextColor(R.id.updateTime, Color.parseColor("#333333"))

        }



        val code = when (thisKey) {
            "ck" -> {
                0
            }
            "ck1" -> {
                1
            }
            "ck2" -> {
                2
            }
            "ck3" -> {
                3
            }
            "ck4" -> {
                4
            }
            "ck5" -> {
                5
            }
            else -> {
                -1
            }
        }

        val cleatInt = Intent(MyApplication.mInstance, EmptyActivity::class.java)
        cleatInt.putExtra("data", thisKey)
        cleatInt.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val clearIntent = PendingIntent.getActivity(MyApplication.mInstance, code, cleatInt, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.headImg, clearIntent)

        val cleatInt2 = Intent(MyApplication.mInstance, MainActivity::class.java)
        cleatInt2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val clearIntent2 = PendingIntent.getActivity(MyApplication.mInstance, 10, cleatInt2, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews.setOnClickPendingIntent(R.id.rightContent, clearIntent2)

        remoteViews.setTextViewText(R.id.beanNum, userBean.beanNum)
        remoteViews.setTextViewText(R.id.todayBean, "+" + userBean.todayBean)
        remoteViews.setTextViewText(R.id.updateTime, "数据更新于:" + getCurrentData())
        remoteViews.setTextViewText(R.id.treeEnergy, userBean.treeEnergy)
        remoteViews.setTextViewText(R.id.farmName, userBean.farmName)
        remoteViews.setTextViewText(R.id.treeTotalEnergy, userBean.treeTotalEnergy)

        remoteViews.setTextViewText(R.id.hongbao1, userBean.hb)

        val df = DecimalFormat("####.00")
        remoteViews.setTextViewText(R.id.hongbao2, String.format("%.2f", userBean.jxRed))
        remoteViews.setTextViewText(R.id.hongbao3, String.format("%.2f", userBean.jdRed))
        remoteViews.setTextViewText(R.id.hongbao4, String.format("%.2f", userBean.jsRed))

        remoteViews.setTextViewText(R.id.guoquHb2, "今日过期:" + String.format("%.2f", userBean.jxRedGQ))
        remoteViews.setTextViewText(R.id.guoquHb3, "今日过期:" + String.format("%.2f", userBean.jdRedGQ))
        remoteViews.setTextViewText(R.id.guoquHb4, "今日过期:" + String.format("%.2f", userBean.jsRedGQ))
        val showType = CacheUtil.getString("douShowType")
        Log.i("====showType", showType.toString())
        if (TextUtils.isEmpty(showType) || "文字展示" == showType) {
            remoteViews.setViewVisibility(R.id.normalShow, View.VISIBLE)
            remoteViews.setViewVisibility(R.id.zhuShow, View.GONE)

            remoteViews.setTextViewText(R.id.todayBeanNum, userBean.todayBean.toString())
            remoteViews.setTextViewText(R.id.oneAgoBeanNum, userBean.ago1Bean.toString())
        } else if ("柱状图展示" == showType) {
            remoteViews.setViewVisibility(R.id.normalShow, View.VISIBLE)
            remoteViews.setViewVisibility(R.id.zhuShow, View.GONE)

            remoteViews.setTextViewText(R.id.todayBeanNum, userBean.todayBean.toString())
            remoteViews.setTextViewText(R.id.oneAgoBeanNum, userBean.ago1Bean.toString())

        }

        val showType2 = CacheUtil.getString("douShowType2")
        Log.i("====showType2", showType2.toString())
        if (TextUtils.isEmpty(showType2) || "京豆展示" == showType2) {
            remoteViews.setViewVisibility(R.id.normalShow_2, View.VISIBLE)
            remoteViews.setViewVisibility(R.id.farmShow_2, View.GONE)

            remoteViews.setTextViewText(R.id.todayBeanNum, userBean.todayBean.toString())
            remoteViews.setTextViewText(R.id.oneAgoBeanNum, userBean.ago1Bean.toString())
        } else if ("农场进度展示" == showType2) {
            remoteViews.setViewVisibility(R.id.normalShow_2, View.GONE)
            remoteViews.setViewVisibility(R.id.farmShow_2, View.VISIBLE)

            if (TextUtils.isEmpty(userBean.headImageUrl)) {
                Glide.with(MyApplication.mInstance)
                    .load(R.mipmap.icon_head_def)
                    .into(object : SimpleTarget<Drawable?>() {
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                            val head = BitmapUtil.drawableToBitmap(resource)
                            remoteViews.setImageViewBitmap(R.id.headImg, BitmapUtil.createCircleBitmap(head))
                            pullWidget()
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            pullWidget()
                        }
                    })
            } else {
                Glide.with(MyApplication.mInstance)
                    .load(userBean.headImageUrl)
                    .into(object : SimpleTarget<Drawable?>() {
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                            val head = BitmapUtil.drawableToBitmap(resource)
                            remoteViews.setImageViewBitmap(R.id.headImg, BitmapUtil.createCircleBitmap(head))
                            pullWidget()
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            pullWidget()
                        }
                    })
            }
            remoteViews.setTextViewText(R.id.treeEnergy, userBean.treeEnergy)
            remoteViews.setTextViewText(R.id.farmName, userBean.farmName)
            remoteViews.setTextViewText(R.id.treeTotalEnergy, userBean.treeTotalEnergy)

        }

        try {
            if (getCurrentHH() + userBean.countdownTime > 24) {
                remoteViews.setTextViewText(R.id.guoquHb1, "明日过期:" + userBean.gqhb)
            } else {
                remoteViews.setTextViewText(R.id.guoquHb1, "今日过期:" + userBean.gqhb)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            remoteViews.setTextViewText(R.id.guoquHb1, "今日过期:" + userBean.gqhb)
        }
        remoteViews.setTextViewText(R.id.jingXiang, userBean.jxiang)


        if (TextUtils.isEmpty(userBean.treeImageUrl)) {
            Glide.with(MyApplication.mInstance)
                .load(R.mipmap.icon_head_def)
                .into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        val head = BitmapUtil.drawableToBitmap(resource)
                        remoteViews.setImageViewBitmap(R.id.treeImage, BitmapUtil.createCircleBitmap(head))
                        pullWidget()
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        pullWidget()
                    }
                })
        } else {
            Glide.with(MyApplication.mInstance)
                .load(userBean.treeImageUrl)
                .into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        val head = BitmapUtil.drawableToBitmap(resource)
                        remoteViews.setImageViewBitmap(R.id.treeImage, BitmapUtil.createCircleBitmap(head))
                        pullWidget()
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        pullWidget()
                    }
                })
        }

        if (TextUtils.isEmpty(userBean.headImageUrl)) {
            Glide.with(MyApplication.mInstance)
                .load(R.mipmap.icon_head_def)
                .into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        val head = BitmapUtil.drawableToBitmap(resource)
                        remoteViews.setImageViewBitmap(R.id.headImg, BitmapUtil.createCircleBitmap(head))
                        pullWidget()
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        pullWidget()
                    }
                })
        } else {
            Glide.with(MyApplication.mInstance)
                .load(userBean.headImageUrl)
                .into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        val head = BitmapUtil.drawableToBitmap(resource)
                        remoteViews.setImageViewBitmap(R.id.headImg, BitmapUtil.createCircleBitmap(head))
                        pullWidget()
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        pullWidget()
                    }
                })
        }
    }

    private fun pullWidget() {
        val manager = AppWidgetManager.getInstance(MyApplication.mInstance)
        val componentName = when (thisKey) {
            "ck" -> {
                ComponentName(MyApplication.mInstance, MyAppWidgetProvider_2::class.java)
            }
            "ck1" -> {
                ComponentName(MyApplication.mInstance, MyAppWidgetProvider1_2::class.java)
            }
            "ck2" -> {
                ComponentName(MyApplication.mInstance, MyAppWidgetProvider2_2::class.java)
            }
            "ck3" -> {
                ComponentName(MyApplication.mInstance, MyAppWidgetProvider3_2::class.java)
            }
            "ck4" -> {
                ComponentName(MyApplication.mInstance, MyAppWidgetProvider4_2::class.java)
            }
            "ck5" -> {
                ComponentName(MyApplication.mInstance, MyAppWidgetProvider5_2::class.java)
            }
            else -> {
                ComponentName(MyApplication.mInstance, MyAppWidgetProvider5_2::class.java)
            }
        }
        manager.updateAppWidget(componentName, remoteViews)
    }
}
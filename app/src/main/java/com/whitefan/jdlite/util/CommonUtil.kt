package com.whitefan.jdlite.util

import com.whitefan.jdlite.MyApplication




fun Int.dmToPx(): Int {
    return MyApplication.mInstance.resources.getDimensionPixelSize(this)
}

fun Int.dmToSp(): Int {
    return MyApplication.mInstance.resources.getDimensionPixelSize(this)
}

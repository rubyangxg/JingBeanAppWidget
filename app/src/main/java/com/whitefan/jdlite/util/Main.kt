package com.whitefan.jdlite.util

import android.graphics.Color


fun main() {
    transColor("#ffffc000")
}

fun colorToHexValue() {
    val d = Integer.valueOf("ff", 16)
    println(d.toString())
}

fun transColor(color: String): Int {
    val c1 = color.subSequence(color.length - 6, color.length - 4)
    val c2 = color.subSequence(color.length - 4, color.length - 2)
    val c3 = color.subSequence(color.length - 2, color.length)

    val d1 = Integer.valueOf(c1.toString(), 16)
    val d2 = Integer.valueOf(c2.toString(), 16)
    val d3 = Integer.valueOf(c3.toString(), 16)

    return Color.rgb(255 - d1, 255 - d2, 255 - d3)
}


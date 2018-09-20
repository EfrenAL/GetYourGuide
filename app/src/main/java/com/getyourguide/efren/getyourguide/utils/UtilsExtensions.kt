package com.getyourguide.efren.getyourguide.utils

import android.support.design.widget.Snackbar
import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.toDate(): Date? {

    var format = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
    try {
        var date = format.parse(this)
        return date
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return null
}


fun Date.transformToGygDate(): String {

    var format = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
    try {
        var string = format.format(this)
        return string
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return "December 21, 1988"
}

fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) +  start

package com.example.virtualfridge.domain.calendar

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.markDay(context: Context, bgRes: Int?, colorRes: Int) {
    setTextColor(ContextCompat.getColor(context, colorRes))
    if (bgRes == null) {
        background = null
    } else {
        setBackgroundResource(bgRes)
    }
}
package com.example.navigationdrawer.helper

import android.content.Context
import androidx.core.content.ContextCompat

object AppUtilities {
    fun fetchColorResource(context: Context, colorRes: Int): Int {
        return ContextCompat.getColor(context,colorRes)
    }
    fun fetchStringResource(context: Context, stringRes: Int): String {
        return context.getString(stringRes)
    }
}
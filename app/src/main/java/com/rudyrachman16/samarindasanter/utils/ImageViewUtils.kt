package com.rudyrachman16.samarindasanter.utils

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

object ImageViewUtils {
    /**
     * Loading circular drawable for loading placeholder when ImageView.load()
     * @param context an Activity Context
     * @return animated circular for loading indicator
     */
    fun loadingDrawable(context: Context) = CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 20f
        start()
    }
}
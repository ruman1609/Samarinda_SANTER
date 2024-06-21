package com.rudyrachman16.samarindasanter.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rudyrachman16.samarindasanter.R
import com.rudyrachman16.samarindasanter.core.BuildConfig

object ImageViewUtils {
    /**
     * Load an online image (PNG or JPG) using an image url, into the ImageView
     * @see ImageView
     * @see Glide
     * @param imageUrl url of the image
     */
    fun ImageView.load(imageUrl: String) {
        Glide.with(this).load("${BuildConfig.IMAGE_URL}/$imageUrl").apply(
            RequestOptions.placeholderOf(loadingDrawable(this.context)).error(
                R.drawable.ic_broken_image
            )
        ).into(this)
    }

    /**
     * Loading circular drawable for loading placeholder when ImageView.load()
     * @see ImageView.load
     * @param context an Activity Context
     * @return animated circular for loading indicator
     */
    private fun loadingDrawable(context: Context) = CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 20f
        start()
    }
}
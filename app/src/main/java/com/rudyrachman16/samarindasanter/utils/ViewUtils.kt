package com.rudyrachman16.samarindasanter.utils

import android.app.Activity
import android.content.Context
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rudyrachman16.samarindasanter.databinding.DialogLoadingBinding

object ViewUtils {
    private var loadingDialog: AlertDialog? = null
    fun Activity.showLoadingDialog(isLoading: Boolean) {
        if (isLoading) {
            if (loadingDialog == null || loadingDialog?.isShowing != true) {
                loadingDialog = AlertDialog.Builder(this).setView(DialogLoadingBinding.inflate(layoutInflater).root).apply {
                    setCancelable(false)
                }.create()
                loadingDialog?.show()
            }
        } else {
            loadingDialog?.dismiss()
        }
    }

    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun TextView.fromHTML(htmlString: String) {
        text = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
    }
}
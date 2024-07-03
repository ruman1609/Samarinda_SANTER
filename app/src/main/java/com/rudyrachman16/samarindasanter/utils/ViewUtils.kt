package com.rudyrachman16.samarindasanter.utils

import android.app.Activity
import android.content.Context
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rudyrachman16.samarindasanter.core.Status
import com.rudyrachman16.samarindasanter.databinding.DialogLoadingBinding

object ViewUtils {
    private var loadingDialog: AlertDialog? = null
    private fun Activity.showLoadingDialog(isLoading: Boolean) {
        if (isLoading) {
            if (loadingDialog == null || loadingDialog?.isShowing != true) {
                loadingDialog = AlertDialog.Builder(this)
                    .setView(DialogLoadingBinding.inflate(layoutInflater).root).apply {
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

    fun String.toSpannedHTML(): Spanned =
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)

    fun <T> Context.viewModelStatusConsume(
        it: Status<T>?, activity: Activity, onErrorCallback: (it: Status.Error) -> Unit = {
            showToast(it.error.message ?: "Terjadi error")
        }, onSuccessCallback: (it: Status.Success<T>) -> Unit, doneCallback: () -> Unit
    ) {
        when (it) {
            Status.Loading -> activity.showLoadingDialog(true)
            is Status.Error -> {
                activity.showLoadingDialog(false)
                onErrorCallback(it)
                doneCallback()
            }

            is Status.Success -> {
                activity.showLoadingDialog(false)
                onSuccessCallback(it)
                doneCallback()
            }

            null -> {}
        }
    }
}
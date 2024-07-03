package com.rudyrachman16.samarindasanter.utils

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.Html
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.rudyrachman16.samarindasanter.core.Status
import com.rudyrachman16.samarindasanter.databinding.DialogLoadingBinding

object ViewUtils {
    private var loadingDialog: AlertDialog? = null
    fun Activity.showLoadingDialog(isLoading: Boolean) {
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

    fun TextView.fromHTML(htmlString: String) {
        text = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
    }

    fun String.fromHTML(htmlString: String): AnnotatedString {
        val spanned = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT),
        return buildAnnotatedString {
            spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
                val start = spanned.getSpanStart(span)
                val end = spanned.getSpanEnd(span)
                when (span) {
                    is StyleSpan -> when (span.style) {
                        Typeface.BOLD -> addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start,
                            end
                        )

                        Typeface.ITALIC -> addStyle(
                            SpanStyle(fontStyle = FontStyle.Italic),
                            start,
                            end
                        )

                        Typeface.BOLD_ITALIC -> addStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            ), start, end
                        )
                    }

                    is UnderlineSpan -> addStyle(
                        SpanStyle(textDecoration = TextDecoration.Underline),
                        start,
                        end
                    )

                    is ForegroundColorSpan -> addStyle(
                        SpanStyle(color = Color(span.foregroundColor)),
                        start,
                        end
                    )
                }
            }
        }
    }

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
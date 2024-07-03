package com.rudyrachman16.samarindasanter.ui.dashboard

import android.text.TextUtils
import android.text.util.Linkify
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.ui.theme.SamarindaSanterTypography
import com.google.android.material.textview.MaterialTextView
import com.rudyrachman16.samarindasanter.R
import com.rudyrachman16.samarindasanter.core.BuildConfig
import com.rudyrachman16.samarindasanter.core.Status
import com.rudyrachman16.samarindasanter.core.model.News
import com.rudyrachman16.samarindasanter.ui.detail.DetailActivity
import com.rudyrachman16.samarindasanter.utils.ImageViewUtils
import com.rudyrachman16.samarindasanter.utils.ViewUtils.showToast
import com.rudyrachman16.samarindasanter.utils.ViewUtils.viewModelStatusConsume

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel(), goToDetail: (news: News) -> Unit) {
    val news by viewModel.newsStatus
    ObserveNewsStatus(it = news, emptyPlaceholder = { isEmpty, warningMessage ->
        viewModel.isEmpty.value = isEmpty
        viewModel.warningMessage.value = warningMessage
    }, successCallback = { viewModel.newsData = it as SnapshotStateList<News> }) {
        viewModel.setNewsStatusToNull()
    }

    AnimatedVisibility(visible = !viewModel.isEmpty.value) {
        Text(
            text = viewModel.warningMessage.value,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentSize()
        )
    }

    AnimatedVisibility(visible = !viewModel.isEmpty.value) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 8.dp),
            modifier = Modifier.padding(20.dp)
        ) {
            items(viewModel.newsData) {
                NewsItem(news = it) { data ->
                    goToDetail(data)
                }
            }
        }
    }
}

@Composable
private fun ObserveNewsStatus(
    it: Status<List<News>>?,
    emptyPlaceholder: (isEmpty: Boolean, warningMessage: String) -> Unit,
    successCallback: (data: List<News>) -> Unit,
    doneCallback: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as DetailActivity

    context.viewModelStatusConsume(it, activity, onErrorCallback = {
        context.showToast(it.error.message ?: "Terjadi error")
        emptyPlaceholder(true, it.error.message ?: "")
    }, onSuccessCallback = {
        emptyPlaceholder(it.data.isEmpty(), context.getString(R.string.data_kosong))
        if (it.data.isEmpty()) {
            return@viewModelStatusConsume
        }
        successCallback(it.data)
    }, doneCallback = doneCallback)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun NewsItem(news: News, goToDetail: (news: News) -> Unit) {
    Card(shape = RoundedCornerShape(6.dp), modifier = Modifier.clickable { goToDetail(news) }) {
        Row(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                model = "${BuildConfig.IMAGE_URL}/${news.foto}",
                contentDescription = news.preview,
                contentScale = ContentScale.Crop,
                failure = placeholder(R.drawable.ic_broken_image),
                loading = placeholder(ImageViewUtils.loadingDrawable(LocalContext.current)),
                modifier = Modifier.fillMaxWidth(.16f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = news.judul ?: "",
                    style = SamarindaSanterTypography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                AndroidView(factory = {
                    MaterialTextView(ContextThemeWrapper(it, R.style.text)).apply {
                        autoLinkMask = Linkify.WEB_URLS
                        linksClickable = true
                    }
                }, update = {
                    it.apply {
                        text = news.isi ?: ""
                        maxLines = 2
                        ellipsize = TextUtils.TruncateAt.END
                    }
                })
                Text(
                    text = news.createdAt ?: "",
                    style = SamarindaSanterTypography.labelSmall,
                    color = Color.LightGray,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
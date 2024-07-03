package com.rudyrachman16.samarindasanter.ui.dashboard

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.ui.theme.SamarindaSanterTypography
import com.rudyrachman16.samarindasanter.R
import com.rudyrachman16.samarindasanter.core.BuildConfig
import com.rudyrachman16.samarindasanter.core.model.News
import com.rudyrachman16.samarindasanter.utils.ImageViewUtils

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreen(news: News) {
    LazyColumn(contentPadding = PaddingValues(bottom = 12.dp), modifier = Modifier.fillMaxWidth()) {
        item {
            GlideImage(
                model = "${BuildConfig.IMAGE_URL}/${news.foto}",
                contentDescription = news.preview,
                contentScale = ContentScale.FillWidth,
                failure = placeholder(R.drawable.ic_broken_image),
                loading = placeholder(ImageViewUtils.loadingDrawable(LocalContext.current)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
        item {
            Text(
                text = news.judul ?: "",
                style = SamarindaSanterTypography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Text(
                text = news.createdAt ?: "",
                style = SamarindaSanterTypography.labelSmall,
                color = Color.LightGray,
                textAlign = TextAlign.End
            )
        }
        item {
            AndroidView(factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            }, update = {
                it.loadData(news.isi ?: "", "text/html", "UTF-8")
            })
        }
    }
}
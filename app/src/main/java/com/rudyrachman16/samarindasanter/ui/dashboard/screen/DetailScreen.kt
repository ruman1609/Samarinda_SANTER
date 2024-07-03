package com.rudyrachman16.samarindasanter.ui.dashboard.screen

import android.webkit.WebView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.ui.theme.SamarindaSanterTypography
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.rudyrachman16.samarindasanter.R
import com.rudyrachman16.samarindasanter.core.BuildConfig
import com.rudyrachman16.samarindasanter.core.model.News
import com.rudyrachman16.samarindasanter.utils.ImageViewUtils

@Composable
fun DetailScreen(news: News) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            AsyncImage(
                model = "${BuildConfig.IMAGE_URL}/${news.foto}",
                contentDescription = news.preview,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = rememberDrawablePainter(ImageViewUtils.loadingDrawable(LocalContext.current)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(bottom = 12.dp))
                    .height(300.dp)
            )
        }
        item {
            Text(
                text = news.judul ?: "",
                style = SamarindaSanterTypography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(bottom = 12.dp, start = 20.dp, end = 20.dp))
            )
        }
        item {
            Text(
                text = news.createdAt ?: "",
                style = SamarindaSanterTypography.labelSmall,
                color = Color.LightGray,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(
                    PaddingValues(
                        bottom = 12.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
                )
            )
        }
        item {
            AndroidView(
                factory = {
                    WebView(it)
                },
                update = {
                    it.loadData(news.isi ?: "", "text/html", "UTF-8")
                },
                modifier = Modifier.padding(
                    PaddingValues(
                        bottom = 12.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
                )
            )
        }
    }
}
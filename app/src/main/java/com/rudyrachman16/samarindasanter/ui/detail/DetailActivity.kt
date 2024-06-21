package com.rudyrachman16.samarindasanter.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rudyrachman16.samarindasanter.R
import com.rudyrachman16.samarindasanter.core.model.News
import com.rudyrachman16.samarindasanter.databinding.ActivityDetailBinding
import com.rudyrachman16.samarindasanter.utils.ImageViewUtils.load

class DetailActivity : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context, news: News) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(NEWS_KEYS, news)
            })
        }

        private const val NEWS_KEYS = "com.rudyrachman16.samarindasanter.ui.detail.NewsKeys"
    }

    private lateinit var bind: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val news = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(NEWS_KEYS, News::class.java)
        } else {
            intent.getParcelableExtra(NEWS_KEYS)
        }

        bind.ivDetailFoto.load(news?.foto ?: "")
        bind.tvDetailTitle.text = news?.judul ?: ""
        bind.tvDetailDate.text = news?.createdAt ?: ""
        bind.wvDetailDesc.loadData(news?.isi ?: "", "text/html", "UTF-8")
    }
}
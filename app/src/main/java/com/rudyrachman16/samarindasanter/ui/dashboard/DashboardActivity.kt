package com.rudyrachman16.samarindasanter.ui.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.rudyrachman16.samarindasanter.R
import com.rudyrachman16.samarindasanter.ViewModelFactory
import com.rudyrachman16.samarindasanter.core.Status
import com.rudyrachman16.samarindasanter.databinding.ActivityDashboardBinding
import com.rudyrachman16.samarindasanter.ui.detail.DetailActivity
import com.rudyrachman16.samarindasanter.utils.ViewUtils.showLoadingDialog
import com.rudyrachman16.samarindasanter.utils.ViewUtils.showToast

class DashboardActivity : AppCompatActivity() {
    private lateinit var bind: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.news.observe(this) {
            when (it) {
                Status.Loading -> showLoadingDialog(true)
                is Status.Error -> {
                    showLoadingDialog(false)
                    showToast(it.error.message ?: "Terjadi error")
                    emptyPlaceholder(true, it.error.message ?: "")
                }

                is Status.Success -> {
                    showLoadingDialog(false)
                    emptyPlaceholder(it.data.isEmpty())

                    if (it.data.isEmpty()) {
                        return@observe
                    }

                    val adapter = DashboardAdapter(it.data) { news ->
                        DetailActivity.start(this@DashboardActivity, news)
                    }

                    bind.rvBerita.adapter = adapter
                }
            }
        }
    }

    private fun emptyPlaceholder(
        isEmpty: Boolean,
        message: String = getString(R.string.data_kosong)
    ) {
        bind.rvBerita.isVisible = !isEmpty
        bind.tvInfo.isVisible = isEmpty
        bind.tvInfo.text = message
    }
}
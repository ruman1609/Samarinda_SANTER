package com.rudyrachman16.samarindasanter.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.rudyrachman16.samarindasanter.R
import com.rudyrachman16.samarindasanter.ViewModelFactory
import com.rudyrachman16.samarindasanter.core.Status
import com.rudyrachman16.samarindasanter.databinding.ActivityMainBinding
import com.rudyrachman16.samarindasanter.ui.dashboard.DashboardActivity
import com.rudyrachman16.samarindasanter.utils.ViewUtils.showLoadingDialog
import com.rudyrachman16.samarindasanter.utils.ViewUtils.showToast

class MainActivity : AppCompatActivity() {
    private var isLogin = true
        set(value) {
            field = value
            bind.btnLogin.isVisible = field
        }

    private lateinit var bind: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bind.btnLogin.isVisible = isLogin

        bind.btnLogin.setOnClickListener {
            if (!isLogin) {
                return@setOnClickListener
            }
            loginRegisterCallback {
                viewModel.login(bind.etUsername.text.toString(), bind.etPassword.text.toString())
                    .observe(this) {
                        when (it) {
                            Status.Loading -> showLoadingDialog(true)
                            is Status.Error -> {
                                showLoadingDialog(false)
                                showToast(it.error.message ?: "Terjadi error")
                            }

                            is Status.Success -> {
                                showLoadingDialog(false)
                                if (it.data.size != 1) {
                                    showToast("${bind.etUsername.text} tidak ditemukan")
                                } else {
                                    showToast("Berhasil login, selamat datang ${it.data[0].username}")
                                    startActivity(
                                        Intent(
                                            this,
                                            DashboardActivity::class.java
                                        ).apply {
                                            flags =
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                                        })
                                    finish()
                                }
                            }
                        }
                    }
            }
        }

        bind.btnRegis.setOnClickListener {
            if (isLogin) {
                isLogin = false
                return@setOnClickListener
            }

            loginRegisterCallback {
                viewModel.register(bind.etUsername.text.toString(), bind.etPassword.text.toString())
                    .observe(this) {
                        when (it) {
                            Status.Loading -> showLoadingDialog(true)
                            is Status.Error -> {
                                showLoadingDialog(false)
                                showToast(it.error.message ?: "Terjadi error")
                            }

                            is Status.Success -> {
                                showLoadingDialog(false)
                                showToast(it.data)
                                isLogin = true
                            }
                        }
                    }
            }
        }
    }

    private fun loginRegisterCallback(callback: () -> Unit) {
        if (bind.etUsername.text.toString().isEmpty()) {
            bind.etUsername.requestFocus()
        } else if (bind.etPassword.text.toString().isEmpty()) {
            bind.etPassword.requestFocus()
        } else {
            callback()
        }
    }
}
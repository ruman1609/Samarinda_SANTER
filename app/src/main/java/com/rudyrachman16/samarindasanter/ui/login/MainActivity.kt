package com.rudyrachman16.samarindasanter.ui.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.core.view.isVisible
import com.example.compose.SamarindaSanterTheme
import com.rudyrachman16.samarindasanter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var isLogin = true
        set(value) {
            field = value
            bind.btnLogin.isVisible = field
        }

    private lateinit var bind: ActivityMainBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SamarindaSanterTheme {
                Surface {
                    LoginCompose()
                }
            }
        }
    }
}
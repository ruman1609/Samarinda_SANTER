package com.rudyrachman16.samarindasanter.ui.login

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.SamarindaSanterTypography
import com.rudyrachman16.samarindasanter.R
import com.rudyrachman16.samarindasanter.core.Status
import com.rudyrachman16.samarindasanter.core.model.User
import com.rudyrachman16.samarindasanter.ui.dashboard.DashboardActivity
import com.rudyrachman16.samarindasanter.utils.ViewUtils.showToast
import com.rudyrachman16.samarindasanter.utils.ViewUtils.viewModelStatusConsume

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    val usernameFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }

    val loginStatus by viewModel.loginStatus
    ObserveLoginStatus(loginStatus, viewModel.username.value) {
        viewModel.setLoginStatusNull()
    }

    val registerStatus by viewModel.registerStatus
    ObserveRegisterStatus(it = registerStatus, setLoginTrueCallback = {
        viewModel.isLogin.value = true
    }) {
        viewModel.setRegisterStatusToNull()
    }

    BackHandler(!viewModel.isLogin.value) {
        viewModel.isLogin.value = true
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = SamarindaSanterTypography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = viewModel.username.value,
            onValueChange = {
                viewModel.username.value = it
            },
            maxLines = 1,
            label = {
                Text(text = stringResource(R.string.username))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .focusRequester(usernameFocus)
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = viewModel.password.value,
            onValueChange = {
                viewModel.password.value = it
            },
            maxLines = 1,
            label = {
                Text(text = stringResource(R.string.password))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ), visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .focusRequester(passwordFocus)
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
        AnimatedVisibility(visible = viewModel.isLogin.value) {
            Button(
                onClick = {
                    loginRegisterCallback(
                        viewModel.username.value, viewModel.password.value,
                        textInputLayoutReqFocusCallback = { isUsername ->
                            if (isUsername) {
                                usernameFocus.requestFocus()
                            } else {
                                passwordFocus.requestFocus()
                            }
                        },
                        callback = {
                            if (!viewModel.isLogin.value) {
                                return@loginRegisterCallback
                            }

                            viewModel.login()
                        })
                }, modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
        Button(
            onClick = {
                if (viewModel.isLogin.value) {
                    viewModel.isLogin.value = false
                    return@Button
                }
                loginRegisterCallback(
                    viewModel.username.value, viewModel.password.value,
                    textInputLayoutReqFocusCallback = { isUsername ->
                        if (isUsername) {
                            usernameFocus.requestFocus()
                        } else {
                            passwordFocus.requestFocus()
                        }
                    },
                    callback = {
                        viewModel.register()
                    })
            }, modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}

private fun loginRegisterCallback(
    username: String,
    password: String,
    textInputLayoutReqFocusCallback: (isUsername: Boolean) -> Unit,
    callback: () -> Unit
) {
    if (username.isEmpty()) {
        textInputLayoutReqFocusCallback(true)
    } else if (password.isEmpty()) {
        textInputLayoutReqFocusCallback(false)
    } else {
        callback()
    }
}

@Composable
private fun ObserveLoginStatus(
    it: Status<List<User>>?,
    username: String,
    doneCallback: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as MainActivity

    context.viewModelStatusConsume(it, activity, onSuccessCallback = {
        if (it.data.size != 1) {
            context.showToast("$username tidak ditemukan")
        } else {
            context.showToast("Berhasil login, selamat datang ${it.data[0].username}")
            context.startActivity(
                Intent(
                    context, DashboardActivity::class.java
                ).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                })
            context.finish()
        }
    }, doneCallback = doneCallback)
}

@Composable
private fun ObserveRegisterStatus(
    it: Status<String>?,
    setLoginTrueCallback: (isLogin: Boolean) -> Unit,
    doneCallback: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as MainActivity

    context.viewModelStatusConsume(it, activity, onSuccessCallback = {
        context.showToast(it.data)
        setLoginTrueCallback(true)
    }, doneCallback = doneCallback)
}
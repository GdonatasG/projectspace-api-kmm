package com.project.space.feature.authorization.android_ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.project.space.components.button.FullWidthButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AuthorizationScreen(logoResId: Int, viewModel: AuthorizationViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val tabs = listOf(AuthorizationViewModel.ViewMode.Login, AuthorizationViewModel.ViewMode.Register)

    val state by viewModel.state.collectAsState()
    val viewMode by viewModel.mode.collectAsState()

    val username by viewModel.username.collectAsState()
    val usernameError by viewModel.usernameError.collectAsState()

    val password by viewModel.password.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            TabRow(
                selectedTabIndex = if (viewMode == AuthorizationViewModel.ViewMode.Login) 0 else 1,
                modifier = Modifier.fillMaxWidth(),
                contentColor = if (state != AuthorizationViewModel.ViewState.Loading) TabRowDefaults.contentColor
                else TabRowDefaults.contentColor.copy(alpha = 0.6f)
            ) {
                tabs.forEach { mode ->
                    Tab(text = { Text(mode.title) }, selected = viewMode == mode, onClick = {
                        viewModel.onModeChange(mode)
                    }, enabled = state != AuthorizationViewModel.ViewState.Loading
                    )
                }
            }

            Box(modifier = Modifier.clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus(true)
            }) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Image(
                            painter = painterResource(id = logoResId),
                            contentDescription = "ProjectSpace",
                            modifier = Modifier.size(width = 200.dp, height = 120.dp),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    item {
                        AnimatedContent(targetState = viewMode == AuthorizationViewModel.ViewMode.Login) { isLogin ->
                            if (isLogin) {
                                BuildLogin(username = username,
                                    usernameError = usernameError,
                                    password = password,
                                    passwordError = passwordError,
                                    isLoading = state is AuthorizationViewModel.ViewState.Loading,
                                    delegate = object : LoginDelegate {
                                        override fun onLogin() {
                                            viewModel.onLogin()
                                        }

                                        override fun onUsernameChanged(value: TextFieldValue) {
                                            viewModel.onUsernameChanged(value)
                                        }

                                        override fun onPasswordChanged(value: TextFieldValue) {
                                            viewModel.onPasswordChanged(value)
                                        }
                                    })

                                return@AnimatedContent
                            }

                            BuildRegister(delegate = object : RegisterDelegate {
                                override fun onRegister() {

                                }

                            })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuildLogin(
    username: TextFieldValue,
    usernameError: String?,
    password: TextFieldValue,
    passwordError: String?,
    isLoading: Boolean,
    delegate: LoginDelegate
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = username, onValueChange = {
            delegate.onUsernameChanged(it)
        }, label = {
            Text(text = "Username")
        }, singleLine = true, isError = usernameError != null)
        if (usernameError != null) {
            Text(
                text = usernameError,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                delegate.onPasswordChanged(it)
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                // Toggle button to hide or display password
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            singleLine = true,
            isError = passwordError != null
        )
        if (passwordError != null) {
            Text(
                text = passwordError,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        FullWidthButton(title = "Login", isLoading = isLoading) {
            delegate.onLogin()
        }
    }
}

private interface LoginDelegate {
    fun onLogin()
    fun onUsernameChanged(value: TextFieldValue)
    fun onPasswordChanged(value: TextFieldValue)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuildRegister(delegate: RegisterDelegate) {
    var passwordVisible by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    var passwordRepeatVisible by remember { mutableStateOf(false) }
    var passwordRepeat by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = {

        }, label = {
            Text(text = "Username (required)")
        }, singleLine = true)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = {

        }, label = {
            Text(text = "First name")
        }, singleLine = true)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = {

        }, label = {
            Text(text = "Last name")
        }, singleLine = true)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = {

        }, label = {
            Text(text = "Email (required)")
        }, singleLine = true)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Password (required)")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                // Toggle button to hide or display password
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordRepeat,
            onValueChange = {
                passwordRepeat = it
            },
            label = {
                Text(text = "Repeat password (required)")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordRepeatVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Localized description for accessibility services
                val description = if (passwordRepeatVisible) "Hide password" else "Show password"

                // Toggle button to hide or display password
                IconButton(onClick = { passwordRepeatVisible = !passwordRepeatVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        FullWidthButton(title = "Register") {
            delegate.onRegister()
        }
    }
}

private interface RegisterDelegate {
    fun onRegister()
}



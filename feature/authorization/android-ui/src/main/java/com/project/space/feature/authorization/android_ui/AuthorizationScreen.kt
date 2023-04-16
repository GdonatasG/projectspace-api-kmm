package com.project.space.feature.authorization.android_ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.project.space.components.button.FullWidthButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AuthorizationScreen(logoResId: Int, viewModel: AuthorizationViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val tabs = listOf("Login", "Register")

    val state by viewModel.state.collectAsState()
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()

    val username by viewModel.username.collectAsState()
    val usernameError by viewModel.usernameError.collectAsState()

    val password by viewModel.password.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val passwordVisible by viewModel.passwordVisible.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                contentColor = if (state != AuthorizationViewModel.ViewState.Loading) TabRowDefaults.contentColor
                else TabRowDefaults.contentColor.copy(alpha = 0.6f)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) }, selected = index == selectedTabIndex,
                        onClick = {
                            focusManager.clearFocus(true)
                            viewModel.onModeChange(index)
                        }, enabled = state != AuthorizationViewModel.ViewState.Loading
                    )
                }
            }

            Box(modifier = Modifier.clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus(true)
            }) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = logoResId),
                                contentDescription = "ProjectSpace",
                                modifier = Modifier.size(width = 200.dp, height = 120.dp),
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    item {
                        BuildForm(
                            isRegister = selectedTabIndex == 1,
                            username = username,
                            usernameError = usernameError,
                            password = password,
                            passwordError = passwordError,
                            passwordVisible = passwordVisible,
                            isLoading = state is AuthorizationViewModel.ViewState.Loading,
                            focusManager = focusManager,
                            delegate = object : FormDelegate {
                                override fun onUsernameChanged(value: TextFieldValue) {
                                    viewModel.onUsernameChanged(value)
                                }

                                override fun onPasswordChanged(value: TextFieldValue) {
                                    viewModel.onPasswordChanged(value)
                                }

                                override fun onAction() {
                                    if (selectedTabIndex == 0) {
                                        viewModel.onLogin()

                                        return
                                    }

                                    // TODO: register
                                }

                                override fun onPasswordVisibilityChanged(value: Boolean) {
                                    viewModel.onPasswordVisibilityChanged(value)
                                }

                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuildForm(
    isRegister: Boolean,
    username: TextFieldValue,
    usernameError: String?,
    password: TextFieldValue,
    passwordError: String?,
    passwordVisible: Boolean,
    isLoading: Boolean,
    focusManager: FocusManager,
    delegate: FormDelegate
) {
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(), value = username,
            onValueChange = {
                delegate.onUsernameChanged(it)
            },
            label = {
                Text(text = "Username (required)")
            },
            singleLine = true, isError = usernameError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        if (usernameError != null) {
            Text(
                text = usernameError,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End
            )
        }
        AnimatedVisibility(visible = isRegister) {
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = {

            }, label = {
                Text(text = "First name")
            }, singleLine = true, keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ), keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
            )
        }
        AnimatedVisibility(visible = isRegister) {
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = {

            }, label = {
                Text(text = "Last name")
            }, singleLine = true, keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ), keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
            )
        }
        AnimatedVisibility(visible = isRegister) {
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = {

            }, label = {
                Text(text = "Email (required)")
            }, singleLine = true, keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ), keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                delegate.onPasswordChanged(it)
            },
            label = {
                Text(text = "Password (required)")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                // Toggle button to hide or display password
                IconButton(onClick = { delegate.onPasswordVisibilityChanged(!passwordVisible) }) {
                    Icon(imageVector = image, description)
                }
            },
            singleLine = true,
            isError = passwordError != null,
            keyboardOptions = if (isRegister) KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ) else KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onNext = {
                if (isRegister)
                    focusManager.moveFocus(FocusDirection.Down)
            })
        )
        if (passwordError != null) {
            Text(
                text = passwordError,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End
            )
        }
        AnimatedVisibility(visible = isRegister) {
            var passwordRepeatVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {

                },
                label = {
                    Text(text = "Repeat password (required)")
                },
                visualTransformation = if (passwordRepeatVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
        }
        Spacer(modifier = Modifier.height(24.dp))
        FullWidthButton(title = if (isRegister) "Register" else "Login", isLoading = isLoading) {
            delegate.onAction()
        }
    }
}

private interface FormDelegate {
    fun onUsernameChanged(value: TextFieldValue)
    fun onPasswordChanged(value: TextFieldValue)
    fun onPasswordVisibilityChanged(value: Boolean)
    fun onAction()
}



package com.imooc.composeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imooc.composeapp.R
import com.imooc.composeapp.compositionLocal.LocalUserViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onClose: () -> Unit) {
    val userViewModel = LocalUserViewModel.current

    // 屏幕的宽度
    var screenWidth: Float
    // 屏幕高度
    var screenHeight: Float

    var showPassword by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    /* with(LocalDensity.current) {
         screenWidth = LocalConfiguration.current.screenWidthDp.dp.toPx()
         screenHeight = LocalConfiguration.current.screenHeightDp.dp.toPx()
     }*/

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        // 右上往左下渐变
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xffbb8378), Color.Transparent),
                            start = Offset(x = constraints.maxWidth.toFloat(), y = 0f),
                            end = Offset(x = 0f, y = constraints.maxHeight.toFloat()),
                        ),
                    ),
        )
        // 左下往右下渐变层
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF149EE7), Color.Transparent),
                            start = Offset(x = 0f, y = constraints.maxHeight.toFloat()),
                            end = Offset(x = constraints.maxWidth.toFloat(), y = 0f),
                        ),
                    ),
        )
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "用户登录",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = userViewModel.userName,
                    onValueChange = { userViewModel.userName = it },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    },
                    label = {
                        Text(text = "用户名", fontSize = 14.sp, color = Color.White)
                    },
                    colors =
                        TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedLabelColor = Color.LightGray,
                            unfocusedLabelColor = Color.LightGray,
                            cursorColor = Color.White,
                        ),
                )

                TextField(
                    value = userViewModel.passWord,
                    onValueChange = { userViewModel.passWord = it },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Password,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector =
                                if (showPassword) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                            contentDescription = null,
                            modifier =
                                Modifier.clickable {
                                    showPassword = !showPassword
                                },
                            tint = Color.White,
                        )
                    },
                    label = {
                        Text(text = "密码", fontSize = 14.sp, color = Color.White)
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    colors =
                        TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedLabelColor = Color.LightGray,
                            unfocusedLabelColor = Color.LightGray,
                            cursorColor = Color.White,
                        ),
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = {
                    coroutineScope.launch {
                        userViewModel.login(onClose = onClose)
                    }
                }, enabled = userViewModel.loading) {
                    Row {
                        Text(text = "登陆", color = Color.White)
                        if (userViewModel.loading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            TextButton(onClick = {}) {
                Text(text = "还没有账号？点击立即注册", color = Color.LightGray, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = userViewModel.error, color = Color.Red, fontSize = 13.sp)
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen {
    }
}

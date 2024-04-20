package com.imooc.composeapp.compositionLocal

import androidx.compose.runtime.compositionLocalOf
import com.imooc.composeapp.viewmodel.UserViewModel

val LocalUserViewModel =
    compositionLocalOf<UserViewModel> { error("User View Model Context Not Found") }
package com.imooc.composeapp.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.imooc.composeapp.ui.components.video.VideoPlayer
import com.imooc.composeapp.ui.components.video.rememberVodController
import com.imooc.composeapp.ui.theme.Blue700
import com.imooc.composeapp.viewmodel.VideoViewModel
import com.imooc.module.webview.WebView
import com.imooc.module.webview.rememberWebViewState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VideoDetailScreen(
    videoViewModel: VideoViewModel = viewModel(),
    onBack: () -> Unit,
) {
    val systemUiController = rememberSystemUiController()

    val webViewState = rememberWebViewState(data = videoViewModel.videoDesc)

    val vodController =
        rememberVodController(
            videoUrl = videoViewModel.videoUrl,
            coverUrl = videoViewModel.coverUrl,
        )

    val configuration = LocalConfiguration.current

    var scaffoldModifier by remember {
        mutableStateOf(
            Modifier.alpha(1f),
        )
    }

    var videoBoxModifier by remember {
        mutableStateOf(
            Modifier.aspectRatio(16 / 9f),
        )
    }

    // todo 横屏后，点击屏幕状态栏即显示出来，而且不会再隐藏，如何处理这个问题
    LaunchedEffect(configuration.orientation) {
        // 横屏后恢复播放
        vodController.restore()
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            videoBoxModifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            systemUiController.isSystemBarsVisible = true
            scaffoldModifier =
                Modifier
                    .background(Blue700)
                    .statusBarsPadding()
        } else {
            videoBoxModifier = Modifier.fillMaxSize()
            systemUiController.isSystemBarsVisible = false
            scaffoldModifier = Modifier
        }
    }

    Scaffold(
        topBar = {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                TopAppBar(title = {
                    Text(
                        text = "视频详情",
                        fontSize = 18.sp,
                    )
                }, navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .clickable { onBack() }
                                .padding(8.dp),
                    )
                })
            } else {
            }
        },
        modifier = scaffoldModifier,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 视频区域
            Box(modifier = videoBoxModifier) {
                VideoPlayer(vodController = vodController)
            }

            // 想让标题一起滚动，有两个方案
            // 方案一：把标题 html文本中去
            // 方案二：计算视频简介在webView中的高度，然后动态设置webView的高度
            // 简介
            WebView(state = webViewState)
        }
    }
}

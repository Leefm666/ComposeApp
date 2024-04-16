package com.imooc.composeapp.ui.components.video


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import java.util.Timer
import java.util.TimerTask

@Composable
fun VideoPlayer(vodController: VodController) {

    var timeFormatText by remember {
        mutableStateOf("")
    }
    val MILLS_PER_SECOND = 1000
    val MILLS_PER_MINUTE = 60000
    val SECOND_PER_MINUTE = 60

    val context = LocalContext.current

    // 获取配置
    val configuration = LocalConfiguration.current

    val lifecycleOwer = LocalLifecycleOwner.current
    // 监听生命周期
    DisposableEffect(vodController) {
        val lifecycleEventObsever = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> vodController.resume()
                Lifecycle.Event.ON_PAUSE -> vodController.pause()
                else -> {

                }
            }
        }
        lifecycleOwer.lifecycle.addObserver(lifecycleEventObsever)
        onDispose {
            lifecycleOwer.lifecycle.removeObserver(lifecycleEventObsever)
            vodController.stopPlay()
        }
    }

    // 当处于横屏状态时，启用backhandler需要监听返回键，回到竖屏状态
    BackHandler(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        // 横屏状态
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            context.findActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    LaunchedEffect(vodController.playValue.currentPosition) {
        val position = vodController.playValue.currentPosition
        val duration = vodController.playValue.duration

        // 格式化时间
        timeFormatText =
            String.format(
                "%02d:%02d:%02d/%02d:%02d:%02d:",
                position / MILLS_PER_MINUTE / SECOND_PER_MINUTE,
                position / MILLS_PER_MINUTE,
                position / MILLS_PER_SECOND % SECOND_PER_MINUTE,
                duration / MILLS_PER_MINUTE / SECOND_PER_MINUTE,
                duration / MILLS_PER_MINUTE,
                duration / MILLS_PER_SECOND % SECOND_PER_MINUTE,
            )
    }


    // 是否显示控制层
    var showControllerBar by remember {
        mutableStateOf(false)
    }


    // 定时器
    var timer: Timer? = null

    Box(Modifier.clickable(
        interactionSource = remember {
            MutableInteractionSource()
        }, indication = null
    ) {
        timer?.cancel()
        timer = null
        if (!showControllerBar) {
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    showControllerBar = false
                }

            }, 3000)
        }
        showControllerBar = !showControllerBar
    }) {
        // 视频播放区域
        VideoView(vodPlayer = vodController.vodPlayer)

        // 视频封面
        if (vodController.playValue.state == PlayState.None) {
            Box() {
                AsyncImage(
                    model = vodController.playValue.coverUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                )

                IconButton(
                    onClick = {
                        vodController.startPlay()
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(60.dp)
                    )
                }
            }
        }


        // 正在加载层
        if (vodController.playValue.state == PlayState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp)
            )
        }

        // 视频控制层
        if (showControllerBar)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Spacer(modifier = Modifier.height(1.dp))
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(45.dp)
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Black,
                                        Color.Transparent
                                    )
                                )
                            )
                    ) {
                        IconButton(onClick = {
                            context.findActivity()?.requestedOrientation =
                                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        Text(vodController.playValue.title ?: "", color = Color.White)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Black
                                )
                            )
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 播放或暂停按钮
                    IconButton(onClick = {
                        if (vodController.playValue.state == PlayState.Playing) {
                            vodController.pause()
                        } else {
                            vodController.resume()
                        }
                    }) {
                        if (vodController.playValue.state == PlayState.Playing) {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                contentDescription = null,
                                tint = Color.White
                            )

                        } else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // 进度条
                    Slider(
                        value = vodController.playValue.currentPosition.toFloat(),
                        onValueChange = {
                            vodController.playValue.currentPosition = it.toLong()
                            vodController.seekTo(it.toLong())
                        },
                        valueRange = 0f..vodController.playValue.duration.toFloat(),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // 时间显示
                    Text(text = timeFormatText, color = Color.White, fontSize = 14.sp)

                    Spacer(modifier = Modifier.width(8.dp))

                    // 控制全屏按钮
                    IconButton(onClick = {
                        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            // 竖屏
                            context.findActivity()?.requestedOrientation =
                                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            context.findActivity()?.window?.decorView?.systemUiVisibility =
                                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_IMMERSIVE)
                        } else {
                            context.findActivity()?.requestedOrientation =
                                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                        }
                    }) {
                        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            // 竖屏
                            Icon(
                                imageVector = Icons.Default.Fullscreen,
                                contentDescription = null,
                                tint = Color.White
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.FullscreenExit,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }

                    Spacer(modifier = Modifier.width(8.dp))

                }
            }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

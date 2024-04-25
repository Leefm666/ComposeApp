package com.imooc.composeapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import com.imooc.composeapp.viewmodel.ArticleViewModel
import com.imooc.module.webview.rememberWebViewState
import kotlinx.coroutines.launch

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleDetailScreen(
    articleViewModel: ArticleViewModel = viewModel(),
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        articleViewModel.fetchInfo()
    }

    val webViewState = rememberWebViewState(data = articleViewModel.content)

    var fontScale by remember {
        mutableStateOf(1.0f)
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "文章详情",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .clickable {
                                    onBack()
                                }
                                .padding(8.dp),
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .clickable {
                                    // todo 点击设置文字大小
                                    coroutineScope.launch {
                                        if (scaffoldState.bottomSheetState.isCollapsed) {
                                            scaffoldState.bottomSheetState.expand()
                                        } else {
                                            scaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                                }
                                .padding(8.dp),
                    )
                },
            )
        },
        modifier =
            Modifier
                .background(MaterialTheme.colors.primary)
                .statusBarsPadding(),
        sheetContent = {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "字体大小", fontSize = 16.sp)
                Slider(value = fontScale, onValueChange = {
                    fontScale = it
                    webViewState.evaluateJavascript("document.body.style.zoom = $it")
                }, steps = 3, valueRange = 0.75f..1.75f)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "较小", fontSize = 14.sp, color = Color(0xFF999999)) // 0.75
                    Text(text = "标准", fontSize = 14.sp, color = Color(0xFF999999)) // 1.0
                    Text(text = "普大", fontSize = 14.sp, color = Color(0xFF999999)) // 1.25
                    Text(text = "超大", fontSize = 14.sp, color = Color(0xFF999999)) // 1.5
                    Text(text = "特大", fontSize = 14.sp, color = Color(0xFF999999)) // 1.75
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        sheetPeekHeight = 0.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            com.imooc.module.webview.WebView(webViewState)
            if (!articleViewModel.infoLoaded) {
            }
            CircularProgressIndicator()
        }
    }
}

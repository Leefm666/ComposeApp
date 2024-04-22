package com.imooc.composeapp.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.imooc.composeapp.extension.onBottomReached
import com.imooc.composeapp.ui.components.ArticleItem
import com.imooc.composeapp.ui.components.NotificationContent
import com.imooc.composeapp.ui.components.SwiperContent
import com.imooc.composeapp.ui.components.TopAppBar
import com.imooc.composeapp.ui.components.VideoItem
import com.imooc.composeapp.viewmodel.ArticleViewModel
import com.imooc.composeapp.viewmodel.MainViewModel
import com.imooc.composeapp.viewmodel.VideoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StudyScreen(
    vm: MainViewModel = viewModel(),
    articleViewModel: ArticleViewModel = viewModel(),
    videoViewModel: VideoViewModel = viewModel(),
    onNavigateToArticle: () -> Unit = {},
    onNavigateToVideo: () -> Unit = {},
    onNavigateToStudyHistory: () -> Unit = {},
) {
    val coroutinScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()

    lazyListState.onBottomReached(buffer = 3) {
        Log.i("====", "StudyScreen: OnBottomReached")
        coroutinScope.launch {
            articleViewModel.loadMore()
        }
    }

    LaunchedEffect(Unit) {
        // 获取分类数据
        vm.categoryData()
        // 获取文章列表
        articleViewModel.fetchArticleList()
    }

    Column {
        TopAppBar(modifier = Modifier.padding(horizontal = 8.dp)) {
            // 搜索按钮
            Surface(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .weight(1f),
                color = Color(0x33ffffff),
            ) {
                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp),
                    )
                    Text(
                        "搜索感兴趣的资讯和课程",
                        color = Color.White,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            // 学习进度
            Text(
                text = "学习\n进度",
                fontSize = 10.sp,
                color = Color.White,
                modifier =
                    Modifier.clickable {
                        onNavigateToStudyHistory()
                    },
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "26%", fontSize = 12.sp, color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.White,
            )
        }
        // 分类标签
        TabRow(
            selectedTabIndex = vm.categoryIndex,
            backgroundColor = Color(0x66149EE7),
            contentColor = Color(0xFF149EE7),
        ) {
            vm.categories.forEachIndexed { index, category ->
                Tab(
                    selected = vm.categoryIndex == index,
                    onClick = {
                        vm.updateCategoryIndex(index)
                    },
                    selectedContentColor = Color(0xFF149EE7),
                    unselectedContentColor = Color(0xFF666666),
                ) {
                    Text(
                        text = category.title,
                        modifier =
                            Modifier
                                .padding(vertical = 8.dp)
                                .placeholder(visible = !vm.categoryLoaded, color = Color.LightGray),
                        fontSize = 14.sp,
                    )
                }
            }
        }

        TabRow(
            selectedTabIndex = vm.currentTypeIndex,
            backgroundColor = Color.Transparent,
            contentColor = Color(0xFF149EE7),
            indicator = {
            },
            divider = {},
        ) {
            vm.types.forEachIndexed { index, dataType ->
                LeadingIconTab(
                    selected = vm.currentTypeIndex == index,
                    onClick = {
                        vm.updateTypeIndex(index)
                    },
                    selectedContentColor = Color(0xFF149EE7),
                    unselectedContentColor = Color(0xFF666666),
                    icon = {
                        Icon(imageVector = dataType.icon, contentDescription = null)
                    },
                    text = {
                        Text(
                            text = dataType.title,
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontSize = 16.sp,
                        )
                    },
                )
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = articleViewModel.refreshing),
            onRefresh = {
                coroutinScope.launch {
                    articleViewModel.refresh()
                }
            },
        ) {
            LazyColumn(state = lazyListState) {
                item {
                    // 轮播图
                    SwiperContent(vm = vm)
                }
                item {
                    // 通知公告
                    NotificationContent(vm)
                }

                if (vm.showArticleList) {
                    // 文章列表
                    items(articleViewModel.list) { article ->
                        ArticleItem(
                            article,
                            articleViewModel.listLoader,
                            modifier = Modifier.clickable { onNavigateToArticle.invoke() },
                        )
                    }
                } else {
                    // 视频列表
                    items(videoViewModel.list) { videoEntity ->
                        VideoItem(
                            modifier =
                                Modifier.clickable {
                                    onNavigateToVideo()
                                },
                            videoEntity,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StudyScreenPreview() {
    StudyScreen()
}

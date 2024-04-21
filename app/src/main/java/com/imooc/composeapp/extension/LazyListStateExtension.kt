package com.imooc.composeapp.extension

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable

fun LazyListState.OnBottomReached(loadMore: () -> Unit) {
    // 是否应该加载更多的状态
    val shouldLoadMore = remember {
        // 由另一个状态计算派生
        derivedStateOf {
            // 获取最后显示的item
            val lastVisibleItem =
                layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf true
            // 如果最后显示的item是最后一个item的话，说明已经触底，需要加载更多
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1

        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore.value) {
            loadMore()
        }
    }

}
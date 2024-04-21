package com.imooc.composeapp.ui.components


import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.imooc.composeapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwiperContent(vm: MainViewModel) {
    // 虚拟页数
    val virtualCount = Int.MAX_VALUE
    // 实际的页数
    val actualCount = vm.swiperData.size

    // 初始图片下标
    val initialIndex = virtualCount / 2

    val pagerState = rememberPagerState(initialPage = initialIndex)

    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        val timer = Timer()
        coroutineScope.launch {
            vm.swiperData()
        }
        timer.schedule(object : TimerTask() {
            override fun run() {
                if(vm.swiperLoaded){
                    //需要执行的任务
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)

                    }
                }

            }
        }, 3000, 3000)

        onDispose {
            timer.cancel()
        }
    }
    // 轮播图
    HorizontalPager(
        count = virtualCount,
        state = pagerState,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
    , userScrollEnabled = vm.swiperLoaded
    ) { index ->
        val actulIndex =
            (index - initialIndex).floorMod(actualCount) //index - (index.floorDiv(actualCount)) * actualCount
        AsyncImage(
            model = vm.swiperData[actulIndex].imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7 / 3f)
            ,
            contentScale = ContentScale.Crop
        )
    }
}


fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@Preview
@Composable
fun SwiperContentPreview(vm: MainViewModel = viewModel()) {
    SwiperContent(vm)
}


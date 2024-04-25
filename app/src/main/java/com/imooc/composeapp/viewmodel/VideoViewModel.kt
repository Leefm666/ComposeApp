package com.imooc.composeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imooc.composeapp.model.entity.VideoEntity
import com.imooc.composeapp.model.service.VideoService

class VideoViewModel : ViewModel() {
    private val videoService = VideoService.instance()

    var list by mutableStateOf(
        listOf(
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
            VideoEntity(
                title = "当下国内仍处于AI产业趋势的早期，也就是硬件阶段。从业绩角度考虑，硬件设施作为“卖铲人”，订单增长确定性更大。",
                type = "视频课程",
                duration = "00:02:00",
                imageUrl = "https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg",
            ),
        ),
    )
        private set

    private val pageSize = 10
    private var pageOffset = 1

    var refreshing by mutableStateOf(false)
        private set

    var listLoaded by mutableStateOf(false)
        private set

    suspend fun fetchList() {
        val res = videoService.list(pageOffset, pageSize)
        if (res.code == 0 && res.data != null) {
            val tmpList = mutableListOf<VideoEntity>()
            if (pageOffset != 1) {
                tmpList.addAll(list)
            }
            tmpList.addAll(res.data)
            hasMore = res.data.size == pageSize
            list = tmpList
            refreshing = false
            listLoaded = true
        }
    }

    suspend fun refresh() {
        pageOffset = 1
        refreshing = true
        fetchList()
    }

    private var hasMore = false

    suspend fun loadMore() {
        if (hasMore) {
        }
        pageOffset++
        refresh()
    }

    private var videoTitle by mutableStateOf("视频标题视频标题视频标题视频标题")

    // html头部
    private val htmlHeader =
        """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Document</title>
        </head>
        <body>
        """.trimIndent()

    // html尾部
    private val htmlFooter =
        """
        </body>
        </html>
        """.trimIndent()

    var content =
        """
        $htmlHeader
        <h5 style="color:#333333;font-size:32px;">$videoTitle</h5>
        $htmlFooter
        """.trimIndent()

    var videoDesc by mutableStateOf(content)

    var videoUrl by mutableStateOf("https://media.w3.org/2010/05/sintel/trailer.mp4")
        private set

    var coverUrl by mutableStateOf("https://media.w3.org/2010/05/sintel/trailer.mp4")
        private set

    var infoLoaded by mutableStateOf(
        false,
    )
        private set

    suspend fun fetchInfo() {
        val res = videoService.info("")
        if (res.code == 0 && res.data != null) {
            val videoEntity = res.data
            videoTitle = videoEntity.title
            coverUrl = videoEntity.imageUrl
            videoUrl = videoEntity.video ?: ""
            videoDesc =
                """
                $htmlHeader
                <h5 style="color:#333333;font-size:32px;">$videoTitle</h5>
                ${videoEntity.desc}
                $htmlFooter
                """.trimIndent()
        }
        infoLoaded = true
    }
}

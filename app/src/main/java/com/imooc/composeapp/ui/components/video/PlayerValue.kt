package com.imooc.composeapp.ui.components.video

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.parcelize.Parcelize

/**
 *
 * 播放器相关数据类
 * @author 10130
 * @date 2024/04/10
 * @constructor 创建[PlayerValue]
 */
@Parcelize
class PlayerValue : Parcelable {


    var coverUrl: String = ""

    var title: String = ""

    // 存储url 目的是为了横竖屏切换等重绘的场景
    var videoUrl: String = ""


    // 视频总时长
    var duration by mutableStateOf(0L)

    // 当前播放进度
    var currentPosition by mutableStateOf(0L)

    // 当前状态
    var state by mutableStateOf(PlayState.None)
}


/**
 * 播放状态
 *
 */
enum class PlayState {
    None, // 未播放
    Loading, // 加载中
    Playing, // 播放中
    Pause // 已暂停

}
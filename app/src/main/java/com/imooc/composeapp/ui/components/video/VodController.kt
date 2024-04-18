package com.imooc.composeapp.ui.components.video

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.tencent.rtmp.ITXVodPlayListener
import com.tencent.rtmp.TXLiveConstants
import com.tencent.rtmp.TXVodPlayer

class VodController(
    context: Context,
    val playValue: PlayerValue
) {


    val vodPlayer = TXVodPlayer(context).apply {
        setVodListener(object : ITXVodPlayListener {
            override fun onPlayEvent(player: TXVodPlayer?, event: Int, param: Bundle?) {
                when (event) {
                    // 当前是什么状态
                    TXLiveConstants.PLAY_EVT_PLAY_LOADING -> {
                        playValue.state = PlayState.Loading
                    }

                    TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED,
                    TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME,
                    TXLiveConstants.PLAY_EVT_VOD_LOADING_END,
                    TXLiveConstants.PLAY_EVT_PLAY_BEGIN
                    -> {
                        playValue.state = PlayState.Playing
                    }
                    // 获取视频时长和进度
                    TXLiveConstants.PLAY_EVT_PLAY_PROGRESS -> {
                        playValue.duration =
                            param?.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS)?.toLong() ?: 0L
                        playValue.currentPosition =
                            param?.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS)?.toLong() ?: 0L
                    }
                }
            }

            override fun onNetStatus(player: TXVodPlayer?, param: Bundle?) {

            }

        })
    }


    /**
     * 开始播放
     * @param [url] url
     */
    fun startPlay() {
        playValue.videoUrl = playValue.videoUrl
        vodPlayer.startVodPlay(playValue.videoUrl)
    }

    /**
     * 停止播放
     */
    fun stopPlay() {
        vodPlayer.stopPlay(true)
    }

    /**
     * 暂停播放
     */
    fun pause() {
        vodPlayer.pause()
        playValue.state = PlayState.Pause
    }


    /**
     * 恢复播放
     *
     */
    fun resume() {
        vodPlayer.resume()
    }


    /**
     * 配置改变后重新播放
     */
    fun restore() {
        vodPlayer.setStartTime(playValue.currentPosition / 1000f)
        startPlay()
    }

    /**
     * 设置播放进度
     *
     */
    fun seekTo(millSeconds: Long) {
        vodPlayer.seek((millSeconds / 1000).toInt())
    }

}


@Composable
fun rememberVodController(
    context: Context = LocalContext.current,
    videoUrl: String,
    coverUrl: String? = "",
    title: String? = ""
) = rememberSaveable(saver = object : Saver<VodController, PlayerValue> {
    override fun restore(value: PlayerValue): VodController? {
        return VodController(context, value)
    }

    override fun SaverScope.save(controller: VodController): PlayerValue {
        return controller.playValue
    }


}) {
    val playValue = PlayerValue()
    playValue.videoUrl = videoUrl
    playValue.coverUrl = coverUrl ?: ""
    playValue.title = title ?: ""
    return@rememberSaveable VodController(context, playValue)

}


/*remember {
    VodController(context, videoUrl, coverUrl)
}*/
package com.imooc.composeapp.ui.components.video

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.imooc.composeapp.R
import com.tencent.rtmp.TXVodPlayer
import com.tencent.rtmp.ui.TXCloudVideoView

@Composable
fun VideoView(vodPlayer: TXVodPlayer) {
    AndroidView(factory = { context ->
        (
            LayoutInflater.from(context).inflate(R.layout.video, null, false)
                .findViewById(R.id.videoView) as TXCloudVideoView
        ).apply {
            vodPlayer.setPlayerView(this)
        }
    })
}

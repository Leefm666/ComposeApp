package com.imooc.composeapp.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoEntity(
    val title: String,
    val type: String? = "",
    val duration: String,
    @Json(name = "cover")
    val imageUrl: String,
    val video: String? = "",
    val desc: String? = "",
)

data class VideoListResponse(val data: List<VideoEntity>?) : BaseReponse()

data class VideInfoResponse(val data: VideoEntity?) : BaseReponse()

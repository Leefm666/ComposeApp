package com.imooc.composeapp.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleEntity(
    val title: String,
    var source: String,
    @Json(name = "time") var timestamp: String,
    var content: String? = "",
)

data class ArticleListResponse(var data: List<ArticleEntity>) : BaseReponse()

data class ArticleInfoResponse(val data: ArticleEntity?) : BaseReponse()

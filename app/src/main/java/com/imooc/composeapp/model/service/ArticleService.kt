package com.imooc.composeapp.model.service

import com.imooc.composeapp.model.Network
import com.imooc.composeapp.model.entity.ArticleListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("article/list")
    suspend fun list(
        @Query("pageOffset") pageOffset: Int,
        @Query("pageSize") pageSize: Int,
    ): ArticleListResponse

    companion object {
        fun instance(): ArticleService {
            return Network.createService(ArticleService::class.java)
        }
    }
}

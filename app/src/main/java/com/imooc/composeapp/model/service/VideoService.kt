package com.imooc.composeapp.model.service

import com.imooc.composeapp.model.Network
import com.imooc.composeapp.model.entity.VideInfoResponse
import com.imooc.composeapp.model.entity.VideoListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {
    @GET("video/list")
    suspend fun list(
        @Query("pageOffset") pageOffset: Int,
        @Query("pageSize") pageSize: Int,
    ): VideoListResponse

    @GET("video/info")
    suspend fun info(
        @Query("id") id: String,
    ): VideInfoResponse

    companion object {
        fun instance(): VideoService {
            return Network.createService(VideoService::class.java)
        }
    }
}

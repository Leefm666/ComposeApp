package com.imooc.composeapp.model.service

import com.imooc.composeapp.model.Network
import com.imooc.composeapp.model.entity.CategoryResponse
import retrofit2.http.GET

interface HomeService {

    @GET("category/list")
    suspend fun category(): CategoryResponse


    companion object {
        fun instance(): HomeService {
            return Network.createService(HomeService::class.java)
        }
    }
}
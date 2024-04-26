package com.imooc.composeapp.model.service

import com.imooc.composeapp.model.Network
import com.imooc.composeapp.model.entity.UserInfoResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @FormUrlEncoded
    @POST("user/signIn")
    suspend fun signIn(
        @Field("userName") userName: String,
        @Field("password") password: String,
    ): UserInfoResponse

    companion object {
        fun instance(): UserService {
            return Network.createService(UserService::class.java)
        }
    }
}

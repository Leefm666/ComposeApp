package com.imooc.composeapp.model.entity

data class UserInfoEntity(val userName: String)

data class UserInfoResponse(val data: UserInfoEntity?) : BaseReponse()

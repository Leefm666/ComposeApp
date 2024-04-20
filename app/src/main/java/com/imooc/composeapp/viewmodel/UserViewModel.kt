package com.imooc.composeapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imooc.composeapp.model.UserInfoManager
import com.imooc.composeapp.model.entity.UserInfoEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class UserViewModel(context: Context) : ViewModel() {


    private val userInfoManager = UserInfoManager(context)

    var userInfo: UserInfoEntity? = null
        private set

    // DataStore // SharedPreference


    init {
        // 其实这里可以使用DataStore的对象存储，直接存储整个对象
        viewModelScope.launch {
            val userName = userInfoManager.userName.firstOrNull()
            userInfo = if (userName?.isNotEmpty() == true) {
                UserInfoEntity(userName)
            } else {
                null
            }
        }
    }

    // 是否已登陆
    val logged: Boolean
        get() {
            return userInfo != null
        }

    /**
     * 登陆操作
     */
    fun login(onClose: () -> Unit) {
        // 模拟网路请求数据回传
        userInfo = UserInfoEntity("user001")
        viewModelScope.launch {
            userInfoManager.save("user001")
        }
        onClose
    }

    fun clear() {
        viewModelScope.launch {
            userInfoManager.clear() // 请求本地存储
            userInfo = null // 置空内存数据
        }
    }

}
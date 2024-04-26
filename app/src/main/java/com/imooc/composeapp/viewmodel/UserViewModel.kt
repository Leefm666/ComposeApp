package com.imooc.composeapp.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imooc.composeapp.model.entity.UserInfoEntity
import com.imooc.composeapp.model.service.UserInfoManager
import com.imooc.composeapp.model.service.UserService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.security.MessageDigest

class UserViewModel(context: Context) : ViewModel() {
    private val userInfoManager = UserInfoManager(context)

    var userInfo: UserInfoEntity? = null
        private set

    private val userService = UserService.instance()

    var userName by
        mutableStateOf("")

    var passWord by
        mutableStateOf("")

    var error by mutableStateOf("")
        private set

    // DataStore // SharedPreference

    init {
        // 其实这里可以使用DataStore的对象存储，直接存储整个对象
        viewModelScope.launch {
            val userName = userInfoManager.userName.firstOrNull()
            userInfo =
                if (userName?.isNotEmpty() == true) {
                    UserInfoEntity(userName)
                } else {
                    null
                }
        }
    }

    // 是否正在登陆
    var loading by mutableStateOf(false)
        private set

    // 是否已登陆
    val logged: Boolean
        get() {
            return userInfo != null
        }

    /**
     * 登陆操作
     */
    suspend fun login(onClose: () -> Unit) {
        error = ""
        loading = true
        // 模拟网路请求数据回传
        val res = userService.signIn(userName, md5(passWord))
        if (res.code == 0 && res.data != null) {
            userInfo = res.data
//            userInfoManager.save(userName)
            onClose
        } else {
            // 失败

            error = res.message
        }
        loading = false
    }

    fun clear() {
        viewModelScope.launch {
            userInfoManager.clear() // 请求本地存储
            userInfo = null // 置空内存数据
        }
    }

    fun md5(content: String): String {
        val hash = MessageDigest.getInstance("MD5").digest(content.toByteArray())
        val hex = StringBuilder(hash.size * 2)
        for (b in hash) {
            var str = Integer.toHexString(b.toInt())
            if (b < 0x10) {
                str = "0$str"
            }
            hex.append(str.substring(str.length - 2))
        }
        return hex.toString()
    }
}

package com.imooc.composeapp.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserInfoManager(private val context: Context) {

    companion object {
        private val Context.userStore: DataStore<Preferences> by preferencesDataStore("user_store")

        val LOGGER = booleanPreferencesKey("LOGGER")
        val USERNAME = stringPreferencesKey("USERNAME")

    }


    val logged: Flow<Boolean> = context.userStore.data.map {
        it[LOGGER] ?: false
    }

    val userName: Flow<String> = context.userStore.data.map {
        it[USERNAME] ?: ""
    }

    /**
     * 存储用户信息
     * @param [userName] 入参
     */
    suspend fun save(userName: String) {
        context.userStore.edit {

        }
    }


    suspend fun clear() {
        context.userStore.edit {
            it[LOGGER] = false
            it[USERNAME] = ""
        }
    }



}


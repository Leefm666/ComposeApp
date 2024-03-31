package com.imooc.composeapp.model.entity

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 导航栏对象
 * @author 10130
 * @date 2024/03/18
 * @constructor 创建[NavigationItem]
 * @param [title]
 * @param [icon]
 */
data class NavigationItem(
    val title: String, // 导航栏标题
    val icon: ImageVector // 底部导航栏图标
)

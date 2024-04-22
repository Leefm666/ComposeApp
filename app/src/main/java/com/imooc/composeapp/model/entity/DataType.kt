package com.imooc.composeapp.model.entity

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 数据类型
 * @author 10130
 * @date 2024/03/24
 * @constructor 创建[DataType]
 * @param [title] 标题
 * @param [icon] 标签
 */
data class DataType(
    var title: String,
    var icon: ImageVector,
)

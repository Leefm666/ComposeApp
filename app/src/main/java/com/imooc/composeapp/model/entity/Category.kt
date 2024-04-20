package com.imooc.composeapp.model.entity

/**
 * 分类
 * @author 10130
 * @date 2024/03/23
 * @constructor 创建[Category]
 * @param [title]
 */
data class Category(
    val title: String,

    val id: String
)

/**
 * 分类
 * @author 10130
 * @date 2024/04/20
 * @constructor 创建[CategoryResponse]
 * @param [data]
 */
data class CategoryResponse(var data: List<Category>) : BaseReponse() {

}

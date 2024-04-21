package com.imooc.composeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imooc.composeapp.model.entity.ArticleEntity
import com.imooc.composeapp.model.service.ArticleService

class ArticleViewModel : ViewModel() {

    private val articleService = ArticleService.instance()


    val pageSize = 10

    private val pageOffset = 1

    // 新闻列表数据
    var list by mutableStateOf(
        listOf(
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            ), ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10"
            )
        )
    )
        private set

    // 第一页文章列表是否加载完成
    var listLoader by mutableStateOf(false)
        private set

    suspend fun fetchArticleList() {
        val res = articleService.list(pageOffset = pageOffset, pageSize = pageSize)
        if (res.code == 0 && res.data != null) {
            list = res.data
            listLoader = true
        }
    }


    // html头部
    private val htmlHeader = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Document</title>
        </head>
        <body>
    """.trimIndent()

    // html尾部
    private val htmlFooter = """
        </body>
        </html>
    """.trimIndent()


    var content = """${htmlHeader}
        <div><h1>Header</h1></div>
        ${htmlFooter}
    """.trimIndent()

}
package com.imooc.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.imooc.composeapp.model.entity.ArticleEntity

class ArticleViewModel : ViewModel() {
    // 新闻列表数据
    var list = listOf(
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
        private set

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
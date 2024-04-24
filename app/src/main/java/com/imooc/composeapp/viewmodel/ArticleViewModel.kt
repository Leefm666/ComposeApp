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

    private var pageOffset = 1

    // 是否正在刷新
    var refreshing by mutableStateOf(false)
        private set

    // 新闻列表数据
    var list by mutableStateOf(
        listOf(
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
            ArticleEntity(
                title = "什么是Side Effect?",
                source = "网站",
                timestamp = "2020-02-10",
            ),
        ),
    )
        private set

    // 第一页文章列表是否加载完成
    var listLoader by mutableStateOf(false)
        private set

    // 是否还有更多
    private var hasMore = false

    suspend fun fetchArticleList() {
        val res = articleService.list(pageOffset = pageOffset, pageSize = pageSize)
        if (res.code == 0 && res.data != null) {
            val tmpList = mutableListOf<ArticleEntity>()
            if (pageOffset != 1) {
                tmpList.addAll(list)
            }
            tmpList.addAll(res.data)
            // 是否还有更多数据
            hasMore = res.data.size == pageSize
            list = tmpList
            listLoader = true
            refreshing = false
        } else {
            pageOffset--
            if (pageOffset <= 1) {
                pageOffset = 1
            }
        }
    }

    suspend fun refresh() {
        pageOffset = 1
//        listLoader = false
        refreshing = true
        fetchArticleList()
    }

    suspend fun loadMore() {
        if (hasMore) {
            pageOffset++
            fetchArticleList()
        }
    }

    // html头部
    private val htmlHeader =
        """
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
    private val htmlFooter =
        """
        </body>
        </html>
        """.trimIndent()
    private var articleEntity: ArticleEntity? = null

    var content by mutableStateOf(
        """
        $htmlHeader
        ${articleEntity?.content ?: ""}
        $htmlFooter
        """.trimIndent(),
    )

    suspend fun fetchInfo() {
        val res = articleService.info("")
        if (res.code == 0 && res.data != null) {
            articleEntity = res.data
            content =
                """
                $htmlHeader
                ${articleEntity?.content ?: ""}
                $htmlFooter
                """.trimIndent()
        }
    }
}

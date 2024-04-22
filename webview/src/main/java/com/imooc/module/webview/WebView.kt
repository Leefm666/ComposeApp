package com.imooc.module.webview

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun WebView(state: WebViewState) {
    var webView by remember {
        mutableStateOf<WebView?>(null)
    }
    // webview 变化或state变化时重新订阅流数据

    LaunchedEffect(webView, state) {
        with(state) {
            // 订阅流
            webView?.handleEvents()
        }
    }
    AndroidView(factory = { context ->
        WebView(context).apply {
            webChromeClient =
                object : WebChromeClient() {
                    override fun onReceivedTitle(
                        view: WebView?,
                        title: String?,
                    ) {
                        super.onReceivedTitle(view, title)
                        state.pageTitle = title
                    }
                }

            webViewClient =
                object : WebViewClient() {
                    override fun onPageStarted(
                        view: WebView?,
                        url: String?,
                        favicon: Bitmap?,
                    ) {
                        super.onPageStarted(view, url, favicon)
                        state.pageTitle = null
                    }
                }
            with(settings) {
                javaScriptEnabled = true
            }
        }.also {
            webView = it
        }
    }) { view ->
        when (val content = state.content) {
            is WebContent.Url -> {
                val url = content.url
                // url 不空或者当前的webView加载的url不相同
                if (url.isNotEmpty() && url != view.url) {
                    view.loadUrl(content.url)
                }
            }

            is WebContent.Data -> {
                view.loadDataWithBaseURL(content.baseUrl, content.data, null, "utf-8", null)
            }
        }
    }
}

sealed class WebContent() {
    data class Url(val url: String) : WebContent()

    data class Data(val data: String, val baseUrl: String? = null) : WebContent()
}

class WebViewState(private val coroutinScope: CoroutineScope, webContent: WebContent) {
    // 网页内容 url 或者 data(html 内容)
    var content by mutableStateOf(webContent)

    // todo 遗留问题调用范围问题
    var pageTitle: String? by mutableStateOf(null)
        internal set

    // 事件类型
    private enum class EventType {
        EVALUATE_JAVASCRIPT, // 执行js方法
    }

    // 共享流的数据类型
    private class Event(val type: EventType, val args: String, val callback: ((String) -> Unit)?)

    // 共享流
    private val events: MutableSharedFlow<Event> = MutableSharedFlow()

    // 异步回调
    internal suspend fun WebView.handleEvents(): Unit =
        withContext(Dispatchers.Main) {
            events.collect { event ->
                when (event.type) {
                    EventType.EVALUATE_JAVASCRIPT -> evaluateJavascript(event.args, event.callback)
                }
            }
        }

    // 执行js方法
    fun evaluateJavascript(
        script: String,
        resultCallback: ((String) -> Unit)? = {},
    ) {
        val event = Event(EventType.EVALUATE_JAVASCRIPT, script, resultCallback)
        coroutinScope.launch {
            events.emit(event) // 推送流
        }
    }
}

@Composable
fun rememberWebViewState(
    coroutinScope: CoroutineScope = rememberCoroutineScope(),
    url: String,
) = remember(key1 = url) {
    WebViewState(coroutinScope, WebContent.Url(url))
}

@Composable
fun rememberWebViewState(
    coroutinScope: CoroutineScope = rememberCoroutineScope(),
    data: String,
    baseUrl: String? = null,
) = remember(key1 = data, key2 = baseUrl) {
    WebViewState(coroutinScope, WebContent.Data(data, baseUrl))
}

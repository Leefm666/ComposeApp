package com.imooc.composeapp.ui.screens


import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleDetailScreen() {
    Scaffold(
        topBar = {TopAppBar(title = { Text(text = "文章详情") })}
    ) {
        Text(text = "这是文章详情页")
    }
}


@Preview
@Composable
fun ArticleDetailScreenPreview() {
    ArticleDetailScreen()
}


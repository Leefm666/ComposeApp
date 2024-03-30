package com.imooc.composeapp.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.imooc.composeapp.ui.components.TopAppBar

@Composable
fun MineScreen() {
    Column() {
        TopAppBar() {
            Text(text = "我的页面")
        }
        Text(text = "我的页面")
    }

}

@Preview
@Composable
fun MineScreenPreview() {
    MineScreen()
}


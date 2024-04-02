package com.imooc.composeapp.ui.screens


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imooc.composeapp.R
import com.imooc.composeapp.ui.components.TopAppBar

@Composable
fun MineScreen() {
    Column() {
        val menus = listOf(MenuItem(R.drawable.baseline_elderly_woman_24, "学习积分"))

        TopAppBar() {
            Text(text = "我的", fontSize = 18.sp, color = Color.White)
        }
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            // 头像部分
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img), contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(62.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .height(62.dp)
                    ) {
                        Text(text = "未登录", color = Color(0xFF333333), fontSize = 18.sp)
                        Text(text = "以坚持学习0天", color = Color(0xFF999999), fontSize = 12.sp)
                    }
                }
            }
            // 菜单部分

        }
    }

}

data class MenuItem(@DrawableRes val icon: Int, val title: String)

@Preview
@Composable
fun MineScreenPreview() {
    MineScreen()
}


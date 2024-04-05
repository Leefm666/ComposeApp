package com.imooc.composeapp.ui.components


import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.imooc.composeapp.ui.navigation.Destinations
import com.imooc.composeapp.ui.screens.ArticleDetailScreen
import com.imooc.composeapp.ui.screens.MainFrame

/**
 *  导航控制器
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHostApp() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Destinations.HomeFrame.route
    ) {
        // 首页大框架
        composable(Destinations.HomeFrame.route, enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left
            )
        }) {
            MainFrame(onNavigateToArticle = {
                Log.i("+++++++++==", "NavHostApp: ")
                navController.navigate(Destinations.ArticleDetail.route)
            })
        }
        // 文章详情页
        composable(Destinations.ArticleDetail.route, enterTransition = {
            slideIntoContainer(AnimatedContentScope.SlideDirection.Left)
        }, exitTransition = {
            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right)
        }) {
            ArticleDetailScreen()
        }
    }
}

@Preview
@Composable
fun NavHostAppPreview() {
    NavHostApp()
}


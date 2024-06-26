package com.imooc.composeapp.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imooc.composeapp.model.entity.Category
import com.imooc.composeapp.model.entity.DataType
import com.imooc.composeapp.model.entity.SwiperEntity
import com.imooc.composeapp.model.service.HomeService

class MainViewModel : ViewModel() {
    private val homeService = HomeService.instance()

    // 分类数据是否加载成功
    var categoryLoaded by mutableStateOf(false)
        private set

    // 分类数据
    var categories by mutableStateOf(
        listOf(
            Category("思想政治1", "1"),
            Category("法律法规2", "2"),
            Category("职业道德3", "3"),
            Category("诚信自律4", "4"),
        ),
    )
        private set

    suspend fun categoryData() {
        val categoryResponse = homeService.category()
        if (categoryResponse.code == 0) {
            categories = categoryResponse.data
            categoryLoaded = true
        } else {
            // 不成功的情况下 读取message
            val message = categoryResponse.message
        }
    }

    // 当前分类下标
    var categoryIndex by mutableStateOf(0)
        private set

    /**
     * 更新分类下标
     * @param [index]
     */
    fun updateCategoryIndex(index: Int) {
        categoryIndex = index
    }

    // 类型数据
    val types by mutableStateOf(
        listOf(
            DataType("相关资讯", Icons.Default.Description),
            DataType("视频课程", Icons.Default.SmartDisplay),
        ),
    )

    // 当前类型下标
    var currentTypeIndex by mutableStateOf(0)
        private set

    // 是否显示文章列表
    var showArticleList by mutableStateOf(true)
        private set

    /**
     * 更新类型下标
     * @param [index] 下标
     */
    fun updateTypeIndex(index: Int) {
        currentTypeIndex = index
        showArticleList = currentTypeIndex == 0
    }

    // 轮播图数据
    var swiperData by mutableStateOf(
        listOf(
            SwiperEntity("https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg"),
            SwiperEntity("https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg"),
            SwiperEntity("https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg"),
            SwiperEntity("https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg"),
            SwiperEntity("https://scpic.chinaz.net/files/default/imgs/2024-03-22/ab89240f49d09119.jpg"),
        ),
    )
        private set

    var swiperLoaded by mutableStateOf(false)
        private set

    suspend fun swiperData() {
        val swiperReponse = homeService.banner()
        if (swiperReponse.code == 0 && swiperReponse.data != null) {
            swiperData = swiperReponse.data
            swiperLoaded = true
        } else {
            val message = swiperReponse.message
        }
    }

    val notifications =
        listOf(
            "问：据悉，中国就美国《通胀削减法》及其实施细则中新能源汽车补贴等措施在世贸组织提起诉讼。请问商务部有何评论？",
            "答：为维护中方新能源汽车企业利益和全球新能源汽车产业公平竞争环境，3月26日，中国就美国《通胀削减法》有关新能源汽车补贴等措施诉诸世界贸易组织争端解决机制。",
            "美方以“应对气候变化”“低碳环保”为名，出台《通胀削减法》及其实施细则，以使用美国等特定地区产品作为补贴前提，针对新能源汽车等制定歧视性补贴政策，将中国等世贸组织成员产品排除在外，扭曲了公平竞争，严重扰乱了全球新能源汽车产业链和供应链，违反了世贸组织国民待遇、最惠国待遇等规则，中方坚决反对。",
            "中方坚定捍卫以规则为基础的多边贸易体制，尊重世贸组织成员在规则框架下实施产业补贴，促进本国经济社会发展的正当权利。我们敦促美方遵守世贸组织规则，尊重全球新能源汽车产业发展趋势，及时纠正歧视性产业政策，维护新能源汽车全球产业链和供应链稳定。",
        )
}

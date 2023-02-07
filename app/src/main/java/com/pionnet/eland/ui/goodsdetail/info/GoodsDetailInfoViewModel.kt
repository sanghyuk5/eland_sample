package com.pionnet.eland.ui.goodsdetail.info

import androidx.lifecycle.MutableLiveData
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.model.*
import com.pionnet.eland.ui.goodsdetail.holder.ViewEntity
import com.pionnet.eland.ui.viewholder.MarginEntity
import com.pionnet.eland.utils.toPx

class GoodsDetailInfoViewModel : BaseViewModel() {

    val result = MutableLiveData<MutableList<ViewTypeDataSet>>()

    fun createModules(data: GoodsDetailData.Data) {
        val module = mutableListOf<ViewTypeDataSet>()

        module.add(ViewTypeDataSet(ViewType.FIRST, data.goodsDetail))
        module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        if (!data.sellerRecommendGoods.isNullOrEmpty()) {
            module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))
            module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "판매자 추천 상품", isLineVisible = true)))
            module.add(ViewTypeDataSet(ViewType.SECOND, data.sellerRecommendGoods))
        }
        if (!data.tagList.isNullOrEmpty()) {
            module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))
            module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "태그", isLineVisible = true)))
            module.add(ViewTypeDataSet(ViewType.THIRD, data.tagList))
        }
        if (!data.sellerPopularGoods.isNullOrEmpty()) {
            module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))
            module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "이 판매자의 인기 상품", isLineVisible = true)))
            module.add(ViewTypeDataSet(ViewType.FOURTH, data.sellerPopularGoods))
        }
        if (!data.recommendGoods.isNullOrEmpty()) {
            module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))
            module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "이 상품은 어때요?", isLineVisible = true)))
            module.add(ViewTypeDataSet(ViewType.FIFTH, data.recommendGoods))
        }
        if (data.popularGoods != null) {
            module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))
            module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, end = 15.toPx, title = "${data.popularGoods.title} 인기 상품", isMoreVisible = true, isLineVisible = true)))
            module.add(ViewTypeDataSet(ViewType.SIXTH, data.popularGoods.goods))
        }

        module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 120.toPx, color = "#ffffff")))

        result.postValue(module)
    }
}
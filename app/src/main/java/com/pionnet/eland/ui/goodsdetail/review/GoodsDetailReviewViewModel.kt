package com.pionnet.eland.ui.goodsdetail.review

import androidx.lifecycle.MutableLiveData
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.data.*
import com.pionnet.eland.ui.goodsdetail.holder.ViewEntity
import com.pionnet.eland.ui.viewholder.MarginEntity
import com.pionnet.eland.utils.toPx

class GoodsDetailReviewViewModel : BaseViewModel() {

    val result = MutableLiveData<MutableList<ViewTypeDataSet>>()

    fun createModules(data: GoodsDetailData.Data.GoodsReviewInfo) {
        val module = mutableListOf<ViewTypeDataSet>()

        if (data.reviewInfo != null) {
            module.add(ViewTypeDataSet(ViewType.FIRST, data.reviewInfo.reviewImageInfo))
            module.add(ViewTypeDataSet(ViewType.SIXTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))


            module.add(ViewTypeDataSet(ViewType.SECOND, ViewEntity(height = 50.toPx, start = 15.toPx, title = "포토 리뷰 (${data.reviewInfo.reviewImageInfo?.reviewCount})", isLineVisible = true)))

            data.reviewInfo.reviewImageInfo?.reviewList?.let { reviewList ->
                if (data.reviewInfo.reviewImageInfo.reviewCount?.compareTo(3) == 1) {
                    module.add(ViewTypeDataSet(ViewType.THIRD, reviewList.subList(0, 3)))
                    module.add(ViewTypeDataSet(ViewType.FOURTH, data.reviewInfo.reviewImageInfo.reviewMoreUrl))
                } else {
                    module.add(ViewTypeDataSet(ViewType.THIRD, reviewList))
                }
            }

            module.add(ViewTypeDataSet(ViewType.SIXTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

            module.add(ViewTypeDataSet(ViewType.SECOND, ViewEntity(height = 50.toPx, start = 15.toPx, title = "텍스트 리뷰 (${data.reviewInfo.reviewTextInfo?.reviewCount})", isLineVisible = true)))

            data.reviewInfo.reviewTextInfo?.reviewList?.let { reviewList ->
                if (data.reviewInfo.reviewTextInfo.reviewCount?.compareTo(3) == 1) {
                    module.add(ViewTypeDataSet(ViewType.FIFTH, reviewList.subList(0, 3)))
                    module.add(ViewTypeDataSet(ViewType.FOURTH, data.reviewInfo.reviewTextInfo.reviewMoreUrl))
                } else {
                    module.add(ViewTypeDataSet(ViewType.FIFTH, reviewList))
                }
            }

            module.add(ViewTypeDataSet(ViewType.SIXTH, MarginEntity(height = 120.toPx, color = "#ffffff")))
        }

        result.postValue(module)
    }
}
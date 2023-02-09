package com.pionnet.eland.ui.leftmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.model.*
import com.pionnet.eland.ui.goodsdetail.holder.ViewEntity
import com.pionnet.eland.ui.viewholder.MarginEntity
import com.pionnet.eland.utils.toPx
import kotlinx.coroutines.launch

class LeftMenuViewModel : BaseViewModel() {
    private val repository by lazy { LeftMenuRepository() }

    val result = MutableLiveData<List<ViewTypeDataSet>>()
    var topMenuList: List<LeftMenuData.Data.TopMenu> ?= null

    fun requestData() {
        viewModelScope.launch {
            repository.requestLeftMenuStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            data.topMenuList?.let {
                                topMenuList = it
                            }

                            result.value = createLeftMenuModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun createLeftMenuModules(data: LeftMenuData.Data): List<ViewTypeDataSet> {
        val module = mutableListOf<ViewTypeDataSet>()

        module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "최근 본 상품")))
        module.add(ViewTypeDataSet(ViewType.FIRST, data.recentlyList))
        module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        var categoryMenuList = mutableListOf<LeftMenuData.Data.Category.CategoryMenu>()
        data.categoryList?.let { categoryList ->
            categoryList[0].categoryMenu?.let {
                categoryMenuList = it.toMutableList()

                val addItemSize = 5 - it.size % 5
                for (i: Int in 0 until addItemSize) {
                    val data = LeftMenuData.Data.Category.CategoryMenu()
                    categoryMenuList.add(data)
                }
            }
        }

        module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "카테고리")))
        module.add(ViewTypeDataSet(ViewType.SECOND, categoryMenuList))
        module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "브랜드")))
        module.add(ViewTypeDataSet(ViewType.THIRD, data.brandList))
        module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "추천 전문관")))
        module.add(ViewTypeDataSet(ViewType.FOURTH, data.shopList))
        module.add(ViewTypeDataSet(ViewType.EIGHTH, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        module.add(ViewTypeDataSet(ViewType.SEVENTH, ViewEntity(height = 50.toPx, start = 15.toPx, title = "서비스 메뉴")))
        module.add(ViewTypeDataSet(ViewType.FIFTH, data.serviceMenuList))

        module.add(ViewTypeDataSet(ViewType.SIXTH))

        return module
    }
}
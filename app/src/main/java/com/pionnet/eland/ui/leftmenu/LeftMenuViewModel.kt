package com.pionnet.eland.ui.leftmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.model.LeftMenuData
import com.pionnet.eland.model.LeftMenuDataSet
import com.pionnet.eland.model.LeftMenuViewType
import com.pionnet.eland.ui.viewholder.MarginEntity
import com.pionnet.eland.utils.toPx
import kotlinx.coroutines.launch

class LeftMenuViewModel : BaseViewModel() {
    private val repository by lazy { LeftMenuRepository() }

    private val leftMenuLiveData = MutableLiveData<List<LeftMenuDataSet>>()
    val leftMenuDataSet: LiveData<List<LeftMenuDataSet>> = leftMenuLiveData
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

                            leftMenuLiveData.value = createLeftMenuModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun createLeftMenuModules(data: LeftMenuData.Data): List<LeftMenuDataSet> {
        val module = mutableListOf<LeftMenuDataSet>()

        module.add(LeftMenuDataSet(LeftMenuViewType.RECENT, data.recentlyList))
        module.add(LeftMenuDataSet(LeftMenuViewType.DIVIDER, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

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

        module.add(LeftMenuDataSet(LeftMenuViewType.CATEGORY, categoryMenuList))
        module.add(LeftMenuDataSet(LeftMenuViewType.DIVIDER, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        module.add(LeftMenuDataSet(LeftMenuViewType.BRAND, data.brandList))
        module.add(LeftMenuDataSet(LeftMenuViewType.DIVIDER, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        module.add(LeftMenuDataSet(LeftMenuViewType.SHOP, data.shopList))
        module.add(LeftMenuDataSet(LeftMenuViewType.DIVIDER, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        module.add(LeftMenuDataSet(LeftMenuViewType.SERVICE, data.serviceMenuList))

        module.add(LeftMenuDataSet(LeftMenuViewType.BOTTOM))

        return module
    }
}
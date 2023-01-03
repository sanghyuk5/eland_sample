package com.pionnet.eland.ui.main.tabBest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.*
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class BestViewModel : CommonViewModel() {
    private val repository by lazy { BestRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var bestCategoryList = listOf<BestData.Data.CategoryList>()

    override fun requestData() {
        viewModelScope.launch {
            repository.requestBestStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setBestModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setBestModules(data: BestData.Data) {
        if (!data.categoryList.isNullOrEmpty()) {
            bestCategoryList = data.categoryList
            val categoryList = data.categoryList.mapIndexed { index, item ->
                Category(imageUrl = item.image, title = item.name, isSelected = index == 0)
            }

            moduleList.add(
                ModuleData.CommonCategoryTab(categoryList, "best")
            )
        }

        if (data.goodsInfo != null && !data.goodsInfo.goods.isNullOrEmpty()) {
            val newList = mutableListOf<ModuleData>()
            addItemWithGoods(newList, data.goodsInfo.goods)
            moduleList.addAll(newList)
        }

        result.postValue(moduleList)
    }

    private fun addItemWithGoods(
        moduleList: MutableList<ModuleData>,
        list: List<Goods>
    ) {
        list.chunked(2).forEachIndexed { index, item ->
            moduleList.add(
                ModuleData.CommonGoodsGridData("best", item, index, isRank = true)
            )
        }
    }
}
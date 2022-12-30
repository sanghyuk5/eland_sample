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
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let { bestData ->
                        if (!bestData.categoryList.isNullOrEmpty()) {
                            bestCategoryList = bestData.categoryList!!
                            val categoryList = bestData.categoryList!!.mapIndexed { index, item ->
                                Category(imageUrl = item.image, title = item.name, isSelected = index == 0)
                            }

                            moduleList.add(
                                ModuleData.CommonCategoryTab(
                                    categoryList
                                )
                            )
                        }

                        if (bestData.goodsInfo != null && !bestData.goodsInfo!!.goods.isNullOrEmpty()) {
                            val newList = mutableListOf<ModuleData>()
                            addItemWithGoods(newList, bestData.goodsInfo!!.goods!!)
                            moduleList.addAll(newList)
                        }

                        result.postValue(moduleList)
                    }

                }
            }
        }
    }

    private fun addItemWithGoods(
        moduleList: MutableList<ModuleData>,
        list: List<Goods>
    ) {
        list.chunked(2).forEachIndexed { index, item ->
            moduleList.add(
                ModuleData.CommonGoodsGridData(
                    "best", item, index, isRank = true
                )
            )
        }
    }
}
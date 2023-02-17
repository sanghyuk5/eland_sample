package com.pionnet.eland.ui.main.tabLuckyDeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.data.Category
import com.pionnet.eland.data.Goods
import com.pionnet.eland.data.LuckyDealData
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class LuckyDealViewModel : CommonViewModel() {
    private val repository by lazy { LuckyDealRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()
    val goodsResult = MutableLiveData<MutableList<ModuleData>>()
    val tabResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var luckyCategoryList = listOf<LuckyDealData.Data.CategoryList>()

    override fun requestData() {
        viewModelScope.launch {
            repository.requestLucyDealStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setLuckyModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setLuckyModules(data: LuckyDealData.Data) {
        moduleList.clear()

        if (!data.goodsList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonCenterTitleData("오늘의 럭키딜")
            )

            data.goodsList.forEach { goods ->
                moduleList.add(
                    ModuleData.CommonLuckyDealGoods(goods)
                )
            }
        }

        if (!data.categoryList.isNullOrEmpty()) {
            luckyCategoryList = data.categoryList
            val categoryList = data.categoryList.mapIndexed { index, item ->
                Category(imageUrl = item.image, title = item.name, isSelected = index == 0)
            }

            moduleList.add(
                ModuleData.CommonCategoryTab(categoryList, "lucky")
            )
        }

        result.postValue(moduleList)
    }

    fun requestGoodsData() {
        val dataSet = moduleList.map { it.clone() }.toMutableList()
        viewModelScope.launch {
            repository.requestLucyDealGoodsStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            if (data.goodsInfo != null) {
                                moduleList.forEachIndexed { index, item ->
                                    when(item) {
                                        is ModuleData.CommonCategoryTab -> {
                                            val newList = mutableListOf<ModuleData>()
                                            addItemWithGoods(newList, data.goodsInfo.goods!!)
                                            dataSet.addAll(index + 1, newList)
                                        }
                                    }
                                }
                                goodsResult.postValue(dataSet)
                            }
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    fun setTabGoodsItem(selectedPosition: Int) {
        val dataSet = moduleList.map { it.clone() }.toMutableList()

        moduleList.forEachIndexed { index, item ->
            when(item) {
                is ModuleData.CommonCategoryTab -> {
                    val categoryList = luckyCategoryList.mapIndexed { index, category ->
                        Category(imageUrl = category.image, title = category.name, isSelected = index == selectedPosition)
                    }

                    dataSet[index] = ModuleData.CommonCategoryTab(categoryList, "lucky")
                }
            }
        }

        tabResult.postValue(dataSet)
    }

    private fun addItemWithGoods(
        moduleList: MutableList<ModuleData>,
        list: List<Goods>
    ) {
        list.chunked(2).forEach {
            moduleList.add(
                ModuleData.CommonGoodsGridData("lucky", it, 0)
            )
        }
    }
}
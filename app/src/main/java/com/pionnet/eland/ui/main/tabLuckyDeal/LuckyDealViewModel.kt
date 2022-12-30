package com.pionnet.eland.ui.main.tabLuckyDeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.Category
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.Status
import com.pionnet.eland.model.LuckyDealData
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
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let { luckyDealData ->
                        if (!luckyDealData.goodsList.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonCenterTitleData("오늘의 럭키딜")
                            )

                            luckyDealData.goodsList!!.forEach { goods ->
                                moduleList.add(
                                    ModuleData.HomeLuckyDealGoodsData(
                                        goods
                                    )
                                )
                            }
                        }

                        if (!luckyDealData.categoryList.isNullOrEmpty()) {
                            luckyCategoryList = luckyDealData.categoryList!!
                            val categoryList = luckyDealData.categoryList!!.mapIndexed { index, item ->
                                Category(imageUrl = item.image, title = item.name, isSelected = index == 0)
                            }

                            moduleList.add(
                                ModuleData.CommonCategoryTab(
                                    categoryList
                                )
                            )
                        }

                        result.postValue(moduleList)
                    }

                }
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

                    dataSet[index] = ModuleData.CommonCategoryTab(categoryList)
                }
            }
        }

        tabResult.postValue(dataSet)
    }

    fun requestGoodsData() {
        val dataSet = moduleList.map { it.clone() }.toMutableList()
        viewModelScope.launch {
            repository.requestLucyDealGoodsStream().collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let { luckyDealGoodsData ->
                        if (luckyDealGoodsData.goodsInfo != null) {
                            moduleList.forEachIndexed { index, item ->
                                when(item) {
                                    is ModuleData.CommonCategoryTab -> {
                                        val newList = mutableListOf<ModuleData>()
                                        addItemWithGoods(newList, luckyDealGoodsData.goodsInfo!!.goods!!)
                                        dataSet.addAll(index + 1, newList)
                                    }
                                }
                            }
                        }

                        goodsResult.postValue(dataSet)
                    }
                } else if (it.status == Status.ERROR) {
                    goodsResult.postValue(dataSet)
                }
            }
        }
    }

    private fun addItemWithGoods(
        moduleList: MutableList<ModuleData>,
        list: List<Goods>
    ) {
        list.chunked(2).forEach {
            moduleList.add(
                ModuleData.CommonGoodsGridData(
                    "lucky", it, 0
                )
            )
        }
    }
}
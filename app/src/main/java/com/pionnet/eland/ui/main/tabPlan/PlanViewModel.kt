package com.pionnet.eland.ui.main.tabPlan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pionnet.eland.model.*
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class PlanViewModel : CommonViewModel() {
    private val repository by lazy { PlanRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()
    private var tabModuleList = mutableListOf<ModuleData>()

    private var planCategoryList = listOf<PlanData.Data.CategoryList>()

    private var pageNo = 1
    private var isLoadingMore: Boolean = false

    override fun requestData() {
        viewModelScope.launch {
            repository.requestPlanStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setPlanModules(data)
                            setTabGoodsItem(0)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setPlanModules(data: PlanData.Data) {
        moduleList.clear()

        if (!data.categoryList.isNullOrEmpty()) {
            planCategoryList = data.categoryList
            val categoryList = data.categoryList.mapIndexed { index, item ->
                Category(imageUrl = item.image, title = item.name, isSelected = index == 0)
            }

            moduleList.add(
                ModuleData.CommonCategoryTab(categoryList, "plan")
            )
        }

        result.postValue(moduleList)
        isLoadingMore = false
    }

    fun setTabGoodsItem(selectedPosition: Int) {
        pageNo = 1
        tabModuleList.clear()

        viewModelScope.launch {
            repository.requestPlanTabStream(selectedPosition).collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setPlanTabModules(data, selectedPosition)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setPlanTabModules(data: PlanData.Data, selectedPosition: Int) {
        tabModuleList = moduleList.map { it.clone() }.toMutableList()

        moduleList.forEachIndexed { index, item ->
            when(item) {
                is ModuleData.CommonCategoryTab -> {
                    val categoryList = planCategoryList.mapIndexed { index, item ->
                        Category(imageUrl = item.image, title = item.name, isSelected = index == selectedPosition)
                    }

                    tabModuleList[index] = ModuleData.CommonCategoryTab(categoryList, "plan")

                    if (!data.planList.isNullOrEmpty()) {
                        data.planList.forEach {
                            tabModuleList.add(
                                ModuleData.PlanGoodsData(it.goods, it.imageUrl, it.linkUrl)
                            )
                        }
                    }
                }
            }
        }

        result.postValue(tabModuleList)
    }

    fun loadMore() {
        if (isLoadingMore) {
            return
        }

        isLoadingMore = true
        pageNo++

        requestPageData(pageNo)
    }

    private fun requestPageData(pageNo: Int) {
        viewModelScope.launch {
            repository.requestPlanPageStream(pageNo).collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setPlanPageModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setPlanPageModules(data: PlanData.Data) {
        val newList = mutableListOf<ModuleData>()
        addItemWithGoods(newList, data.planList!!)

        tabModuleList = tabModuleList.map { it.clone() }.toMutableList()
        tabModuleList.addAll(tabModuleList.size, newList)

        result.postValue(tabModuleList)
        isLoadingMore = false
    }

    private fun addItemWithGoods(
        moduleList: MutableList<ModuleData>,
        list: List<PlanData.Data.PlanList>
    ) {
        list.forEach {
            moduleList.add(
                ModuleData.PlanGoodsData(it.goods, it.imageUrl, it.linkUrl)
            )
        }
    }
}
package com.pionnet.eland.ui.main.tabEkids

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.EKidsData
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.Status
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class EKidsViewModel : CommonViewModel() {
    private val repository by lazy { EKidsRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()
    val tabResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var weeklyBestGroup = listOf<EKidsData.Data.ExpandGroup.Group>()
    private var newArrivalGroup = listOf<EKidsData.Data.ExpandGroup.Group>()

    private var weeklyBestGoodsList = listOf<Goods>()
    private var newArrivalGoodsList = listOf<Goods>()

    private var tabClickViewType = "weeklyBest"
    private var selectedPosition = 0
    private var indexAddNewArrivalGoods = 0
    private var isWeeklyBestClick = false
    private var isNewArrivalClick = false

    override fun requestData() {
        viewModelScope.launch {
            repository.requestEKidsStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setEKidsModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setEKidsModules(data: EKidsData.Data) {
        if (!data.mainBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMainBanner(data.mainBanner)
            )
        }

        if (!data.category.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.EKidsCategoryData(data.category)
            )
        }

        if (!data.bandBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMultiBannerData(
                    data.bandBanner,
                    isShowDivide = false
                )
            )
        }

        if (!data.subBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMultiBannerData(
                    data.subBanner,
                    isShowDivide = false
                )
            )
        }

        if (data.specialDeal != null) {
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    data.specialDeal.title ?: "이번주 특가"
                )
            )

            data.specialDeal.goods?.forEach { goods ->
                moduleList.add(
                    ModuleData.EKidsCategoryGoodsData(
                        goods
                    )
                )
            }
        }

        if (data.brandStory != null) {
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    data.brandStory.title ?: "Brand Shop"
                )
            )

            moduleList.add(
                ModuleData.EKidsBrandData(
                    data.brandStory.banner!!
                )
            )
        }

        if (data.weeklyBest != null) {
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    data.weeklyBest.title ?: "MD추천"
                )
            )

            data.weeklyBest.group?.let { group ->
                group[0].isSelected = true

                weeklyBestGroup = group
                weeklyBestGoodsList = group[0].goods!!

                moduleList.add(
                    ModuleData.EKidsRecommendCategoryData(
                        group,
                        "weeklyBest"
                    )
                )
            }
        }

        if (data.newArrival != null) {
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    data.newArrival.title ?: "MD추천 신상"
                )
            )

            data.newArrival.group?.let { group ->
                group[0].isSelected = true

                newArrivalGroup = group
                newArrivalGoodsList = group[0].goods!!

                moduleList.add(
                    ModuleData.EKidsRecommendCategoryData(
                        group,
                        "newArrival"
                    )
                )
            }
        }

        result.postValue(moduleList)
    }

    fun setGoodsView() {
        indexAddNewArrivalGoods = weeklyBestGoodsList.take(20).chunked(2).size

        setView()
    }

    fun setExpandableGoodsView(isClickMore: Boolean, viewType: String) {
        if (viewType == "weeklyBest") {
            isWeeklyBestClick = isClickMore
        } else {
            isNewArrivalClick = isClickMore
        }

        indexAddNewArrivalGoods = if (isWeeklyBestClick) {
            weeklyBestGoodsList.chunked(2).size
        } else {
            weeklyBestGoodsList.take(20).chunked(2).size
        }

        setView()
    }

    fun setTabGoodsView(selectedPosition: Int, viewType: String) {
        this.selectedPosition = selectedPosition

        if (viewType == "weeklyBest") {
            tabClickViewType = "weeklyBest"
            isWeeklyBestClick = false
            weeklyBestGoodsList = weeklyBestGroup[selectedPosition].goods!!
            indexAddNewArrivalGoods = weeklyBestGoodsList.take(20).chunked(2).size
        } else {
            tabClickViewType = "newArrival"
            isNewArrivalClick = false
            newArrivalGoodsList = newArrivalGroup[selectedPosition].goods!!
        }

        setView()
    }

    private fun setView() {
        val dataSet = moduleList.map { it.clone() }.toMutableList()
        moduleList.forEachIndexed { index, item ->
            when(item) {
                is ModuleData.EKidsRecommendCategoryData -> {
                    if (item.viewType == "weeklyBest") {
                        if (tabClickViewType == "weeklyBest") {
                            weeklyBestGroup.mapIndexed { index, group ->
                                group.isSelected = index == selectedPosition
                            }

                            dataSet[index] = ModuleData.EKidsRecommendCategoryData(weeklyBestGroup, "weeklyBest")
                        }

                        val newList = mutableListOf<ModuleData>()
                        val goodsList = if (isWeeklyBestClick) weeklyBestGoodsList else weeklyBestGoodsList.take(20)
                        addItemWithGoods(newList, goodsList, true, isWeeklyBestClick, "weeklyBest")
                        dataSet.addAll(index + 1, newList)
                    } else if (item.viewType == "newArrival") {
                        if (tabClickViewType == "newArrival") {
                            newArrivalGroup.mapIndexed { index, group ->
                                group.isSelected = index == selectedPosition
                            }

                            dataSet[index] = ModuleData.EKidsRecommendCategoryData(newArrivalGroup, "newArrival")
                        }

                        val newList = mutableListOf<ModuleData>()
                        val goodsList = if (isNewArrivalClick) newArrivalGoodsList else newArrivalGoodsList.take(20)
                        addItemWithGoods(newList, goodsList, true, isNewArrivalClick, "newArrival")
                        dataSet.addAll(index + indexAddNewArrivalGoods + 2, newList)
                    }
                }
            }
        }

        tabResult.postValue(dataSet)
    }

    private fun addItemWithGoods(
        moduleList: MutableList<ModuleData>,
        list: List<Goods>,
        isExistMore: Boolean,
        isClickMore: Boolean,
        viewType: String
    ) {
        list.chunked(2).forEach {
            moduleList.add(
                ModuleData.CommonGoodsGridData("ekids", it, 0)
            )
        }

        if (isExistMore) {
            moduleList.add(
                ModuleData.EKidsExpandableData(!isClickMore, viewType)
            )
        }
    }
}
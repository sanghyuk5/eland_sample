package com.pionnet.eland.ui.main.tabEkids

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pionnet.eland.model.EKidsData
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.Status
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class EKidsViewModel : CommonViewModel() {
    private val repository by lazy { EKidsRepository() }

    val eKidsResult = MutableLiveData<MutableList<ModuleData>>()
    val tabResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var weeklyBestGroup = listOf<EKidsData.Data.ExpandGroup.Group>()
    private var newArrivalGroup = listOf<EKidsData.Data.ExpandGroup.Group>()

    private var weeklyBestGoodsList = listOf<Goods>()
    private var newArrivalGoodsList = listOf<Goods>()

    private var indexAddNewArrivalGoods = 0
    private var isWeeklyBestClick = false
    private var isNewArrivalClick = false

    override fun requestData() {
        viewModelScope.launch {
            repository.requestEKidsStream().collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let { eKidsData ->
                        if (!eKidsData.mainBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonMainBanner(
                                    eKidsData.mainBanner!!
                                )
                            )
                        }

                        if (!eKidsData.category.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.EKidsCategoryData(
                                    eKidsData.category!!
                                )
                            )
                        }

                        if (!eKidsData.bandBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonMultiBannerData(
                                    eKidsData.bandBanner!!,
                                    isShowDivide = false
                                )
                            )
                        }

                        if (!eKidsData.subBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonMultiBannerData(
                                    eKidsData.subBanner!!,
                                    isShowDivide = false
                                )
                            )
                        }

                        if (eKidsData.specialDeal != null) {
                            moduleList.add(
                                ModuleData.CommonCenterTitleData(
                                    eKidsData.specialDeal!!.title ?: "이번주 특가"
                                )
                            )

                            eKidsData.specialDeal!!.goods?.forEach { goods ->
                                moduleList.add(
                                    ModuleData.EKidsCategoryGoodsData(
                                        goods
                                    )
                                )
                            }
                        }

                        if (eKidsData.brandStory != null) {
                            moduleList.add(
                                ModuleData.CommonCenterTitleData(
                                    eKidsData.brandStory!!.title ?: "Brand Shop"
                                )
                            )

                            moduleList.add(
                                ModuleData.EKidsBrandData(
                                    eKidsData.brandStory!!.banner!!
                                )
                            )
                        }

                        if (eKidsData.weeklyBest != null) {
                            moduleList.add(
                                ModuleData.CommonCenterTitleData(
                                    eKidsData.weeklyBest!!.title ?: "MD추천"
                                )
                            )

                            eKidsData.weeklyBest!!.group?.let { group ->
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

                        if (eKidsData.newArrival != null) {
                            moduleList.add(
                                ModuleData.CommonCenterTitleData(
                                    eKidsData.newArrival!!.title ?: "MD추천 신상"
                                )
                            )

                            eKidsData.newArrival!!.group?.let { group ->
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

                        eKidsResult.postValue(moduleList)
                    }
                } else if (it.status == Status.ERROR) {

                }
            }
        }
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
        if (viewType == "weeklyBest") {
            isWeeklyBestClick = false
            weeklyBestGoodsList = weeklyBestGroup[selectedPosition].goods!!
            indexAddNewArrivalGoods = weeklyBestGoodsList.take(20).chunked(2).size
        } else {
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
                        val newList = mutableListOf<ModuleData>()
                        val goodsList = if (isWeeklyBestClick) weeklyBestGoodsList else weeklyBestGoodsList.take(20)
                        addItemWithGoods(newList, goodsList, true, isWeeklyBestClick, "weeklyBest")
                        dataSet.addAll(index + 1, newList)
                    } else if (item.viewType == "newArrival") {
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
                ModuleData.CommonGoodsGridData(
                    "ekids", it, 0
                )
            )
        }

        if (isExistMore) {
            moduleList.add(
                ModuleData.EKidsExpandableData(!isClickMore, viewType)
            )
        }
    }
}
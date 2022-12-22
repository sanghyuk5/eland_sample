package com.pionnet.eland.ui.main.tabPlanDetail

import androidx.lifecycle.*
import com.pionnet.eland.model.PlanDetailData
import com.pionnet.eland.model.Status
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class PlanDetailViewModel : CommonViewModel() {
    private val repository by lazy { PlanDetailRepository() }

    val planResult = MutableLiveData<MutableList<ModuleData>>()
    val sortResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()
    var shopName = ""
    val tabList = mutableListOf<String>()
    private var goodsInfo = listOf<PlanDetailData.Data.GoodsInfo>()

    var sortPosition = 0
    var viewType = "grid"
    private var clickCount = 0

    override fun requestData() {
        viewModelScope.launch {
            repository.requestPlanDetailStream().collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let { planData ->
                        if (planData.shopInfo != null) {
                            shopName = planData.shopInfo!!.name.toString()
                            moduleList.add(
                                ModuleData.CommonCenterTitleData(
                                    shopName
                                )
                            )

                            moduleList.add(
                                ModuleData.CommonWebViewData(
                                    planData.shopInfo!!
                                )
                            )
                        }

                        if (!planData.tabList.isNullOrEmpty()) {
                            tabList.clear()
                            tabList.add("전체")
                            planData.tabList!!.forEach { planTabList ->
                                planTabList.name?.let { it -> tabList.add(it) }
                            }

                            moduleList.add(
                                ModuleData.CommonSortData(
                                    tabList,
                                    sortPosition,
                                    viewType
                                )
                            )
                        }

                        if (!planData.goodsInfo.isNullOrEmpty()) {
                            goodsInfo = planData.goodsInfo!!
                        }

                        planResult.postValue(moduleList)
                    }
                } else if (it.status == Status.ERROR) {

                }
            }
        }
    }

    fun setGoodsView(isClick: Boolean) {
        if (!isClick) clickCount += 1

        viewType = if (clickCount % 3 == 1) {
            "linear"
        } else if (clickCount % 3 == 2) {
            "large"
        } else {
            clickCount = 0
            "grid"
        }

        val dataSet = moduleList.map { it.clone() }.toMutableList()
        moduleList.forEachIndexed { index, item ->
            when(item) {
                is ModuleData.CommonSortData -> {
                    dataSet[index] = ModuleData.CommonSortData(tabList, sortPosition, viewType)

                    when (viewType) {
                        "linear" -> {
                            goodsInfo.forEachIndexed { index, goodsInfo ->
                                dataSet.add(
                                    ModuleData.PlanTabTitleData(
                                        goodsInfo.name ?: "",
                                        goodsInfo.goodsList?.size ?: 0
                                    )
                                )

                                goodsInfo.goodsList?.forEach {
                                    dataSet.add(
                                        ModuleData.CommonGoodsLinearData(
                                            it,
                                            index
                                        )
                                    )
                                }
                            }
                        }
                        "large" -> {
                            goodsInfo.forEachIndexed { index, goodsInfo ->
                                dataSet.add(
                                    ModuleData.PlanTabTitleData(
                                        goodsInfo.name ?: "",
                                        goodsInfo.goodsList?.size ?: 0
                                    )
                                )

                                goodsInfo.goodsList?.forEach {
                                    dataSet.add(
                                        ModuleData.CommonGoodsLargeData(
                                            it,
                                            index
                                        )
                                    )
                                }
                            }
                        }
                        else -> {
                            goodsInfo.forEachIndexed { index, goodsInfo ->
                                dataSet.add(
                                    ModuleData.PlanTabTitleData(
                                        goodsInfo.name ?: "",
                                        goodsInfo.goodsList?.size ?: 0
                                    )
                                )

                                goodsInfo.goodsList?.chunked(2)?.forEach {
                                    dataSet.add(
                                        ModuleData.CommonGoodsGridData(
                                            "planDetail",
                                            it,
                                            index
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        sortResult.postValue(dataSet)
    }
}
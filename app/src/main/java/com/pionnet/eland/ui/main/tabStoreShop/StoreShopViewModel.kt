package com.pionnet.eland.ui.main.tabStoreShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.Status
import com.pionnet.eland.model.StorePickData
import com.pionnet.eland.model.StoreShopData
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class StoreShopViewModel(private val params: String) : CommonViewModel() {
    private val repository by lazy { StoreShopRepository() }

    val storeShopResult = MutableLiveData<MutableList<ModuleData>>()
    val storePickResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var categoryNo: String? = null
    private var smartPickData = listOf<StoreShopData.Data.SmartPick>()

    override fun requestData() {
        viewModelScope.launch {
            repository.requestStoreShopStream(params).collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.let { storeShopData ->
                        if (!storeShopData.data.mainBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.MainBannerData(
                                    storeShopData.data.mainBanner!!
                                )
                            )
                        }

                        if (storeShopData.data.delivery != null) {
                            moduleList.add(
                                ModuleData.StoreShopDeliveryData(
                                    storeShopData.data.delivery!!
                                )
                            )
                        }

                        if (!storeShopData.data.recommend.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.StoreShopRecommendData(
                                    storeShopData.data.recommend!!
                                )
                            )
                        }

                        if (storeShopData.data.regular != null) { //size가 0이여도 그려야함.
                            moduleList.add(
                                ModuleData.StoreShopRegularStoreData(
                                    storeShopData.data.regular!!
                                )
                            )
                        }

                        if (!storeShopData.data.smartPick.isNullOrEmpty()) {
                            categoryNo = storeShopData.data.smartPick!![0].categoryNo
                            smartPickData = storeShopData.data.smartPick!!
                            moduleList.add(
                                ModuleData.StoreShopSmartPickData(
                                    smartPickData,
                                    mutableListOf()
                                )
                            )
                        }

                        if (!storeShopData.data.categoryGoods.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.HomeTitleData(
                                    "카테고리별 베스트 상품",
                                    ""
                                )
                            )
                            moduleList.add(
                                ModuleData.StoreShopCategoryData(
                                    storeShopData.data.categoryGoods!!
                                )
                            )

                            storeShopData.data.categoryGoods!!.forEach { categoryGoods ->
                                moduleList.add(
                                    ModuleData.StoreShopCategoryTitleData(
                                        categoryGoods.ctgNm ?: ""
                                    )
                                )
                                moduleList.add(
                                    ModuleData.StoreShopCategoryGoodData(
                                        categoryGoods.goodsList!!
                                    )
                                )
                            }
                        }

                        storeShopResult.postValue(moduleList)
                    }
                }
            }
        }
    }

    fun requestStorePickData() {
        viewModelScope.launch {
            repository.requestStorePickStream(categoryNo).collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.let { storePickData ->
                        if (storePickData.data.keywordResult != null && !storePickData.data.keywordResult!!.goodsList.isNullOrEmpty()) {
                            val dataSet = moduleList.map { it.clone() }.toMutableList()
                            moduleList.forEachIndexed { index, item ->
                                when(item) {
                                    is ModuleData.StoreShopSmartPickData -> {
                                        dataSet[index] = ModuleData.StoreShopSmartPickData(smartPickData, storePickData.data.keywordResult!!.goodsList!!)
                                    }
                                }
                            }

                            storePickResult.postValue(dataSet)
                        }
                    }
                }
            }
        }
    }
}
package com.pionnet.eland.ui.main.tabStoreShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.Status
import com.pionnet.eland.model.StoreShopData
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class StoreShopViewModel(private val params: String) : CommonViewModel() {
    private val repository by lazy { StoreShopRepository() }

    val storeShopResult = MutableLiveData<MutableList<ModuleData>>()
    val regularHolderResult = MutableLiveData<MutableList<ModuleData>>()
    val smartPickHolderResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var isRegular = false

    var pickNo: String? = null
    var pickName = ""
    var sort = 1
    private var clickCount = 0
    private var viewType = "grid"

    private var regularData = listOf<StoreShopData.Data.Regular>()
    private var regularGoodsData = listOf<Goods>()

    private var smartPickData = listOf<StoreShopData.Data.SmartPick>()
    private var smartPickGoodsData = listOf<Goods>()

    var categoryGoodsCount = 0

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
                            regularData = storeShopData.data.regular!!
                            moduleList.add(
                                ModuleData.StoreShopRegularStoreData(
                                    regularData,
                                    regularGoodsData,
                                    false
                                )
                            )
                        }

                        if (!storeShopData.data.smartPick.isNullOrEmpty()) {
                            pickNo = storeShopData.data.smartPick!![0].categoryNo
                            pickName = storeShopData.data.smartPick!![0].name ?: ""

                            smartPickData = storeShopData.data.smartPick!!
                            storeShopData.data.smartPick!![0].isSelected = true

                            moduleList.add(
                                ModuleData.StoreShopSmartPickData(
                                    smartPickData,
                                    pickName,
                                    1,
                                    "grid"
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

                            storeShopData.data.categoryGoods!![0].isSelected = true
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

                                if (!categoryGoods.goodsList.isNullOrEmpty()) {
                                    categoryGoodsCount = categoryGoods.goodsList!!.size / 2
                                    categoryGoods.goodsList!!.chunked(2).forEach {
                                        moduleList.add(
                                            ModuleData.CommonGoodGridData(
                                                it
                                            )
                                        )
                                    }
                                }

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
            repository.requestStorePickStream(pickNo).collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.let { storePickData ->
                        if (storePickData.data.keywordResult != null && !storePickData.data.keywordResult!!.goodsList.isNullOrEmpty()) {
                            smartPickGoodsData = storePickData.data.keywordResult!!.goodsList!!

                            smartPickHolderResult.postValue(getViewType())
                        }
                    }
                }
            }
        }
    }

    fun setView() {
        clickCount += 1

        viewType = if (clickCount % 3 == 1) {
            "linear"
        } else if (clickCount % 3 == 2) {
            "large"
        } else {
            clickCount = 0
            "grid"
        }

        smartPickHolderResult.postValue(getViewType())
    }

    fun requestRegularData() {
        isRegular = true
        regularHolderResult.postValue(getViewType())
    }

    private fun getViewType(): MutableList<ModuleData> {
        val dataSet = moduleList.map { it.clone() }.toMutableList()
        moduleList.forEachIndexed { index, item ->
            when(item) {
                is ModuleData.StoreShopRegularStoreData -> {
                    if (isRegular) {
                        regularGoodsData = smartPickGoodsData.chunked(10)[0]

                        dataSet[index] = ModuleData.StoreShopRegularStoreData(regularData, regularGoodsData, true)
                    }
                }

                is ModuleData.StoreShopSmartPickData -> {
                    dataSet[index] = ModuleData.StoreShopSmartPickData(smartPickData, pickName, sort, viewType)

                    when (viewType) {
                        "linear" -> {
                            smartPickGoodsData.forEachIndexed { addIndex, addItem ->
                                dataSet.add(
                                    index + addIndex + 1,
                                    ModuleData.CommonGoodLinearData(addItem)
                                )
                            }

                            dataSet.add(
                                index + smartPickGoodsData.size + 1,
                                ModuleData.StoreShopSmartPickNameData(pickName, pickNo)
                            )
                        }
                        "large" -> {
                            smartPickGoodsData.forEachIndexed { addIndex, addItem ->
                                dataSet.add(
                                    index + addIndex + 1,
                                    ModuleData.CommonGoodLargeData(addItem)
                                )
                            }

                            dataSet.add(
                                index + smartPickGoodsData.size + 1,
                                ModuleData.StoreShopSmartPickNameData(pickName, pickNo)
                            )
                        }
                        else -> {
                            smartPickGoodsData.chunked(2).forEachIndexed { addIndex, addItem ->
                                dataSet.add(
                                    index + addIndex + 1,
                                    ModuleData.CommonGoodGridData(addItem)
                                )
                            }

                            dataSet.add(
                                index + smartPickGoodsData.chunked(2).size + 1,
                                ModuleData.StoreShopSmartPickNameData(pickName, pickNo)
                            )
                        }
                    }
                }
            }
        }

        return dataSet
    }
}
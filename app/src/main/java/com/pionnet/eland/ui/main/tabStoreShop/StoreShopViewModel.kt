package com.pionnet.eland.ui.main.tabStoreShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.Status
import com.pionnet.eland.model.StoreShopData
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class StoreShopViewModel(private val params: String) : CommonViewModel() {
    private val repository by lazy { StoreShopRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()
    val regularHolderResult = MutableLiveData<MutableList<ModuleData>>()
    val sortResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var isRegular = false

    var pickNo: String? = null
    var pickName = ""
    var sortPosition = 1
    private var viewShape = "grid"
    private var clickCount = 0

    private var regularData = listOf<StoreShopData.Data.Regular>()
    private var regularGoodsData = listOf<Goods>()

    private var smartPickData = listOf<StoreShopData.Data.SmartPick>()
    private var smartPickGoodsData = listOf<Goods>()

    var categoryGoodsCount = 0

    override fun requestData() {
        viewModelScope.launch {
            repository.requestStoreShopStream(params).collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setStoreShopModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setStoreShopModules(data: StoreShopData.Data) {
        moduleList.clear()

        if (!data.mainBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMainBanner(data.mainBanner)
            )
        }

        if (data.delivery != null) {
            moduleList.add(
                ModuleData.StoreShopDeliveryData(data.delivery)
            )
        }

        if (!data.recommend.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonTitleData("?????? ??????", "")
            )

            moduleList.add(
                ModuleData.StoreShopRecommendData(data.recommend)
            )
        }

        if (data.regular != null) { //size??? 0????????? ????????????.
            moduleList.add(
                ModuleData.CommonTitleData("?????? ????????????", "")
            )

            regularData = data.regular
            moduleList.add(
                ModuleData.StoreShopRegularStoreData(regularData, regularGoodsData, false)
            )
        }

        if (!data.smartPick.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonTitleData("???????????? ??????", "???????????? ?????? ???????????? ??????????????????.")
            )

            smartPickData = data.smartPick
            pickName = data.smartPick[0].name ?: ""
            pickNo = data.smartPick[0].categoryNo
            data.smartPick[0].isSelected = true

            moduleList.add(
                ModuleData.StoreShopPickSearchData(smartPickData, pickName)
            )

            moduleList.add(
                ModuleData.CommonSortData(null, 1, "storeShop", "grid")
            )
        }

        if (!data.categoryGoods.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonTitleData("??????????????? ????????? ??????", "")
            )

            data.categoryGoods[0].isSelected = true
            moduleList.add(
                ModuleData.StoreShopCategoryData(data.categoryGoods)
            )

            data.categoryGoods.forEach { categoryGoods ->
                moduleList.add(
                    ModuleData.StoreShopCategoryTitleData(categoryGoods.ctgNm ?: "")
                )

                if (!categoryGoods.goodsList.isNullOrEmpty()) {
                    categoryGoodsCount = categoryGoods.goodsList.size / 2
                    categoryGoods.goodsList.chunked(2).forEachIndexed { index, goodsInfo ->
                        moduleList.add(
                            ModuleData.CommonGoodsGridData("storeShop", goodsInfo, index)
                        )
                    }
                }

            }
        }

        result.postValue(moduleList)
    }

    fun requestStorePickData() {
        viewModelScope.launch {
            repository.requestStorePickStream(pickNo).collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            if (data.keywordResult != null && !data.keywordResult.goodsList.isNullOrEmpty()) {
                                smartPickGoodsData = data.keywordResult.goodsList

                                sortResult.postValue(getViewType())
                            }
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    fun setGoodsView() {
        clickCount += 1

        viewShape = if (clickCount % 3 == 1) {
            "linear"
        } else if (clickCount % 3 == 2) {
            "large"
        } else {
            clickCount = 0
            "grid"
        }

        sortResult.postValue(getViewType())
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

                is ModuleData.StoreShopPickSearchData -> {
                    dataSet[index] = ModuleData.StoreShopPickSearchData(smartPickData, pickName)
                }

                is ModuleData.CommonSortData -> {
                    dataSet[index] = ModuleData.CommonSortData(null, sortPosition, "storeShop", viewShape)

                    when (viewShape) {
                        "linear" -> {
                            smartPickGoodsData.forEachIndexed { addIndex, addItem ->
                                dataSet.add(
                                    index + addIndex + 1,
                                    ModuleData.CommonGoodsLinearData(addItem, addIndex)
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
                                    ModuleData.CommonGoodsLargeData(addItem, addIndex)
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
                                    ModuleData.CommonGoodsGridData("storeShop", addItem, addIndex)
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
package com.pionnet.eland.ui.main.tabEShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pionnet.eland.model.*
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.ui.main.tabBest.BestRepository
import com.pionnet.eland.utils.removeRange
import kotlinx.coroutines.launch
import java.text.FieldPosition

class EShopViewModel : CommonViewModel() {
    private val repository by lazy { EShopRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()
    val tabResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var issueGroup = listOf<EShopData.Data.Group>()
    private var arrivalGroup = listOf<EShopData.Data.Group>()

    private var issueGoodsList = listOf<Goods>()
    private var arrivalGoodsList = listOf<Goods>()

    private var issueClickCount = 1
    private var arrivalClickCount = 1

    private var tabClickViewType = "issue"
    private var selectedPosition = 0
    private var indexAddArrivalItems = 0

    private var issueBannerExist = false

    override fun requestData() {
        viewModelScope.launch {
            repository.requestEShopStream().collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let { eShopData ->
                        if (!eShopData.mainBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonMainBanner(
                                    eShopData.mainBanner!!
                                )
                            )
                        }

                        if (!eShopData.subBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonMultiBannerData(
                                    eShopData.subBanner!!
                                )
                            )
                        }

                        if (eShopData.issue != null && !eShopData.issue!!.group.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonTitleData(
                                    eShopData.issue!!.title ?: "",
                                    ""
                                )
                            )

                            eShopData.issue!!.group!!.mapIndexed { index, group ->
                                group.isSelected = index == 0
                            }

                            moduleList.add(
                                ModuleData.EShopCategoryData(
                                    eShopData.issue!!.group!!,
                                    "issue"
                                )
                            )

                            issueGroup = eShopData.issue!!.group!!
                            issueGoodsList = eShopData.issue!!.group!![0].goods!!
                            indexAddArrivalItems = eShopData.issue!!.group!![0].goods!!.chunked(2).size

                            issueClickCount = 1
                        }

                        if (!eShopData.banner2.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonMultiBannerData(
                                    eShopData.banner2!!
                                )
                            )
                        }

                        if (eShopData.arrival != null && !eShopData.arrival!!.group.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonTitleData(
                                    eShopData.arrival!!.title ?: "",
                                    ""
                                )
                            )

                            eShopData.arrival!!.group!!.mapIndexed { index, group ->
                                group.isSelected = index == 0
                                group.title = group.category
                            }

                            moduleList.add(
                                ModuleData.EShopCategoryData(
                                    eShopData.arrival!!.group!!,
                                    "arrival"
                                )
                            )

                            arrivalGroup = eShopData.arrival!!.group!!
                            arrivalGoodsList = eShopData.arrival!!.group!![0].goods!!

                            arrivalClickCount = 1
                        }

                        result.postValue(moduleList)
                    }

                }
            }
        }
    }

    fun setExpandableGoodsView(viewType: String) {
        if (viewType == "issue") {
            indexAddArrivalItems = 0
            issueClickCount += 1

        } else {
            if (issueBannerExist) indexAddArrivalItems--
            arrivalClickCount += 1
        }

        setView(selectedPosition)
    }

    fun setTabGoodsView(selectedPosition: Int, viewType: String) {
        if (viewType == "issue") {
            tabClickViewType = "issue"
            indexAddArrivalItems = 0
            issueClickCount = 1
            issueGoodsList = issueGroup[selectedPosition].goods!!
            indexAddArrivalItems = issueGoodsList.chunked(2).size
        } else {
            tabClickViewType = "arrival"
            if (issueBannerExist) indexAddArrivalItems--
            arrivalClickCount = 1
            arrivalGoodsList = arrivalGroup[selectedPosition].goods!!
        }

        setView(selectedPosition)
    }

    fun setView(selectedPosition: Int) {
        this.selectedPosition = selectedPosition

        val dataSet = moduleList.map { it.clone() }.toMutableList()

        moduleList.forEachIndexed { index, item ->
            when(item) {
                is ModuleData.EShopCategoryData -> {
                    if (item.viewType == "issue") {
                        if (tabClickViewType == "issue") {
                            issueGroup.mapIndexed { index, group ->
                                group.isSelected = index == selectedPosition
                            }

                            dataSet[index] = ModuleData.EShopCategoryData(issueGroup, "issue")
                        }
                        issueGroup[selectedPosition].banner?.let {
                            val bannerList = mutableListOf<Banner>()
                            bannerList.add(it)
                            dataSet.add(index + 1, ModuleData.CommonMultiBannerData(bannerList))
                            issueBannerExist = true
                            indexAddArrivalItems++
                        }

                        val newList = mutableListOf<ModuleData>()
                        val goodsList: List<Goods>
                        if (issueGoodsList.size > 8) {
                            goodsList = issueGoodsList.take(8 * issueClickCount)
                            indexAddArrivalItems++
                        } else {
                            goodsList = issueGoodsList
                        }

                        addItemWithGoods(
                            newList,
                            goodsList,
                            issueGoodsList.size > 8 * arrivalClickCount,
                            "issue"
                        )

                        dataSet.addAll(index + 2, newList)
                    } else if (item.viewType == "arrival") {
                        if (tabClickViewType == "arrival") {
                            arrivalGroup.mapIndexed { index, group ->
                                group.isSelected = index == selectedPosition
                            }

                            dataSet[index + indexAddArrivalItems + 1] = ModuleData.EShopCategoryData(arrivalGroup, "arrival")
                        }

                        arrivalGroup[selectedPosition].banner?.let {
                            val bannerList = mutableListOf<Banner>()
                            bannerList.add(it)
                            dataSet.add(index + indexAddArrivalItems + 2, ModuleData.CommonMultiBannerData(bannerList))
                            indexAddArrivalItems++
                        }

                        val newList = mutableListOf<ModuleData>()
                        val goodsList = if (arrivalGoodsList.size > 8) arrivalGoodsList.take(8 * arrivalClickCount) else arrivalGoodsList

                        addItemWithGoods(
                            newList,
                            goodsList,
                            arrivalGoodsList.size > 8 * arrivalClickCount,
                            "arrival"
                        )

                        dataSet.addAll(index + indexAddArrivalItems + 2, newList)
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
        viewType: String
    ) {
        list.chunked(2).forEach {
            moduleList.add(
                ModuleData.CommonGoodsGridData(
                    "eshop", it, 0
                )
            )
        }

        moduleList.add(
            ModuleData.EShopCategoryMoreData(isExistMore, viewType)
        )
    }

    private fun MutableList<ModuleData>.removeGoodsHolder(range: IntRange): MutableList<ModuleData> {
        return this.removeRange(range).map { it.clone() }.toMutableList()
    }
}
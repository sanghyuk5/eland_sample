package com.pionnet.eland.ui.main.tabHome

import androidx.lifecycle.*
import com.pionnet.eland.model.Category
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.model.Status
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class HomeViewModel : CommonViewModel() {
    private val repository by lazy { HomeRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()
    val tabResult = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var homeMdCategoryList = listOf<HomeData.Data.MDRecommend.CategoryList>()

    /**
     * 홈 api
     */
    override fun requestData() {
        viewModelScope.launch {
            repository.requestHomeStream().collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.let { homeData ->
                        moduleList.clear()
                        if (!homeData.data.mainBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonMainBanner(
                                    homeData.data.mainBanner!!
                                )
                            )
                        }

                        if (!homeData.data.categoryIcon.isNullOrEmpty()) {
                            var isMoreClick = false
                            if (homeData.data.categoryIcon!!.size > 10) {
                                isMoreClick = true
                            }
                            moduleList.add(
                                ModuleData.HomeCategoryIconData(
                                    homeData.data.categoryIcon!!,
                                    isMoreClick
                                )
                            )
                        }

                        if (!homeData.data.multiBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonMultiBannerData(
                                    homeData.data.multiBanner!!
                                )
                            )
                        }

                        if (homeData.data.timeSale != null) {
                            moduleList.add(
                                ModuleData.HomeTimeData(
                                    homeData.data.timeSale!!
                                )
                            )
                        }

                        if (!homeData.data.brand.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.HomeBrandData(
                                    homeData.data.brand!!
                                )
                            )
                        }

                        if (homeData.data.luckyDeal != null && !homeData.data.luckyDeal!!.goodsList.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonTitleData(
                                    homeData.data.luckyDeal!!.title ?: "럭키딜",
                                    homeData.data.luckyDeal!!.subtitle ?: "서브타이틀"
                                )
                            )
                            homeData.data.luckyDeal!!.goodsList!!.forEach { goods ->
                                moduleList.add(
                                    ModuleData.HomeLuckyDealGoodsData(
                                        goods
                                    )
                                )
                            }
                        }

                        if (homeData.data.seasonPlan != null && !homeData.data.seasonPlan!!.offerList.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonTitleData(
                                    homeData.data.seasonPlan!!.title ?: "시즌기획전",
                                    homeData.data.seasonPlan!!.subtitle ?: "서브타이틀"
                                )
                            )
                            homeData.data.seasonPlan!!.offerList!!.forEach { offerList ->
                                moduleList.add(
                                    ModuleData.HomeSeasonPlansData(
                                        offerList
                                    )
                                )
                            }
                        }

                        if (homeData.data.storeShop != null) {
                            if (!homeData.data.storeShop!!.bannerList.isNullOrEmpty() ||
                                !homeData.data.storeShop!!.goodsList.isNullOrEmpty()) {
                                moduleList.add(
                                    ModuleData.HomeStoreShopData(
                                        homeData.data.storeShop!!
                                    )
                                )
                            }
                        }

                        if (homeData.data.mdRecommend != null && !homeData.data.mdRecommend!!.categoryList.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.CommonTitleData(
                                    homeData.data.mdRecommend!!.title ?: "MD추천",
                                    homeData.data.seasonPlan!!.subtitle ?: ""
                                )
                            )

                            homeMdCategoryList = homeData.data.mdRecommend!!.categoryList!!
                            val categoryList = homeData.data.mdRecommend!!.categoryList!!.mapIndexed { index, category ->
                                Category(imageUrl = category.imageUrl,
                                    title = category.title,
                                    isSelected = index == 0
                                )
                            }

                            moduleList.add(
                                ModuleData.CommonCategoryTab(
                                    categoryList
                                )
                            )

                            moduleList.add(
                                ModuleData.CommonGoodsHorizontalData(
                                    homeMdCategoryList[0].goodsList!!
                                )
                            )
                        }

                        result.postValue(moduleList)
                    }
                } else if (it.status == Status.ERROR) {
                    result.postValue(moduleList)
                }
            }
        }
    }

    fun setTabGoodsItem(selectedPosition: Int) {
        val dataSet = moduleList.map { it.clone() }.toMutableList()

        moduleList.forEachIndexed { index, item ->
            when(item) {
                is ModuleData.CommonCategoryTab -> {
                    val categoryList = homeMdCategoryList.mapIndexed { index, category ->
                        Category(imageUrl = category.imageUrl,
                            title = category.title,
                            isSelected = index == selectedPosition
                        )
                    }

                    dataSet[index] = ModuleData.CommonCategoryTab(categoryList)
                }

                is ModuleData.CommonGoodsHorizontalData -> {
                    dataSet[index] = ModuleData.CommonGoodsHorizontalData(homeMdCategoryList[selectedPosition].goodsList!!)
                }
            }
        }

        tabResult.postValue(dataSet)
    }
}
package com.pionnet.eland.ui.main.tabHome

import androidx.lifecycle.*
import com.pionnet.eland.model.Status
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class HomeViewModel : CommonViewModel() {
    private val repository by lazy { HomeRepository() }

    val homeResult = MutableLiveData<MutableList<ModuleData>>()

    /**
     * 홈 api
     */
    override fun requestData() {
        viewModelScope.launch {
            repository.requestHomeStream().collect {
                val moduleList = mutableListOf<ModuleData>()

                if (it.status == Status.SUCCESS) {
                    it.data?.let { homeData ->
                        if (!homeData.data.mainBanner.isNullOrEmpty()) {
                            moduleList.add(
                                ModuleData.MainBannerData(
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
                                ModuleData.HomeMultiBannerData(
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
                                ModuleData.HomeTitleData(
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
                                ModuleData.HomeTitleData(
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
                            homeData.data.mdRecommend!!.apply {
                                categoryList!!.map { it.isSelected = false }
                            }
                            homeData.data.mdRecommend!!.categoryList!![0].isSelected = true

                            moduleList.add(
                                ModuleData.HomeMDRecommendData(
                                    homeData.data.mdRecommend!!
                                )
                            )
                        }

                        homeResult.postValue(moduleList)
                    }
                } else if (it.status == Status.ERROR) {
                    homeResult.postValue(moduleList)
                }
            }
        }
    }
}
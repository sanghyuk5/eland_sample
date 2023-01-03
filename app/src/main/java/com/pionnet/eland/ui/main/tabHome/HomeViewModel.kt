package com.pionnet.eland.ui.main.tabHome

import androidx.lifecycle.*
import com.pionnet.eland.model.Category
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class HomeViewModel : CommonViewModel() {
    private val repository by lazy { HomeRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var homeMdCategoryList = listOf<HomeData.Data.MDRecommend.CategoryList>()

    /**
     * 홈 api
     */
    override fun requestData() {
        viewModelScope.launch {
            repository.requestHomeStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setHomeModules(data)    
                        }
                        
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setHomeModules(data: HomeData.Data) {
        moduleList.clear()
        if (!data.mainBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMainBanner(data.mainBanner)
            )
        }

        if (!data.categoryIcon.isNullOrEmpty()) {
            var isMoreClick = false
            if (data.categoryIcon.size > 10) {
                isMoreClick = true
            }
            moduleList.add(
                ModuleData.HomeCategoryIconData(data.categoryIcon, isMoreClick)
            )
        }

        if (!data.multiBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMultiBannerData(data.multiBanner)
            )
        }

        if (data.timeSale != null) {
            moduleList.add(
                ModuleData.HomeTimeData(data.timeSale)
            )
        }

        if (!data.brand.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.HomeBrandData(data.brand)
            )
        }

        if (data.luckyDeal != null && !data.luckyDeal.goodsList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonTitleData(data.luckyDeal.title ?: "럭키딜", data.luckyDeal.subtitle ?: "서브타이틀")
            )

            data.luckyDeal.goodsList.forEach { goods ->
                moduleList.add(
                    ModuleData.CommonLuckyDealGoods(goods)
                )
            }
        }

        if (data.seasonPlan != null && !data.seasonPlan.offerList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonTitleData(data.seasonPlan.title ?: "시즌기획전", data.seasonPlan.subtitle ?: "서브타이틀")
            )
            data.seasonPlan.offerList.forEach { offerList ->
                moduleList.add(
                    ModuleData.HomeSeasonPlansData(offerList)
                )
            }
        }

        if (data.storeShop != null) {
            if (!data.storeShop.bannerList.isNullOrEmpty() || !data.storeShop.goodsList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.HomeStoreShopData(data.storeShop)
                )
            }
        }

        if (data.mdRecommend != null && !data.mdRecommend.categoryList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonTitleData(data.mdRecommend.title ?: "MD추천", data.mdRecommend.subtitle ?: ""
                )
            )

            homeMdCategoryList = data.mdRecommend.categoryList
            val categoryList = data.mdRecommend.categoryList.mapIndexed { index, category ->
                Category(imageUrl = category.imageUrl,
                    title = category.title,
                    isSelected = index == 0
                )
            }

            moduleList.add(
                ModuleData.CommonCategoryTab(categoryList, "home")
            )

            moduleList.add(
                ModuleData.CommonGoodsHorizontalData(homeMdCategoryList[0].goodsList!!)
            )
        }

        result.postValue(moduleList)
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

                    dataSet[index] = ModuleData.CommonCategoryTab(categoryList, "home")
                }

                is ModuleData.CommonGoodsHorizontalData -> {
                    dataSet[index] = ModuleData.CommonGoodsHorizontalData(homeMdCategoryList[selectedPosition].goodsList!!)
                }
            }
        }

        result.postValue(dataSet)
    }
}
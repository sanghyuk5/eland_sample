package com.pionnet.eland.ui.main

import com.pionnet.eland.model.*

sealed class ModuleData {
    abstract fun clone(): ModuleData

    data class MainBannerData(
        val mainBannerData: List<Banner>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeCategoryIconData(
        val homeCategoryIconData: List<HomeData.Data.CategoryIcon>,
        val isMoreClick: Boolean = false,
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeMultiBannerData(
        val homeBannerData: List<Banner>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeTimeData(
        val homeTimeData: Goods
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeBrandData(
        val homeBrandData: List<HomeData.Data.Brand>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonTitleData(
        val title: String,
        val subTitle: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeLuckyDealGoodsData(
        val homeLuckyDealData: Goods
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeSeasonPlansData(
        val homeSeasonPlansData: HomeData.Data.SeasonPlan.HomeOffer
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeStoreShopData(
        val homeStoreShopData: HomeData.Data.StoreShop
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeMDRecommendData(
        val homeMDRecommendData: HomeData.Data.MDRecommend,
        var isSelected: Boolean = false
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopDeliveryData(
        val deliveryData: StoreShopData.Data.Delivery
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopRecommendData(
        val recommendData: List<StoreShopData.Data.Recommend>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopRegularStoreData(
        val regularStoreData: List<StoreShopData.Data.Regular>,
        val goods: List<Goods>,
        val isShowData: Boolean
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopPickSearchData(
        val smartPickData: List<StoreShopData.Data.SmartPick>,
        val pickName: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonSortData(
        val sortData: List<String>?,
        val sortPosition: Int,
        val viewType: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopSmartPickNameData(
        val pickName: String,
        val pickNo: String?
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopCategoryData(
        val categoryData: List<StoreShopData.Data.CategoryGoods>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopCategoryTitleData(
        val title: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonGoodsGridData(
        val goodsData: List<Goods>,
        val index: Int
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonGoodsLinearData(
        val goodsData: Goods,
        val index: Int
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonGoodsLargeData(
        val goodsData: Goods,
        val index: Int
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class PlanInfoData(
        val shopInfo: PlanDetailData.Data.ShopInfo
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class PlanTabTitleData(
        val tabTitle: String,
        val count: Int
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }
}



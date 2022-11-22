package com.pionnet.eland.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.*
import com.pionnet.eland.ui.viewholder.*

class CommonModulesRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner
): ListAdapter<ModuleData, BaseViewHolder>(moduleDiffUtilCallback) {

    private val itemHolders: MutableList<RecyclerView.ViewHolder> = mutableListOf()

    var values: List<ModuleData> = listOf()
        set(value) {
            field = value
            submitList(field)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ModuleData.MainBannerData.ordinal() -> MainBannerViewHolder(
                    ViewMainBannerModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), lifecycleOwner
                )

            ModuleData.HomeCategoryIconData.ordinal() -> HomeCategoryIconViewHolder(
                    ViewHomeCategoryIconModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ModuleData.HomeMultiBannerData.ordinal() -> HomeMultiBannerViewHolder(
                    ViewHomeMultiBannerModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ModuleData.HomeTimeData.ordinal() -> HomeTimeSaleViewHolder(
                    ViewHomeTimesaleModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ModuleData.HomeBrandData.ordinal() -> HomeBrandViewHolder(
                    ViewHomeBrandModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ModuleData.HomeTitleData.ordinal() -> TitleViewHolder(
                    ViewTitleModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ModuleData.HomeLuckyDealGoodsData.ordinal() -> HomeLuckyDealGoodViewHolder(
                    ViewHomeLuckyDealGoodModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ModuleData.HomeSeasonPlansData.ordinal() -> HomeSeasonPlanViewHolder(
                    ViewHomeSeasonPlanModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ModuleData.HomeStoreShopData.ordinal() -> HomeStoreShopViewHolder(
                    ViewHomeStoreShopModuleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            ModuleData.HomeMDRecommendData.ordinal() -> HomeMDRecommendViewHolder(
                ViewHomeMdRecommendModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopDeliveryData.ordinal() -> StoreShopDeliveryViewHolder(
                ViewStoreShopDeliveryModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopRecommendData.ordinal() -> StoreShopRecommendViewHolder(
                ViewStoreShopRecommendModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopRegularStoreData.ordinal() -> StoreShopRegularViewHolder(
                ViewStoreShopRegularModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopSmartPickData.ordinal() -> StoreShopSmartPickViewHolder(
                ViewStoreShopSmartPickModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopCategoryData.ordinal() -> StoreShopCategoryViewHolder(
                ViewStoreShopCategoryModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopCategoryTitleData.ordinal() -> StoreShopCategoryTitleViewHolder(
                ViewStoreShopCategoryTitleModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopCategoryGoodData.ordinal() -> StoreShopCategoryGoodViewHolder(
                ViewStoreShopCategoryGoodModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> UnknownViewHolder(
                ViewUnknownModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.onBind(item, position)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.map {
                when (it) {
//                    DataManager.HOT_VIDEO_RELEASE -> holder.onResetPlayView()
//                    DataManager.STYLE_NOW_TOOL_TIP_SHOW -> holder.onStyleNowSetToolTop()
//                    DataManager.LIVE_PLAY_ON -> holder.onLivePlay()
//                    DataManager.LIVE_PLAY_OFF -> holder.onLiveStop()
                    else -> onBindViewHolder(holder, position)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).ordinal()
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAppeared()

        itemHolders.add(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappeared()

        itemHolders.remove(holder)
    }

    companion object {
        private val moduleDiffUtilCallback = object : DiffUtil.ItemCallback<ModuleData>() {
            override fun areItemsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean = oldItem == newItem
        }
    }
}

private inline fun <reified T : Any> T.ordinal(): Int {
    if (T::class.isSealed) {
        return T::class.java.classes.indexOfFirst { sub -> sub == javaClass }
    }

    val klass = if (T::class.nestedClasses.isEmpty()) {
        javaClass.declaringClass
    } else {
        javaClass
    }

    return klass.superclass?.classes?.indexOfFirst { it == klass } ?: -1
}
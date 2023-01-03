package com.pionnet.eland

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import com.google.android.material.transition.Hold
import com.pionnet.eland.ui.main.ModuleData

/**
* 필요한 이벤트
*   : linkEvent - for link click event
*   : holderEvent - fragment > holder UI control
*/
object EventBus {
    private const val MIN_CLICK_INTERVAL: Long = 600
    private var lastEventTime: Long = 0

    val linkEvent: MutableLiveData<SingleLiveEvent<LinkEvent>> = MutableLiveData()

    val homeTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val luckyTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val bestTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val planTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val storeShopRegularSearchStore: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val storeShopPickSearchStore: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val storeShopSort: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val storeShopViewChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val storeShopTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eKidsWeeklyBestTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eKidsNewArrivalTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eKidsWeeklyExpand: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eKidsNewArrivalExpand: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eShopIssueTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eShopArrivalTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eShopIssueMore: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eShopArrivalMore: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val planDetailViewChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val planDetailSort: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()

    fun fire(event: LinkEvent) {
        if (isIntervalTooShort()) return

        linkEvent.value = SingleLiveEvent(event)
    }

    fun fire(event: HolderEvent) {
        if (isIntervalTooShort()) return

        when (event.type) {
            HolderEventType.TAB_CLICK_HOME -> homeTabChange.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_LUCKY -> luckyTabChange.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_BEST -> bestTabChange.value = SingleLiveEvent(event)
            HolderEventType.STORE_SHOP_REGULAR_SEARCH -> storeShopRegularSearchStore.value = SingleLiveEvent(event)
            HolderEventType.STORE_SHOP_PICK_SEARCH -> storeShopPickSearchStore.value = SingleLiveEvent(event)
            HolderEventType.STORE_SHOP_SORT -> storeShopSort.value = SingleLiveEvent(event)
            HolderEventType.STORE_SHOP_VIEW_CHANGE -> storeShopViewChange.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_STORE_SHOP -> storeShopTabChange.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_E_KIDS_WEEKLY -> eKidsWeeklyBestTabChange.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_E_KIDS_ARRIVAL -> eKidsNewArrivalTabChange.value = SingleLiveEvent(event)
            HolderEventType.EXPAND_E_KIDS_WEEKLY -> eKidsWeeklyExpand.value = SingleLiveEvent(event)
            HolderEventType.EXPAND_E_KIDS_ARRIVAL -> eKidsNewArrivalExpand.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_E_SHOP_ISSUE -> eShopIssueTabChange.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_E_SHOP_ARRIVAL -> eShopArrivalTabChange.value = SingleLiveEvent(event)
            HolderEventType.MORE_E_SHOP_ISSUE -> eShopIssueMore.value = SingleLiveEvent(event)
            HolderEventType.MORE_E_SHOP_ARRIVAL -> eShopArrivalMore.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_PLAN -> planTabChange.value = SingleLiveEvent(event)
            HolderEventType.PLAN_DETAIL_SORT -> planDetailSort.value = SingleLiveEvent(event)
            HolderEventType.PLAN_DETAIL_VIEW_CHANGE -> planDetailViewChange.value = SingleLiveEvent(event)
        }
    }

    private fun isIntervalTooShort(): Boolean {
        val currentEventTime = SystemClock.uptimeMillis()
        val elapsedTime = currentEventTime - lastEventTime
        lastEventTime = currentEventTime
        return elapsedTime <= MIN_CLICK_INTERVAL
    }
}

class LinkEvent {
    val url: String?
    val type: LinkEventType
    val data: String?

    constructor(type: LinkEventType) {
        this.url = ""
        this.type = type
        this.data = null
    }

    constructor(url: String?, type: LinkEventType = LinkEventType.DEFAULT) {
        this.url = url
        this.type = type
        this.data = null
    }

    constructor(type: LinkEventType, data: String?) {
        this.url = ""
        this.type = type
        this.data = data
    }

    constructor(type: LinkEventType, url: String?, data: String) {
        this.url = url
        this.type = type
        this.data = data
    }
}

enum class LinkEventType {
    HOME,
    DEFAULT,
    PROFILE,
    STORE_DETAIL,
    STORE_LIST,
    STORE_REGISTER,
    LOGIN,
    SIGN_IN,
    LOCATION,
    WEB_URL,
    FIND_PW,
    DIAL,
    REPORT,
    CLIPBOARD,
    EXTERNAL
}

class HolderEvent {
    val type: HolderEventType
    val data: Any?

    constructor(type: HolderEventType) {
        this.type = type
        this.data = null
    }

    constructor(type: HolderEventType, data: Any) {
        this.type = type
        this.data = data
    }
}

enum class HolderEventType {
    TAB_CLICK_HOME,
    TAB_CLICK_LUCKY,
    TAB_CLICK_BEST,
    STORE_SHOP_REGULAR_SEARCH,
    STORE_SHOP_PICK_SEARCH,
    STORE_SHOP_SORT,
    STORE_SHOP_VIEW_CHANGE,
    TAB_CLICK_STORE_SHOP,
    TAB_CLICK_E_KIDS_WEEKLY,
    TAB_CLICK_E_KIDS_ARRIVAL,
    EXPAND_E_KIDS_WEEKLY,
    EXPAND_E_KIDS_ARRIVAL,
    TAB_CLICK_E_SHOP_ISSUE,
    TAB_CLICK_E_SHOP_ARRIVAL,
    MORE_E_SHOP_ISSUE,
    MORE_E_SHOP_ARRIVAL,
    TAB_CLICK_PLAN,
    PLAN_DETAIL_SORT,
    PLAN_DETAIL_VIEW_CHANGE
}

class SingleLiveEvent<out T>(private val content: T) {
    private var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
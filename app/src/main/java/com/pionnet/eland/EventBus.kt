package com.pionnet.eland

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
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
    val storeShopRegularSearchStore: MutableLiveData<SingleLiveEvent<String>> = MutableLiveData()
    val storeShopSearchStore: MutableLiveData<SingleLiveEvent<ModuleData.StoreShopPickSearchData>> = MutableLiveData()
    val sort: MutableLiveData<SingleLiveEvent<List<String>>> = MutableLiveData()
    val viewChange: MutableLiveData<SingleLiveEvent<String>> = MutableLiveData()
    val tabChange: MutableLiveData<SingleLiveEvent<Int>> = MutableLiveData()
    val eKidsWeeklyBestTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eKidsNewArrivalTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eKidsWeeklyExpand: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eKidsNewArrivalExpand: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eShopIssueTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eShopArrivalTabChange: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eShopIssueMore: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()
    val eShopArrivalMore: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()

    fun fire(event: LinkEvent) {
        if (isIntervalTooShort()) return

        linkEvent.value = SingleLiveEvent(event)
    }

    fun fire(event: HolderEvent) {
        if (isIntervalTooShort()) return

        when (event.type) {
            HolderEventType.TAB_CLICK_E_KIDS_WEEKLY -> eKidsWeeklyBestTabChange.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_E_KIDS_ARRIVAL -> eKidsNewArrivalTabChange.value = SingleLiveEvent(event)
            HolderEventType.EXPAND_E_KIDS_WEEKLY -> eKidsWeeklyExpand.value = SingleLiveEvent(event)
            HolderEventType.EXPAND_E_KIDS_ARRIVAL -> eKidsNewArrivalExpand.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_E_SHOP_ISSUE -> eShopIssueTabChange.value = SingleLiveEvent(event)
            HolderEventType.TAB_CLICK_E_SHOP_ARRIVAL -> eShopArrivalTabChange.value = SingleLiveEvent(event)
            HolderEventType.MORE_E_SHOP_ISSUE -> eShopIssueMore.value = SingleLiveEvent(event)
            HolderEventType.MORE_E_SHOP_ARRIVAL -> eShopArrivalMore.value = SingleLiveEvent(event)
        }
    }

    fun fire(event: ModuleData.StoreShopPickSearchData) {
        if (isIntervalTooShort()) return

        storeShopSearchStore.value = SingleLiveEvent(event)
    }

    fun fire(event: List<String>) {
        if (isIntervalTooShort()) return

        sort.value = SingleLiveEvent(event)
    }


    fun fire(event: String) {
        if (isIntervalTooShort()) return

        when(event) {
            "searchStore" -> storeShopRegularSearchStore.value = SingleLiveEvent(event)
            "viewChange" -> viewChange.value = SingleLiveEvent(event)
        }
    }

    fun fire(event: Int) {
        if (isIntervalTooShort()) return

        tabChange.value = SingleLiveEvent(event)
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
    val data: String?

    constructor(type: HolderEventType) {
        this.type = type
        this.data = null
    }

    constructor(type: HolderEventType, data: String) {
        this.type = type
        this.data = data
    }
}

enum class HolderEventType {
    TAB_CLICK_E_KIDS_WEEKLY,
    TAB_CLICK_E_KIDS_ARRIVAL,
    EXPAND_E_KIDS_WEEKLY,
    EXPAND_E_KIDS_ARRIVAL,
    TAB_CLICK_E_SHOP_ISSUE,
    TAB_CLICK_E_SHOP_ARRIVAL,
    MORE_E_SHOP_ISSUE,
    MORE_E_SHOP_ARRIVAL
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
package com.pionnet.eland

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData

/**
* 필요한 이벤트
*   : linkEvent - for link click event
*   : holderEvent - fragment > holder UI control
*/
object EventBus {
    private const val MIN_CLICK_INTERVAL: Long = 600
    private var lastEventTime: Long = 0

    val linkEvent: MutableLiveData<SingleLiveEvent<LinkEvent>> = MutableLiveData()
    val holderEvent: MutableLiveData<SingleLiveEvent<HolderEvent>> = MutableLiveData()

    fun fire(event: LinkEvent) {
        if (isIntervalTooShort()) return

        linkEvent.value = SingleLiveEvent(event)
    }

    fun fire(event: HolderEvent) {
        if (isIntervalTooShort()) return

        holderEvent.value = SingleLiveEvent(event)
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

class HolderEvent(val type: HolderEventType)

enum class HolderEventType {
    REFRESH,
    ADD_REPLY
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
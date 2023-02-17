package com.pionnet.eland.ui.push

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.ElandApp.Companion.database
import com.pionnet.eland.data.GoodsDetailData
import com.pionnet.eland.data.room.Push
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class PushListRepository {

    fun requestPushListStream(): Flow<List<Push>> {
        return database.pushDao().getAll()
    }
}
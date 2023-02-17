package com.pionnet.eland.ui.leftmenu

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.data.LeftMenuData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class LeftMenuRepository {

    suspend fun requestLeftMenuStream(): Flow<Result<LeftMenuData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/leftMenu.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, LeftMenuData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
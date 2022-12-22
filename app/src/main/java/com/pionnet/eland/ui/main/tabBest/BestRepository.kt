package com.pionnet.eland.ui.main.tabBest

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.model.Resource
import com.pionnet.eland.model.BestData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class BestRepository {
    /**
     *  럭키딜 데이터
     */
    suspend fun requestBestStream(): Flow<Resource<BestData>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/best.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, BestData::class.java)

            emit(Resource.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
package com.pionnet.eland.ui.main.tabEkids

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.data.EKidsData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class EKidsRepository {
    /**
     *  EKids 데이터
     */
    suspend fun requestEKidsStream(): Flow<Result<EKidsData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/ekids.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, EKidsData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
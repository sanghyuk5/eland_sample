package com.pionnet.eland.ui.main.tabEShop

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.model.BestData
import com.pionnet.eland.model.EShopData
import com.pionnet.eland.model.Resource
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class EShopRepository {
    /**
     *  e-shop 데이터
     */
    suspend fun requestEShopStream(): Flow<Result<EShopData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/eshop.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, EShopData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
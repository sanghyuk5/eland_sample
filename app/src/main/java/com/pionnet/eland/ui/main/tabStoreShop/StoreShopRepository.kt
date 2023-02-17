package com.pionnet.eland.ui.main.tabStoreShop

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.data.StorePickData
import com.pionnet.eland.data.StoreShopData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class StoreShopRepository {

    /**
     *  스토어쇼핑
     */
    suspend fun requestStoreShopStream(mbrVendNo: String?): Flow<Result<StoreShopData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/storeShop.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, StoreShopData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    /**
     *  스토어쇼핑 스토어픽
     */
    suspend fun requestStorePickStream(categoryNo: String?): Flow<Result<StorePickData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/storePick.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, StorePickData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
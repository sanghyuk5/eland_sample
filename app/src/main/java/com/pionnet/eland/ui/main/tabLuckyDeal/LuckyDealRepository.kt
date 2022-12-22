package com.pionnet.eland.ui.main.tabLuckyDeal

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.model.Resource
import com.pionnet.eland.model.LuckyDealData
import com.pionnet.eland.model.LuckyDealGoodsData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class LuckyDealRepository {
    /**
     *  럭키딜 데이터
     */
    suspend fun requestLucyDealStream(): Flow<Resource<LuckyDealData>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/luckydeal1.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, LuckyDealData::class.java)

            emit(Resource.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestLucyDealGoodsStream(): Flow<Resource<LuckyDealGoodsData>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/luckydeal.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, LuckyDealGoodsData::class.java)

            emit(Resource.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
package com.pionnet.eland.ui.search

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.model.SearchPlanShopData
import com.pionnet.eland.model.SearchRawRankData
import com.pionnet.eland.model.SearchResultBrandData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class SearchRepository {

    suspend fun requestSearchResultBrandStream(): Flow<Result<SearchResultBrandData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/search_result_brand.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, SearchResultBrandData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestSearchResultRelatedStream(): Flow<Result<SearchRawRankData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/search_result_related.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, SearchRawRankData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }



    suspend fun requestSearchResultPlanShopStream(): Flow<Result<SearchPlanShopData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/search_result_plan.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, SearchPlanShopData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

}
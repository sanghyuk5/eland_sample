package com.pionnet.eland.ui.search.searchBrand

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.model.SearchBrandKeyword
import com.pionnet.eland.model.SearchBrandKeywordList
import com.pionnet.eland.model.SearchRawRankData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class SearchBrandRepository {

    suspend fun requestSearchPopularStream(): Flow<Result<SearchRawRankData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/search_brand.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, SearchRawRankData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestSearchKeywordStream(): Flow<Result<SearchBrandKeyword?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/search_brand_keyword.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, SearchBrandKeyword::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestSearchKeywordListStream(): Flow<Result<SearchBrandKeywordList?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/search_brand_keyword_list.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, SearchBrandKeywordList::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

}
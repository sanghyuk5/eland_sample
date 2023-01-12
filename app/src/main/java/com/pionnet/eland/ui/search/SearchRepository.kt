package com.pionnet.eland.ui.search

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.model.SearchRank
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class SearchRepository {

    suspend fun requestSearchPopularStream(): Flow<Result<SearchRank?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/search_popular.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, SearchRank::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

}
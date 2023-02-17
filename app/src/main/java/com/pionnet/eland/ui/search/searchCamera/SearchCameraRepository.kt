package com.pionnet.eland.ui.search.searchCamera

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.data.SearchRawRankData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class SearchCameraRepository {

    suspend fun requestSearchPopularStream(): Flow<Result<SearchRawRankData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/search_brand.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, SearchRawRankData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

}


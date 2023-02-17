package com.pionnet.eland.ui.main

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.data.HomeData
import com.pionnet.eland.data.TabData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class MainRepository {
    /**
     * splash
     * 메인 상단 탭 메뉴 데이터
     */

    suspend fun requestTabStream(): Flow<Result<TabData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/mainGnb.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, TabData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    /**
     * splash
     * 메인 홈 데이터
     */
    suspend fun requestHomeStream(): Flow<Result<HomeData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/home.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, HomeData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
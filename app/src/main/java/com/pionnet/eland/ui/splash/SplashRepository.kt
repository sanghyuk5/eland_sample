package com.pionnet.eland.ui.splash

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.model.Resource
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.model.tabmenu.TabData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class SplashRepository {
    /**
     *  메인 상단 탭 메뉴 데이터
     */
    suspend fun requestTabStream(): Flow<Resource<TabData>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/mainGnb.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, TabData::class.java)

            emit(Resource.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    /**
     *  메인 홈 데이터
     */
    suspend fun requestHomeStream(): Flow<Resource<HomeData>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/home.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, HomeData::class.java)

            emit(Resource.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
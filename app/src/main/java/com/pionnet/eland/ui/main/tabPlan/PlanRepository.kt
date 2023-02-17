package com.pionnet.eland.ui.main.tabPlan

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.data.PlanData
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class PlanRepository {
    /**
     *  기획전 데이터
     */
    suspend fun requestPlanStream(): Flow<Result<PlanData?>> {
        return flow {
            val jsonString = getJsonFileToString("sample_json/plan_0_page1.json", ElandApp.appContext)
            val data = Gson().fromJson(jsonString, PlanData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestPlanPageStream(pageNo: Int): Flow<Result<PlanData?>> {
        return flow {
            val jsonString = when (pageNo) {
                2 -> getJsonFileToString("sample_json/plan_0_page2.json", ElandApp.appContext)
                else -> getJsonFileToString("sample_json/plan_0_page3.json", ElandApp.appContext)
            }
            val data = Gson().fromJson(jsonString, PlanData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestPlanTabStream(selectedPosition: Int): Flow<Result<PlanData?>> {
        return flow {
            val jsonString = when (selectedPosition) {
                1 -> getJsonFileToString("sample_json/plan_1.json", ElandApp.appContext)
                2 -> getJsonFileToString("sample_json/plan_2.json", ElandApp.appContext)
                3 -> getJsonFileToString("sample_json/plan_3.json", ElandApp.appContext)
                4 -> getJsonFileToString("sample_json/plan_4.json", ElandApp.appContext)
                5 -> getJsonFileToString("sample_json/plan_5.json", ElandApp.appContext)
                else -> getJsonFileToString("sample_json/plan_0_page1.json", ElandApp.appContext)
            }

            val data = Gson().fromJson(jsonString, PlanData::class.java)

            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}
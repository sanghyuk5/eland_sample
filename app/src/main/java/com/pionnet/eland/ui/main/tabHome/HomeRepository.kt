/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pionnet.eland.ui.main.tabHome

import com.google.gson.Gson
import com.pionnet.eland.ElandApp
import com.pionnet.eland.localData.DataManager
import com.pionnet.eland.model.Resource
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.model.DataSource
import com.pionnet.eland.utils.getJsonFileToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class HomeRepository {
    /**
     *  메인 홈 데이터
     */
    suspend fun requestHomeStream(): Flow<Resource<HomeData>> {
        return flow {
            val data = if (DataManager.isIntroToMain) {
                DataManager.isIntroToMain = false
                DataSource.homeData
            } else {
                val jsonString = getJsonFileToString("sample_json/home.json", ElandApp.appContext)
                Gson().fromJson(jsonString, HomeData::class.java)
            }

            emit(Resource.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}

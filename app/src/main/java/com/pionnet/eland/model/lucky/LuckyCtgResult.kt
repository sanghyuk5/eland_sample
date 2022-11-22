package com.pionnet.eland.model.lucky

import java.lang.Exception

sealed class LuckyCtgResult {
    data class Success(val data: LuckyCtgData.Data) : LuckyCtgResult()
    data class Error(val error: Exception) : LuckyCtgResult()
}
package com.pionnet.eland.model.best

import java.lang.Exception

sealed class BestResult {
    data class Success(val data: BestData.Data) : BestResult()
    data class Error(val error: Exception) : BestResult()
}
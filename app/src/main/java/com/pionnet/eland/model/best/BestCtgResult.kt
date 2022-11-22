package com.pionnet.eland.model.best

import java.lang.Exception

sealed class BestCtgResult {
    data class Success(val data: BestCtgData.Data) : BestCtgResult()
    data class Error(val error: Exception) : BestCtgResult()
}
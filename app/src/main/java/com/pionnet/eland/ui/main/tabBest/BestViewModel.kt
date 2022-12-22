package com.pionnet.eland.ui.main.tabBest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.Status
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class BestViewModel : CommonViewModel() {
    private val repository by lazy { BestRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    override fun requestData() {
        viewModelScope.launch {
            repository.requestBestStream().collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let { bestData ->
                        if (bestData.goods != null && !bestData.goods!!.goods.isNullOrEmpty()) {
                            val newList = mutableListOf<ModuleData>()
                            addItemWithGoods(newList, bestData.goods!!.goods!!)
                            moduleList.addAll(0, newList)
                        }

                        result.postValue(moduleList)
                    }

                }
            }
        }
    }

    private fun addItemWithGoods(
        moduleList: MutableList<ModuleData>,
        list: List<Goods>
    ) {
        list.chunked(2).forEach {
            moduleList.add(
                ModuleData.CommonGoodsGridData(
                    "best", it, 0, isRank = true
                )
            )
        }
    }
}
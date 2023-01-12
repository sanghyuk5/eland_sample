package com.pionnet.eland.ui.search.searchPopular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.SearchPlanShop
import com.pionnet.eland.model.SearchRank
import com.pionnet.eland.ui.main.CommonViewModel
import kotlinx.coroutines.launch

class PopularViewModel : CommonViewModel() {

    private val repository by lazy { PopularRepository() }

    val popularResult = MutableLiveData<MutableList<SearchRank>>()
    val planShopResult = MutableLiveData<SearchPlanShop>()

    private var popularList = mutableListOf<SearchRank>()

    override fun requestData() {
        viewModelScope.launch {
            repository.requestSearchPopularStream().collect {
                it.fold(
                    onSuccess = {
                        it?.result?.let { data ->
                            data.forEachIndexed { index, resultData ->
                                resultData?.let { value ->
                                    var isTopFive = false
                                    if (index in 0..4) isTopFive = true
                                    if (value.size == 2) {
                                        popularList.add(SearchRank(value[1], value[0], isTopFive))
                                    }
                                }
                            }
                            popularResult.postValue(popularList)
                        }
                    },
                    onFailure = {}
                )
            }

            repository.requestSearchPlanShopStream().collect {
                it.fold(
                    onSuccess = {
                        planShopResult.postValue(it)
                    },
                    onFailure = {}
                )
            }
        }
    }

}
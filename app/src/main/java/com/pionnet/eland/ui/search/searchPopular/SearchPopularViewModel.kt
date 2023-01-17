package com.pionnet.eland.ui.search.searchPopular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.SearchPlanShopData
import com.pionnet.eland.model.SearchRank
import com.pionnet.eland.ui.main.CommonViewModel
import kotlinx.coroutines.launch

class SearchPopularViewModel : CommonViewModel() {

    private val repository by lazy { SearchPopularRepository() }

    val popularResult = MutableLiveData<MutableList<SearchRank>>()
    val planShopResult = MutableLiveData<List<SearchPlanShopData.Result.Planshop>>()

    private var popularList = mutableListOf<SearchRank>()

    override fun requestData() {
        viewModelScope.launch {
            repository.requestSearchPopularStream().collect {
                it.fold(
                    onSuccess = {
                        it?.result?.let { data ->
                            popularList.clear()

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
                        it?.result?.let { data ->
                            planShopResult.postValue(data[0].planshop)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

}
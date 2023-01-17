package com.pionnet.eland.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.model.SearchPlanShopData
import com.pionnet.eland.model.SearchRank
import com.pionnet.eland.model.SearchRawRankData
import com.pionnet.eland.model.SearchResultBrandData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    private val repository by lazy { SearchRepository() }

    val brandResult = MutableLiveData<List<SearchResultBrandData.Result>>()
    val popularResult = MutableLiveData<MutableList<SearchRank>>()
    val planShopResult = MutableLiveData<List<SearchPlanShopData.Result.Planshop>>()

    private val popularList = mutableListOf<SearchRank>()

    fun requestData() {
        viewModelScope.launch {
            merge(repository.requestSearchResultBrandStream()
                , repository.requestSearchResultRelatedStream()
                , repository.requestSearchResultPlanShopStream()
            )
                .catch {}
                .onEach {
                    it.fold(
                        onSuccess = {
                            when (it) {
                                is SearchResultBrandData -> {
                                    it.result?.let { data ->
                                        brandResult.postValue(data)
                                    }
                                }
                                is SearchRawRankData -> {
                                    it.result?.let {  data ->
                                        popularList.clear()

                                        data.forEach { resultData ->
                                            resultData?.let { value ->
                                                if (value.size == 1) {
                                                    popularList.add(SearchRank(keyword = value[0]))
                                                }
                                            }
                                        }
                                        popularResult.postValue(popularList)
                                    }
                                }
                                is SearchPlanShopData -> {
                                    it.result?.let { data ->
                                        planShopResult.postValue(data[0].planshop)
                                    }
                                }

                            }
                        },
                        onFailure = {}
                    )
                }
                .onCompletion {}
                .launchIn(viewModelScope)
        }
    }
}
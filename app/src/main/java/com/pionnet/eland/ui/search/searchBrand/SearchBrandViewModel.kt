package com.pionnet.eland.ui.search.searchBrand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pionnet.eland.model.*
import com.pionnet.eland.ui.main.CommonViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BrandViewModel : CommonViewModel() {

    private val repository by lazy { SearchBrandRepository() }

    val result = MutableLiveData<MutableList<SearchBrandDataSet>>()

    private val moduleList = mutableListOf<SearchBrandDataSet>()
    private val popularList = mutableListOf<SearchRank>()

    private var keywordList = listOf<SearchBrandKeyword.Data?>()
    private var brandLetter = listOf<SearchBrandKeyword.Data.NavBrandKeyword.NavBrandKeywordLetter?>()
    private var brandLetterList = listOf<SearchBrandKeywordList.Data?>()

    private var letterType = 0


    val popularResult = MutableLiveData<MutableList<SearchRank>>()
    val brandKeywordResult = MutableLiveData<List<SearchBrandKeyword.Data.NavBrandKeyword.NavBrandKeywordLetter?>?>()
    val brandKeywordListResult = MutableLiveData<SearchBrandKeywordList.Data>()


    override fun requestData() {
        viewModelScope.launch {
            merge(repository.requestSearchPopularStream(), repository.requestSearchKeywordStream(), repository.requestSearchKeywordListStream())
                .catch {}
                .onEach {
                    it.fold(
                        onSuccess = {
                            if (it is SearchRawRankData) {
                                it.result?.let {  data ->
                                    data.forEachIndexed { index, resultData ->
                                        resultData?.let { value ->
                                            if (value.size == 2) {
                                                popularList.add(SearchRank(value[1], value[0]))
                                            }
                                        }
                                    }
                                }
                            } else if (it is SearchBrandKeyword) {
                                it.data?.let { data ->
                                    keywordList = data
                                    data[0]?.navBrandKeyword?.let { navBrandKeyword ->
                                        navBrandKeyword.navBrandKeywordListKor?.mapIndexed { index, navBrandKeywordLetter ->
                                            if (index == 0) navBrandKeywordLetter?.isSelected = true else false
                                        }

                                        navBrandKeyword.navBrandKeywordListEng?.mapIndexed { index, navBrandKeywordLetter ->
                                            if (index == 0) navBrandKeywordLetter?.isSelected = true else false
                                        }
                                    }
                                }

                            } else if (it is SearchBrandKeywordList) {
                                it.data?.get(0)?.let { data ->
                                    brandLetterList = data
                                }
                            }
                        },
                        onFailure = {}
                    )
                }
                .onCompletion {
                    setSearchBrandModules()
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestKeywordList() {
        viewModelScope.launch {
            repository.requestSearchKeywordListStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.get(0)?.let { data ->
                            brandLetterList = data
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setSearchBrandModules() {
        moduleList.add(SearchBrandDataSet(SearchBrandViewType.POPULAR, popularList))
        moduleList.add(SearchBrandDataSet(SearchBrandViewType.LETTER, keywordList))
        moduleList.add(SearchBrandDataSet(SearchBrandViewType.LETTERLIST, brandLetterList))

        result.postValue(moduleList)
    }
}

data class SearchBrandDataSet(
    val viewType: SearchBrandViewType,
    var data: Any? = null
)

enum class SearchBrandViewType {
    POPULAR,
    LETTER,
    LETTERLIST
}
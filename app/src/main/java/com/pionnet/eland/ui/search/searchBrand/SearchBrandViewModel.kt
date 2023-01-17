package com.pionnet.eland.ui.search.searchBrand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.*
import com.pionnet.eland.ui.main.CommonViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BrandViewModel : CommonViewModel() {

    private val repository by lazy { SearchBrandRepository() }

    val result = MutableLiveData<MutableList<SearchBrandDataSet>>()

    private val moduleList = mutableListOf<SearchBrandDataSet>()
    private val popularList = mutableListOf<SearchRank>()

    private var keywordList = listOf<SearchBrandKeywordData.Data?>()
    private var brandLetterList = listOf<SearchBrandKeywordListData.Data?>()

    override fun requestData() {
        viewModelScope.launch {
            merge(repository.requestSearchPopularStream()
                , repository.requestSearchKeywordStream()
                , repository.requestSearchKeywordListStream("ㄱ"))
                .catch {}
                .onEach {
                    it.fold(
                        onSuccess = {
                            when (it) {
                                is SearchRawRankData -> {
                                    it.result?.let {  data ->
                                        data.forEach { resultData ->
                                            resultData?.let { value ->
                                                if (value.size == 2) {
                                                    popularList.add(SearchRank(value[1], value[0]))
                                                }
                                            }
                                        }
                                    }
                                }
                                is SearchBrandKeywordData -> {
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

                                }
                                is SearchBrandKeywordListData -> {
                                    it.data?.get(0)?.let { data ->
                                        brandLetterList = data
                                    }
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

    fun requestKeywordList(keyword: String) {
        viewModelScope.launch {
            repository.requestSearchKeywordListStream(keyword).collect {
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
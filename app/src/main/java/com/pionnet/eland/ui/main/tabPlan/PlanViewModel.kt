package com.pionnet.eland.ui.main.tabPlan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.model.Category
import com.pionnet.eland.model.LuckyDealData
import com.pionnet.eland.model.PlanData
import com.pionnet.eland.model.Status
import com.pionnet.eland.ui.main.CommonViewModel
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.launch

class PlanViewModel : CommonViewModel() {
    private val repository by lazy { PlanRepository() }

    val result = MutableLiveData<MutableList<ModuleData>>()

    private val moduleList = mutableListOf<ModuleData>()

    private var planCategoryList = listOf<PlanData.Data.CategoryList>()

    override fun requestData() {
        viewModelScope.launch {
            repository.requestPlanStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data?.let { data ->
                            setPlanModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setPlanModules(data: PlanData.Data) {
        if (!data.categoryList.isNullOrEmpty()) {
            planCategoryList = data.categoryList
            val categoryList = data.categoryList.mapIndexed { index, item ->
                Category(imageUrl = item.image, title = item.name, isSelected = index == 0)
            }

            moduleList.add(
                ModuleData.CommonCategoryTab(categoryList)
            )
        }

        if (!data.planList.isNullOrEmpty()) {
            data.planList.forEach {
                moduleList.add(
                    ModuleData.PlanGoodsData(it.goods, it.imageUrl, it.linkUrl)
                )
            }
        }

        result.postValue(moduleList)
    }
}
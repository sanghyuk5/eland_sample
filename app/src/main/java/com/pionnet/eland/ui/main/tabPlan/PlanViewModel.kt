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
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let { planData ->
                        if (!planData.categoryList.isNullOrEmpty()) {
                            planCategoryList = planData.categoryList!!
                            val categoryList = planData.categoryList!!.mapIndexed { index, item ->
                                Category(imageUrl = item.image, title = item.name, isSelected = index == 0)
                            }

                            moduleList.add(
                                ModuleData.CommonCategoryTab(
                                    categoryList
                                )
                            )
                        }

                        if (!planData.planList.isNullOrEmpty()) {
                            planData.planList!!.forEach {
                                moduleList.add(
                                    ModuleData.PlanGoodsData(
                                        it.goods, it.imageUrl, it.linkUrl
                                    )
                                )
                            }
                        }

                        result.postValue(moduleList)
                    }
                }
            }
        }
    }

}
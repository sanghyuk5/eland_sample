package com.pionnet.eland.ui.goodsdetail.order

import androidx.lifecycle.MutableLiveData
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.model.*
import com.pionnet.eland.model.GoodsDetailData.Data.OrderInfoText
import com.pionnet.eland.model.GoodsDetailData.Data.StoreInfo
import com.pionnet.eland.ui.goodsdetail.holder.ViewEntity
import com.pionnet.eland.ui.viewholder.MarginEntity
import com.pionnet.eland.utils.toPx

class GoodsDetailOrderViewModel : BaseViewModel() {

    val result = MutableLiveData<MutableList<ViewTypeDataSet>>()

    fun createModules(data: GoodsDetailData.Data.OrderInfo) {
        val module = mutableListOf<ViewTypeDataSet>()

        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("배송정보", true)))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("-------------------------------------------------------------")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("주문하실시 평균배송일은 주말/공휴일 제외한 1~3일정도가 소요됩니다")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("보다 정확하고 원활한 배송이 될 수 있도록 노력하겠습니다.")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("-------------------------------------------------------------")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("[택배사&배송정보]")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("CJ택배사로 배송해드립니다. *파업지역인곳은 롯데택배, 우체국택배로 배송해드립니다.*")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("주문/결제하신후 주말/공휴일제외한 1~3일정도 소요됩니다. 참고해주세요")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("도서산간 제주도지역은 특수지역으로 3,000원이 부과됩니다. 참고해주세요")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("-------------------------------------------------------------")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("[교환&반품 택배비/차액금 입금계좌번호]")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("은행명: ${data.bankInfo}")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("계좌번호: ${data.accountInfo}")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("예금주명: ${data.bankOwner}")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("*주문자명/입금자명 동일하게 기재해주세요*")))

        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("*교환/환불", true)))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("반품 및 교환은 상품 수령 후 7일 이내에 신청하실 수 있습니다.")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("재화 등의 내용이 표시, 광고의 내용과 다르거나 계약내용과 다르게 이행된 경우에는 전자상거래법 제17조3항에 따라 청약철회를 할 수 있습니다.")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("교환/환불이 발생하는 경우 그 원인을 제공한 자가 배송비를 부담합니다.")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 고객변심 : 최초 배송비 + 반품 배송비 + (교환의 경우) 교환 배송비는 고객이 부담")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 판매자귀책 : 최초 배송비 + 반품 배송비 + (교환의 경우) 교환 배송비는 판매자가 부담")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("다음의 경우는 교환 및 환불이 불가능합니다.")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 반품/교환 가능 기간을 초과한 경우")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 상품 및 구성품을 분실하였거나 취급부주의로 인한 오염/파손/고장된 경우")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 상품을 착용하였거나 세탁, 수선한 경우")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 소비자 과실로 인한 옷의 변색(예 : 착생, 화장품 오염 등)")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 착용으로 인한 니트류 상품의 늘어남 발생 및 가죽 제품의 주름 발생")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 명품은 택 제거 후 반품 불가")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 상품의 가치가 현저히 감소하여 재판매가 불가할 경우")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 구매확정된 주문의 경우")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("  - 귀금속류의 경우는 소비자분쟁해결 기준에 의거 교환만 가능합니다.")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("(단, 함량미달의 경우에는 환불이 가능함)")))
        module.add(ViewTypeDataSet(ViewType.FIRST, OrderInfoText("-------------------------------------------------------------")))

        module.add(ViewTypeDataSet(ViewType.SECOND, MarginEntity(height = 5.toPx, color = "#f4f5f7")))
        module.add(ViewTypeDataSet(ViewType.THIRD, ViewEntity(height = 50.toPx, start = 15.toPx, title = "스토어 정보", isLineVisible = true)))

        val storeInfoList = mutableListOf<StoreInfo>()
        if (!data.businessName.isNullOrEmpty()) {
            storeInfoList.add(StoreInfo("상호명", data.businessName))
        }
        if (!data.representative.isNullOrEmpty()) {
            storeInfoList.add(StoreInfo("대표자", data.representative))
        }
        if (!data.businessNumber.isNullOrEmpty()) {
            storeInfoList.add(StoreInfo("사업자번호", data.businessNumber))
        }
        if (!data.mailOrderNumber.isNullOrEmpty()) {
            storeInfoList.add(StoreInfo("통신판매업번호", data.mailOrderNumber))
        }
        if (!data.address.isNullOrEmpty()) {
            storeInfoList.add(StoreInfo("통신판매업번호", data.address))
        }

        module.add(ViewTypeDataSet(ViewType.FOURTH, storeInfoList))
        module.add(ViewTypeDataSet(ViewType.SECOND, MarginEntity(height = 5.toPx, color = "#f4f5f7")))

        module.add(ViewTypeDataSet(ViewType.THIRD, ViewEntity(height = 50.toPx, start = 15.toPx, title = "스토어 고객센터", isLineVisible = true)))

        val storeCustomerList = mutableListOf<StoreInfo>()
        if (!data.customerCenter.isNullOrEmpty()) {
            storeCustomerList.add(StoreInfo("time", data.customerCenter))
        }
        if (!data.mailAddress.isNullOrEmpty()) {
            storeCustomerList.add(StoreInfo("mail", data.mailAddress))
        }
        if (!data.callNumber.isNullOrEmpty()) {
            storeCustomerList.add(StoreInfo("call", data.callNumber))
        }

        module.add(ViewTypeDataSet(ViewType.FIFTH, storeCustomerList))

        module.add(ViewTypeDataSet(ViewType.SECOND, MarginEntity(height = 120.toPx, color = "#ffffff")))

        result.postValue(module)
    }
}
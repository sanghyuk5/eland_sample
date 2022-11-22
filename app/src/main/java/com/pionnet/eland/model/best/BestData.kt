package com.pionnet.eland.model.best

import com.google.gson.annotations.SerializedName

data class BestData (
    @field:SerializedName("code") val code: String,
    @field:SerializedName("data") val data: Data,
    @field:SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @field:SerializedName("disp_ctg_list") var disp_ctg_list: List<DispCtgList> = emptyList()
    ) {
        data class DispCtgList (
            @field:SerializedName("img_path") var img_path: String?,
            @field:SerializedName("disp_ctg_show_nm") var disp_ctg_show_nm: String,
            @field:SerializedName("disp_ctg_no") var disp_ctg_no: String,
            @field:SerializedName("ldisp_ctg_show_nm") var ldisp_ctg_show_nm: String,
            @field:SerializedName("ldisp_ctg_no") var ldisp_ctg_no: String
        )
    }
}
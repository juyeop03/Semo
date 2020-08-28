package kr.hs.dgsw.stac.semo.viewmodel

import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.InfoLaundryAdapter

class InfoViewModel : BaseViewModel() {

    val infoLaundryAdapter = InfoLaundryAdapter()

    fun setLaundryInfoList(laundryInfoModelList: ArrayList<LaundryInfoModel>) {
        infoLaundryAdapter.setList(laundryInfoModelList)
        infoLaundryAdapter.notifyDataSetChanged()
    }
}
package kr.hs.dgsw.stac.semo.viewmodel

import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.InfoLaundryAdapter

class InfoViewModel : BaseViewModel() {

    val laundryInfoModelList = ArrayList<LaundryInfoModel>()
    val infoLaundryAdapter = InfoLaundryAdapter()

    val onSaveEvent = SingleLiveEvent<Unit>()

    fun setLaundryInfoList() {
        infoLaundryAdapter.setList(laundryInfoModelList)
        infoLaundryAdapter.notifyDataSetChanged()
    }

    fun saveEvent() {
        onSaveEvent.call()
    }
}
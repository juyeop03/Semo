package kr.hs.dgsw.stac.semo.viewmodel

import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.MyLaundryAdapter

class MainViewModel : BaseViewModel() {

    val myLaundryModelList = ArrayList<MyLaundryModel>()
    val myLaundryAdapter = MyLaundryAdapter()

    val onAddEvent = SingleLiveEvent<Unit>()

    fun setList() {
        myLaundryAdapter.setList(myLaundryModelList)
        myLaundryAdapter.notifyDataSetChanged()
    }

    fun addEvent() {
        onAddEvent.call()
    }
}
package kr.hs.dgsw.stac.semo.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.InfoLaundryAdapter

class MyLaundryViewModel : BaseViewModel() {

    lateinit var myLaundryModel: MyLaundryModel

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    val onBackEvent = SingleLiveEvent<Unit>()

    val laundryInfoModelList = ArrayList<LaundryInfoModel>()
    val infoLaundryAdapter = InfoLaundryAdapter()

    fun setData() {
        title.value = myLaundryModel.title
        content.value = myLaundryModel.content
    }

    fun setList() {
        infoLaundryAdapter.setList(laundryInfoModelList)
        infoLaundryAdapter.notifyDataSetChanged()
    }

    fun backEvent() {
        onBackEvent.call()
    }
}
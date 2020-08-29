package kr.hs.dgsw.stac.semo.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.SelectSymbolAdapter

class AddViewModel : BaseViewModel() {

    val selectSymbolAdapter = SelectSymbolAdapter()
    var laundryList = ArrayList<String>()

    val date = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val memo = MutableLiveData<String>()
    val image = MutableLiveData<String>()

    val onCameraEvent = SingleLiveEvent<Unit>()
    val onSaveEvent = SingleLiveEvent<Unit>()

    fun setSelectSymbolList() {
        selectSymbolAdapter.setList(laundryList)
        selectSymbolAdapter.notifyDataSetChanged()
    }

    fun cameraEvent() {
        onCameraEvent.call()
    }

    fun saveEvent() {
        onSaveEvent.call()
    }
}
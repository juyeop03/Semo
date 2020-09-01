package kr.hs.dgsw.stac.semo.viewmodel.view

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.SelectSymbolAdapter

class AddViewModel : BaseViewModel() {

    val selectSymbolAdapter = SelectSymbolAdapter()
    var laundryList = ArrayList<String>()

    var date = MutableLiveData<String>()
    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()
    var imageUrl = MutableLiveData<String>()

    val onCameraEvent = SingleLiveEvent<Unit>()
    val onSaveEvent = SingleLiveEvent<Unit>()
    val onFailEvent = SingleLiveEvent<Unit>()

    fun setSelectSymbolList() {
        selectSymbolAdapter.setList(laundryList)
        selectSymbolAdapter.notifyDataSetChanged()
    }

    fun cameraEvent() {
        onCameraEvent.call()
    }
    fun saveEvent() {
        if (title.value != null && content.value != null) onSaveEvent.call()
        else onFailEvent.call()
    }
}
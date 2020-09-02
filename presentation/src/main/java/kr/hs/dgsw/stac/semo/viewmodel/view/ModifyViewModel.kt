package kr.hs.dgsw.stac.semo.viewmodel.view

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.SelectSymbolAdapter

class ModifyViewModel : BaseViewModel() {

    lateinit var myLaundryModel: MyLaundryModel

    var date = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    var imageUrl = MutableLiveData<String>()

    val onCameraEvent = SingleLiveEvent<Unit>()
    val onModifyEvent = SingleLiveEvent<Unit>()
    val onFailEvent = SingleLiveEvent<Unit>()

    var laundryList = ArrayList<String>()
    val selectSymbolAdapter = SelectSymbolAdapter()

    fun setData() {
        title.value = myLaundryModel.title
        content.value = myLaundryModel.content
        laundryList = myLaundryModel.laundry as ArrayList<String>
    }
    fun setList() {
        selectSymbolAdapter.setList(laundryList)
        selectSymbolAdapter.notifyDataSetChanged()
    }

    fun cameraEvent() {
        onCameraEvent.call()
    }
    fun modifyEvent() {
        if (!title.value.isNullOrBlank() && !content.value.isNullOrBlank()) onModifyEvent.call()
        else onFailEvent.call()
    }
}
package kr.hs.dgsw.stac.semo.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.MyLaundryAdapter

class MainViewModel : BaseViewModel() {

    val myLaundryModelList = ArrayList<MyLaundryModel>()
    val myLaundryAdapter = MyLaundryAdapter()

    val firstLaundry = MutableLiveData<MyLaundryModel>()
    val secondLaundry = MutableLiveData<MyLaundryModel>()
    val thirdLaundry = MutableLiveData<MyLaundryModel>()

    val onFirstEvent = SingleLiveEvent<Unit>()
    val onSecondEvent = SingleLiveEvent<Unit>()
    val onThirdEvent = SingleLiveEvent<Unit>()
    val onAddEvent = SingleLiveEvent<Unit>()

    fun setList() {
        myLaundryAdapter.setList(myLaundryModelList)
        myLaundryAdapter.notifyDataSetChanged()
    }

    fun addEvent() {
        onAddEvent.call()
    }

    fun firstEvent() {
        if (firstLaundry.value != null) onFirstEvent.call()
    }
    fun secondEvent() {
        if (secondLaundry.value != null) onSecondEvent.call()
    }
    fun thirdEvent() {
        if (thirdLaundry.value != null) onThirdEvent.call()
    }
}
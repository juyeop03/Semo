package kr.hs.dgsw.stac.semo.viewmodel.view

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class AgreementViewModel : BaseViewModel() {

    val onCloseEvent = SingleLiveEvent<Unit>()
    val onNextEvent = SingleLiveEvent<Unit>()
    val onInformationEvent = SingleLiveEvent<Unit>()

    val agree = MutableLiveData<Boolean>(false)

    fun closeEvent() {
        onCloseEvent.call()
    }
    fun informationEvent() {
        onInformationEvent.call()
    }
    fun nextEvent() {
        onNextEvent.call()
    }
}
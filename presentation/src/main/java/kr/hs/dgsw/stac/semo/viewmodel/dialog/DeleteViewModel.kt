package kr.hs.dgsw.stac.semo.viewmodel.dialog

import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class DeleteViewModel : BaseViewModel() {

    val onPositiveEvent = SingleLiveEvent<Unit>()
    val onNegativeEvent = SingleLiveEvent<Unit>()

    fun positiveEvent() {
        onPositiveEvent.call()
    }

    fun negativeEvent() {
        onNegativeEvent.call()
    }
}
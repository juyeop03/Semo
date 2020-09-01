package kr.hs.dgsw.stac.semo.viewmodel.view

import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class SelectViewModel : BaseViewModel() {

    val onBackEvent = SingleLiveEvent<Unit>()
    val onCameraEvent = SingleLiveEvent<Unit>()
    val onSelectEvent = SingleLiveEvent<Unit>()

    fun backEvent() {
        onBackEvent.call()
    }
    fun cameraEvent() {
        onCameraEvent.call()
    }
    fun selectEvent() {
        onSelectEvent.call()
    }
}
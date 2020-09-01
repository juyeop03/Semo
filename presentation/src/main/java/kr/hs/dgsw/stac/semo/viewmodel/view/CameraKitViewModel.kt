package kr.hs.dgsw.stac.semo.viewmodel.view

import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class CameraKitViewModel : BaseViewModel() {

    val onDetectEvent = SingleLiveEvent<Unit>()

    fun detectEvent() {
        onDetectEvent.call()
    }
}
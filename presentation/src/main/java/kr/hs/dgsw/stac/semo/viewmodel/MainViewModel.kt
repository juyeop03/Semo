package kr.hs.dgsw.stac.semo.viewmodel

import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class MainViewModel : BaseViewModel() {

    val onAddEvent = SingleLiveEvent<Unit>()

    fun addEvent() {
        onAddEvent.call()
    }
}
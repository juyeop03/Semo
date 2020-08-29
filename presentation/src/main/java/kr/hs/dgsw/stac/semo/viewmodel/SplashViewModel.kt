package kr.hs.dgsw.stac.semo.viewmodel

import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.`object`.UserObject

class SplashViewModel : BaseViewModel() {

    val onFailEvent = SingleLiveEvent<Unit>()
    val onSuccessEvent = SingleLiveEvent<Unit>()

    init {
        if (UserObject.userUid.isNotEmpty()) onSuccessEvent.call()
        else onFailEvent.call()
    }
}
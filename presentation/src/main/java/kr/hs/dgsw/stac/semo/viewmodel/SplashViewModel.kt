package kr.hs.dgsw.stac.semo.viewmodel

import android.content.Context
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class SplashViewModel(
    context: Context
) : BaseViewModel() {

    val onFailEvent = SingleLiveEvent<Unit>()
    val onSuccessEvent = SingleLiveEvent<Unit>()

    init {
        if (SharedPreferencesManager.getUserUid(context) != null) onSuccessEvent.call()
        else onFailEvent.call()
    }
}
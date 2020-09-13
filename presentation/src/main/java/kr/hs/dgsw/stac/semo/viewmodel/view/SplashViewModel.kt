package kr.hs.dgsw.stac.semo.viewmodel.view

import android.content.Context
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class SplashViewModel(
    val context: Context
) : BaseViewModel() {

    val onSuccessEvent = SingleLiveEvent<Unit>()
    val onFailEvent = SingleLiveEvent<Unit>()

    init {
        if (SharedPreferencesManager.getUserUid(context) != null) onSuccessEvent.call()
        else onFailEvent.call()
    }
}
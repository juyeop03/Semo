package kr.hs.dgsw.stac.semo.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class SignInViewModel : BaseViewModel() {

    var email = MutableLiveData<String>()
    var pw = MutableLiveData<String>()

    val onSignInEvent = SingleLiveEvent<Unit>()
    val onGoogleSignInEvent = SingleLiveEvent<Unit>()
    val onSignUpEvent = SingleLiveEvent<Unit>()

    fun signInEvent() {
        onSignInEvent.call()
    }
    fun googleSignInEvent() {
        onGoogleSignInEvent.call()
    }

    fun signUpEvent() {
        onSignUpEvent.call()
    }
}
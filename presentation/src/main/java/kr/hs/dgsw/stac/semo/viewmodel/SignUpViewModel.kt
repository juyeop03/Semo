package kr.hs.dgsw.stac.semo.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import java.util.regex.Pattern

class SignUpViewModel: BaseViewModel() {

    val email = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val name = MutableLiveData<String>()

    val onSuccessEvent = SingleLiveEvent<Unit>()
    val onFailEvent = SingleLiveEvent<Unit>()

    fun signUpEvent() {
        if (checkData()) onSuccessEvent.call()
        else onFailEvent.call()
    }

    private fun checkData() : Boolean {
        return if (email.value.isNullOrEmpty() || pw.value.isNullOrEmpty() || name.value.isNullOrEmpty()) false
        else checkEmail() && checkPw()
    }
    private fun checkEmail() : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()
    }
    private fun checkPw() : Boolean {
        return pw.value?.length!! >= 6
    }
}
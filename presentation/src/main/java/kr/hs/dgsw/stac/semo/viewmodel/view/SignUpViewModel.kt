package kr.hs.dgsw.stac.semo.viewmodel.view

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import java.lang.Exception

class SignUpViewModel: BaseViewModel() {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val onCompleteEvent = SingleLiveEvent<Unit>()
    val onCompleteErrorEvent = SingleLiveEvent<Unit>()
    val onErrorEvent = SingleLiveEvent<Unit>()

    val email = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val recommandName = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    fun signUpEvent() {
        if (checkData()) signUp()
        else onErrorEvent.call()
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

    private fun signUp() {
        setIsLoadingTrue()
        firebaseAuth.createUserWithEmailAndPassword(email.value.toString(), pw.value.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setIsLoadingFalse()
                    setUserData(firebaseAuth.uid.toString())

                    onCompleteEvent.call()
                } else {
                    setIsLoadingFalse()
                    onCompleteErrorEvent.call()
                }
            }
            .addOnFailureListener {
                setIsLoadingFalse()
                onFailureData.value = it
            }
    }

    private fun setUserData(uid: String) {
        val userData = HashMap<String, Any>()
        userData["email"] = email.value.toString().trim()
        userData["name"] = name.value.toString()
        userData["recommandName"] = if (recommandName.value.isNullOrBlank()) "" else recommandName.value.toString()

        firebaseFireStore.collection("user").document(uid)
            .set(userData)
            .addOnFailureListener {
                onFailureData.value = it
            }
    }
}
package kr.hs.dgsw.stac.semo.viewmodel.view

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.semo.R
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import java.lang.Exception

class SignInViewModel(
    val context: Context
) : BaseViewModel() {

    val GOOGLE_LOGIN_CODE = 9001

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var googleSignInClient: GoogleSignInClient

    val onCompleteEvent = SingleLiveEvent<Unit>()
    val onCompleteErrorEvent = SingleLiveEvent<Unit>()
    val onSignUpEvent = SingleLiveEvent<Unit>()

    var email = MutableLiveData<String>()
    var pw = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()
    var onActivityResultData = MutableLiveData<Intent>()

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun signInEvent() {
        setIsLoadingTrue()
        firebaseAuth.signInWithEmailAndPassword(email.value.toString(), pw.value.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setIsLoadingFalse()

                    SharedPreferencesManager.setUserUid(context, task.result!!.user!!.uid)
                    onCompleteEvent.call()
                } else {
                    setIsLoadingFalse()
                    onCompleteErrorEvent.call()
                }
            }
    }

    fun googleSignInEvent() {
        setIsLoadingTrue()

        val signInIntent = googleSignInClient.signInIntent
        onActivityResultData.value = signInIntent
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setIsLoadingFalse()
                    setUserData(account, firebaseAuth.uid.toString())

                    SharedPreferencesManager.setUserUid(context, task.result!!.user!!.uid)
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

    private fun setUserData(account: GoogleSignInAccount, uid: String) {
        val fireStore = FirebaseFirestore.getInstance()
        val userData = HashMap<String, Any>()

        userData["email"] = account.email.toString()
        userData["name"] = account.displayName.toString()

        fireStore.collection("user")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (document.data["email"]!! == account.email.toString()) return@addOnCompleteListener
                    }
                    fireStore.collection("user").document(uid)
                        .set(userData)
                        .addOnFailureListener {
                            onFailureData.value = it
                        }
                }
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }

    fun signUpEvent() {
        onSignUpEvent.call()
    }
}
package kr.hs.dgsw.stac.semo.view.view

import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySignUpBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.SignUpViewModel
import kr.hs.dgsw.stac.semo.widget.extension.longToastMessage
import kr.hs.dgsw.stac.semo.widget.extension.shortToastMessage
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {

    override val viewModel: SignUpViewModel
        get() = getViewModel(SignUpViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onSuccessEvent.observe(this@SignUpActivity, Observer {
                signUp()
                setIsLoadingTrue()
            })
            onFailEvent.observe(this@SignUpActivity, Observer {
                shortToastMessage("회원가입 형식에 맞게 작성해주시기 바랍니다.")
            })
        }
    }

    private fun signUp() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(viewModel.email.value!!, viewModel.pw.value!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    shortToastMessage("회원가입을 성공했습니다.")
                    viewModel.setIsLoadingFalse()

                    setUserData(firebaseAuth.uid.toString())
                    startActivityWithFinish(applicationContext, SignInActivity::class.java)
                } else {
                    longToastMessage(task.exception.toString())
                    viewModel.setIsLoadingFalse()
                }
            }
    }

    private fun setUserData(uid: String) {
        val fireStore = FirebaseFirestore.getInstance()
        val userData = HashMap<String, Any>()

        userData["email"] = viewModel.email.value!!.trim()
        userData["name"] = viewModel.name.value!!

        fireStore.collection("user").document(uid)
            .set(userData)
            .addOnFailureListener {
                longToastMessage(it.message.toString())
            }
    }
}
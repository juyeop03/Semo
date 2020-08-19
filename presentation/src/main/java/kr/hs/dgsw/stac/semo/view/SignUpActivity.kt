package kr.hs.dgsw.stac.semo.view

import android.telephony.PhoneNumberFormattingTextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySignInBinding
import kr.hs.dgsw.stac.semo.viewmodel.SignUpViewModel
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignUpActivity : BaseActivity<ActivitySignInBinding, SignUpViewModel>() {

    override val viewModel: SignUpViewModel
        get() = getViewModel(SignUpViewModel::class)

    override fun init() {
        phoneNumberInputEditText.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }
    override fun observerViewModel() {
        with(viewModel) {
            onSuccessEvent.observe(this@SignUpActivity, Observer {
                signUp()
            })
            onFailEvent.observe(this@SignUpActivity, Observer {
                Toast.makeText(applicationContext, "회원가입 형식에 맞게 작성해주시기 바랍니다.", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun signUp() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(viewModel.email.value!!, viewModel.pw.value!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) setUserData()
                else Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun setUserData() {
        val fireStore = FirebaseFirestore.getInstance()
        val userData = HashMap<String, Any>()
        userData["email"] = viewModel.email.value!!
        userData["pw"] = viewModel.pw.value!!
        userData["name"] = viewModel.name.value!!
        userData["phone"] = viewModel.phone.value!!

        fireStore.collection("user")
            .add(userData)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "회원가입 성공", Toast.LENGTH_SHORT).show()
                startActivityWithFinish(applicationContext, SignInActivity::class.java)
            }
    }
}
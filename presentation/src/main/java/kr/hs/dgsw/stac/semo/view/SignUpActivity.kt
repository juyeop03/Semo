package kr.hs.dgsw.stac.semo.view

import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySignUpBinding
import kr.hs.dgsw.stac.semo.viewmodel.SignUpViewModel
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
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "회원가입을 성공했습니다.", Toast.LENGTH_SHORT).show()
                    setUserData()
                }
                else Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun setUserData() {
        val fireStore = FirebaseFirestore.getInstance()
        val userData = HashMap<String, Any>()
        userData["email"] = viewModel.email.value!!
        userData["name"] = viewModel.name.value!!

        fireStore.collection("user")
            .add(userData)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "DB에 데이터를 추가했습니다.", Toast.LENGTH_SHORT).show()
                startActivityWithFinish(applicationContext, SignInActivity::class.java)
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}
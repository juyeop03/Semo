package kr.hs.dgsw.stac.semo.view

import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySignInBinding
import kr.hs.dgsw.stac.semo.viewmodel.SignInViewModel
import kr.hs.dgsw.stac.semo.widget.extension.startActivityNoFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>() {

    override val viewModel: SignInViewModel
        get() = getViewModel(SignInViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onSignInEvent.observe(this@SignInActivity, Observer {
                signIn()
            })
            onSignUpEvent.observe(this@SignInActivity, Observer {
                startActivityNoFinish(applicationContext, SignUpActivity::class.java)
            })
        }
    }

    private fun signIn() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(viewModel.email.value.toString(), viewModel.pw.value.toString())
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) Toast.makeText(applicationContext, "로그인을 성공하였습니다.", Toast.LENGTH_SHORT).show()
                else Toast.makeText(applicationContext, "로그인을 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
    }
}
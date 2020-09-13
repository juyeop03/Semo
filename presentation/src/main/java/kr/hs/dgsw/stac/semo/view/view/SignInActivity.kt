package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.Auth
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySignInBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.SignInViewModel
import kr.hs.dgsw.stac.semo.widget.extension.longToastMessage
import kr.hs.dgsw.stac.semo.widget.extension.shortToastMessage
import kr.hs.dgsw.stac.semo.widget.extension.startActivity
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignInActivity : BaseActivity<ActivitySignInBinding, SignInViewModel>() {

    override val viewModel: SignInViewModel
        get() = getViewModel(SignInViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onCompleteEvent.observe(this@SignInActivity, Observer {
                shortToastMessage("로그인을 성공하였습니다.")
                startActivityWithFinish(applicationContext, MainActivity::class.java)
            })
            onCompleteErrorEvent.observe(this@SignInActivity, Observer {
                shortToastMessage("로그인을 실패하였습니다.")
            })
            onFailureData.observe(this@SignInActivity, Observer {
                longToastMessage(it.message.toString())
            })
            onActivityResultData.observe(this@SignInActivity, Observer {
                startActivityForResult(it, GOOGLE_LOGIN_CODE)
            })
            onSignUpEvent.observe(this@SignInActivity, Observer {
                startActivity(applicationContext, SignUpActivity::class.java)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == viewModel.GOOGLE_LOGIN_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result!!.isSuccess) {
                val account = result.signInAccount
                viewModel.firebaseAuthWithGoogle(account!!)
            } else {
                viewModel.setIsLoadingFalse()
                longToastMessage(result.status.toString())
            }
        }
    }
}
package kr.hs.dgsw.stac.semo.view.view

import androidx.lifecycle.Observer
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
            onCompleteEvent.observe(this@SignUpActivity, Observer {
                shortToastMessage("회원가입을 성공했습니다.")
                startActivityWithFinish(applicationContext, SignInActivity::class.java)
            })
            onCompleteErrorEvent.observe(this@SignUpActivity, Observer {
                shortToastMessage("회원가입을 실패했습니다.")
            })
            onErrorEvent.observe(this@SignUpActivity, Observer {
                shortToastMessage("회원가입 형식에 맞게 작성해주세요.")
            })
            onFailureData.observe(this@SignUpActivity, Observer {
                longToastMessage(it.message.toString())
            })
        }
    }
}
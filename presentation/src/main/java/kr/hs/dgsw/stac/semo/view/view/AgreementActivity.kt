package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.Observer
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityAgreementBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.AgreementViewModel
import kr.hs.dgsw.stac.semo.widget.extension.shortToastMessage
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AgreementActivity : BaseActivity<ActivityAgreementBinding, AgreementViewModel>() {

    override val viewModel: AgreementViewModel
        get() = getViewModel(AgreementViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onCloseEvent.observe(this@AgreementActivity, Observer {
                onBackPressed()
            })
            onInformationEvent.observe(this@AgreementActivity, Observer {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://juyeop.tistory.com/notice/41")))
            })
            onNextEvent.observe(this@AgreementActivity, Observer {
                if (agree.value == true) startActivityWithFinish(applicationContext, SignUpActivity::class.java)
                else shortToastMessage("약관동의가 필요합니다.")
            })
        }
    }
}
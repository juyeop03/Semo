package kr.hs.dgsw.stac.semo.view.view

import androidx.lifecycle.Observer
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySelectBinding
import kr.hs.dgsw.stac.semo.viewmodel.SelectViewModel
import kr.hs.dgsw.stac.semo.widget.extension.startActivityNoFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SelectActivity : BaseActivity<ActivitySelectBinding, SelectViewModel>() {

    override val viewModel: SelectViewModel
        get() = getViewModel(SelectViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onBackEvent.observe(this@SelectActivity, Observer {
                onBackPressed()
            })
            onCameraEvent.observe(this@SelectActivity, Observer {
                startActivityNoFinish(applicationContext, CameraKitActivity::class.java)
            })
            onSelectEvent.observe(this@SelectActivity, Observer {
                // 선택 화면으로 이동
            })
        }
    }
}
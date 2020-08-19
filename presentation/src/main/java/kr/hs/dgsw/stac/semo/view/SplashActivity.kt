package kr.hs.dgsw.stac.semo.view

import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySplashBinding
import kr.hs.dgsw.stac.semo.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel
        get() = getViewModel(SplashViewModel::class)

    override fun init() {}
    override fun observerViewModel() {}
}
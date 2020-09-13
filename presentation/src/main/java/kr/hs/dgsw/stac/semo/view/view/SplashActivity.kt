package kr.hs.dgsw.stac.semo.view.view

import android.os.Handler
import androidx.lifecycle.Observer
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivitySplashBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.SplashViewModel
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel
        get() = getViewModel(SplashViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        val handler = Handler()
        var runnable : Runnable

        with(viewModel) {
            onSuccessEvent.observe(this@SplashActivity, Observer {
                runnable = Runnable { startActivityWithFinish(applicationContext, MainActivity::class.java) }
                handler.postDelayed(runnable, 2000)
            })
            onFailEvent.observe(this@SplashActivity, Observer {
                runnable = Runnable { startActivityWithFinish(applicationContext, SignInActivity::class.java) }
                handler.postDelayed(runnable, 2000)
            })
        }
    }
}
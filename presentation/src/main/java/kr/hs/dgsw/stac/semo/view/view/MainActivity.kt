package kr.hs.dgsw.stac.semo.view.view

import androidx.lifecycle.Observer
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityMainBinding
import kr.hs.dgsw.stac.semo.viewmodel.MainViewModel
import kr.hs.dgsw.stac.semo.widget.extension.startActivityNoFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel
        get() = getViewModel(MainViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onAddEvent.observe(this@MainActivity, Observer {
                startActivityNoFinish(applicationContext, SelectActivity::class.java)
            })
        }
    }
}
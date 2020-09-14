package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityInfoBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.InfoViewModel
import kr.hs.dgsw.stac.semo.widget.extension.longToastMessage
import kr.hs.dgsw.stac.semo.widget.extension.startActivityExtraWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class InfoActivity : BaseActivity<ActivityInfoBinding, InfoViewModel>() {

    override val viewModel: InfoViewModel
        get() = getViewModel(InfoViewModel::class)

    override fun init() {
        viewModel.laundryList = intent.getStringArrayListExtra("laundryList")!!
        viewModel.getLaundryInfo()
    }
    override fun observerViewModel(){
        with(viewModel) {
            onSaveEvent.observe(this@InfoActivity, Observer {
                startActivityExtraWithFinish(Intent(applicationContext, AddActivity::class.java).putExtra("laundryList", laundryList))
            })
            onFailureData.observe(this@InfoActivity, Observer {
                longToastMessage(it.message.toString())
            })
        }
    }
}
package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityInfoBinding
import kr.hs.dgsw.stac.semo.viewmodel.InfoViewModel
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithExtra
import org.koin.androidx.viewmodel.ext.android.getViewModel

class InfoActivity : BaseActivity<ActivityInfoBinding, InfoViewModel>() {

    private var laundryList = ArrayList<String>()

    override val viewModel: InfoViewModel
        get() = getViewModel(InfoViewModel::class)

    override fun init() {
        laundryList = intent.getStringArrayListExtra("laundryList")!!

        for((idx, laundry) in laundryList.withIndex()) {
            getLaundryInfo(laundry,idx == laundryList.size - 1)
        }
    }
    override fun observerViewModel(){
        with(viewModel) {
            onSaveEvent.observe(this@InfoActivity, Observer {
                startActivityWithExtra(Intent(applicationContext, AddActivity::class.java).putExtra("laundryList", laundryList))
            })
        }
    }

    private fun getLaundryInfo(laundry: String, isLast:Boolean) {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("washerMethod").document(laundry)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val title = it.result!!.get("title").toString()
                    val sub = it.result!!.get("sub").toString()
                    viewModel.laundryInfoModelList.add(LaundryInfoModel(laundry, title, sub))

                    if(isLast) viewModel.setLaundryInfoList()
                }
            }
    }
}
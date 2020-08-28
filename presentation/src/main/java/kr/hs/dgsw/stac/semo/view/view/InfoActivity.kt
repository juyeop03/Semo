package kr.hs.dgsw.stac.semo.view.view

import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityInfoBinding
import kr.hs.dgsw.stac.semo.viewmodel.InfoViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class InfoActivity : BaseActivity<ActivityInfoBinding, InfoViewModel>() {

    private var laundryList = ArrayList<String>()
    private val laundryInfoModelList = ArrayList<LaundryInfoModel>()

    override val viewModel: InfoViewModel
        get() = getViewModel(InfoViewModel::class)

    override fun init() {
        laundryList = intent.getStringArrayListExtra("laundryList")!!

        for((idx, laundry) in laundryList.withIndex()) {
            getLaundryInfo(laundry,idx == laundryList.size - 1)
        }
    }
    override fun observerViewModel(){}

    private fun getLaundryInfo(laundry: String, isLast:Boolean) {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("washerMethod").document(laundry)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val title = it.result!!.get("title").toString()
                    val sub = it.result!!.get("sub").toString()
                    laundryInfoModelList.add(LaundryInfoModel(laundry, title, sub))

                    if(isLast) viewModel.setLaundryInfoList(laundryInfoModelList)
                }
            }
    }
}
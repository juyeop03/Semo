package kr.hs.dgsw.stac.semo.view.view

import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_my_laundry.*
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityMyLaundryBinding
import kr.hs.dgsw.stac.semo.viewmodel.MyLaundryViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MyLaundryActivity : BaseActivity<ActivityMyLaundryBinding, MyLaundryViewModel>() {

    override val viewModel: MyLaundryViewModel
        get() = getViewModel(MyLaundryViewModel::class)

    override fun init() {
        viewModel.myLaundryModel = intent.getSerializableExtra("myLaundryModel") as MyLaundryModel
        Glide.with(applicationContext).load(viewModel.myLaundryModel.imageUri).into(laundryImage)

        for((idx, laundry) in viewModel.myLaundryModel.laundry.withIndex()) {
            getLaundryInfo(laundry, idx == viewModel.myLaundryModel.laundry.size - 1)
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            onBackEvent.observe(this@MyLaundryActivity, Observer {
                onBackPressed()
            })
        }
    }

    private fun getLaundryInfo(laundry: String, isLast: Boolean) {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("washerMethod").document(laundry)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val title = it.result!!.get("title").toString()
                    val sub = it.result!!.get("sub").toString()

                    viewModel.laundryInfoModelList.add(LaundryInfoModel(laundry, title, sub))

                    if(isLast) {
                        viewModel.setData()
                        viewModel.setList()
                    }
                }
            }
    }
}
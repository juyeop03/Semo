package kr.hs.dgsw.stac.semo.view.view

import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityMainBinding
import kr.hs.dgsw.stac.semo.viewmodel.MainViewModel
import kr.hs.dgsw.stac.semo.widget.`object`.UserObject
import kr.hs.dgsw.stac.semo.widget.extension.startActivityNoFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel
        get() = getViewModel(MainViewModel::class)

    override fun init() {
        getMyMethod()
    }
    override fun observerViewModel() {
        with(viewModel) {
            onAddEvent.observe(this@MainActivity, Observer {
                startActivityNoFinish(applicationContext, SelectActivity::class.java)
            })
            myLaundryAdapter.onItemClickEvent.observe(this@MainActivity, Observer {
                // 상세화면으로 이동
            })
        }
    }

    private fun getMyMethod() {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("userWasher").document(UserObject.userUid).collection("date")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result?.size() != 0) {
                    for ((idx, document) in task.result!!.withIndex()) {
                        val result = document.data
                        viewModel.myLaundryModelList.add(MyLaundryModel(result["date"].toString(), result["title"].toString(), result["content"].toString(),
                                                                        result["imageUri"].toString(), result["laundry"] as List<String>))

                        if (idx == task.result!!.size()-1) viewModel.setList()
                    }
                } else {
                    viewModel.setList()
                }
            }
    }
}
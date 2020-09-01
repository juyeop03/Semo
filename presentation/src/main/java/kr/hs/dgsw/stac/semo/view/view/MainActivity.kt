package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityMainBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.MainViewModel
import kr.hs.dgsw.stac.semo.widget.extension.startActivityNoFinish
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithExtraNoFinish
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
                startActivityWithExtraNoFinish(Intent(applicationContext, MyLaundryActivity::class.java).putExtra("myLaundryModel", it))
            })
            onFirstEvent.observe(this@MainActivity, Observer {
                startActivityWithExtraNoFinish(Intent(applicationContext, MyLaundryActivity::class.java).putExtra("myLaundryModel", firstLaundry.value))
            })
            onSecondEvent.observe(this@MainActivity, Observer {
                startActivityWithExtraNoFinish(Intent(applicationContext, MyLaundryActivity::class.java).putExtra("myLaundryModel", secondLaundry.value))
            })
            onThirdEvent.observe(this@MainActivity, Observer {
                startActivityWithExtraNoFinish(Intent(applicationContext, MyLaundryActivity::class.java).putExtra("myLaundryModel", thirdLaundry.value))
            })
        }
    }

    private fun getMyMethod() {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("userWasher").document(SharedPreferencesManager.getUserUid(applicationContext).toString()).collection("date")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result?.size() != 0) {
                    for ((idx, document) in task.result!!.withIndex()) {
                        val result = document.data
                        viewModel.myLaundryModelList.add(MyLaundryModel(result["date"].toString(), result["title"].toString(), result["content"].toString(),
                                                                        result["imageUri"].toString(), result["laundry"] as List<String>))

                        if (idx == task.result!!.size()-1) {
                            viewModel.setList()
                            setRecentMethod()
                        }
                    }
                } else {
                    viewModel.setList()
                    setRecentMethod()
                }
            }
    }

    private fun setRecentMethod() {
        with(viewModel) {
            when {
                myLaundryModelList.size >= 3 -> {
                    firstLaundry.value = myLaundryModelList[myLaundryModelList.size-1]
                    Glide.with(applicationContext).load(firstLaundry.value!!.imageUri).into(imageView1)

                    secondLaundry.value = myLaundryModelList[myLaundryModelList.size-2]
                    Glide.with(applicationContext).load(secondLaundry.value!!.imageUri).into(imageView2)

                    thirdLaundry.value = myLaundryModelList[myLaundryModelList.size-3]
                    Glide.with(applicationContext).load(thirdLaundry.value!!.imageUri).into(imageView3)
                }
                myLaundryModelList.size >= 2 -> {
                    firstLaundry.value = myLaundryModelList[myLaundryModelList.size-1]
                    Glide.with(applicationContext).load(firstLaundry.value!!.imageUri).into(imageView1)

                    secondLaundry.value = myLaundryModelList[myLaundryModelList.size-2]
                    Glide.with(applicationContext).load(secondLaundry.value!!.imageUri).into(imageView2)
                }
                myLaundryModelList.size >= 1 -> {
                    firstLaundry.value = myLaundryModelList[myLaundryModelList.size-1]
                    Glide.with(applicationContext).load(firstLaundry.value!!.imageUri).into(imageView1)
                }
                else -> {
                    return@with
                }
            }
        }
    }
}
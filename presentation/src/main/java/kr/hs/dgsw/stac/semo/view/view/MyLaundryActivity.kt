package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_my_laundry.*
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityMyLaundryBinding
import kr.hs.dgsw.stac.semo.view.dialog.DeleteDialog
import kr.hs.dgsw.stac.semo.viewmodel.view.MyLaundryViewModel
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.semo.widget.extension.startActivityExtra
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MyLaundryActivity : BaseActivity<ActivityMyLaundryBinding, MyLaundryViewModel>() {

    override val viewModel: MyLaundryViewModel
        get() = getViewModel(MyLaundryViewModel::class)

    override fun init() {
        viewModel.myLaundryModel = intent.getSerializableExtra("myLaundryModel") as MyLaundryModel
        Glide.with(applicationContext).load(viewModel.myLaundryModel.imageUri).into(laundryImage)

        viewModel.getLaundryInfo()
    }

    override fun observerViewModel() {
        with(viewModel) {
            onImageEvent.observe(this@MyLaundryActivity, Observer {
                startActivityExtra(Intent(applicationContext, ImageActivity::class.java).putExtra("imageUri", myLaundryModel.imageUri))
            })
            onBackEvent.observe(this@MyLaundryActivity, Observer {
                onBackPressed()
            })
            onModifyEvent.observe(this@MyLaundryActivity, Observer {
                startActivityExtra(Intent(applicationContext, ModifyActivity::class.java).putExtra("myLaundryModel", myLaundryModel))
            })
            onDeleteEvent.observe(this@MyLaundryActivity, Observer {
                deleteLaundry()
            })
            onCompleteEvent.observe(this@MyLaundryActivity, Observer {
                startActivityWithFinish(applicationContext, MainActivity::class.java)
            })
        }
    }

    fun deleteLaundry() {
        val dialog = DeleteDialog()
        dialog.show(supportFragmentManager)
        dialog.onDeleteEvent.observe(this, Observer {
            viewModel.deleteLaundryInfo()
        })
    }
}
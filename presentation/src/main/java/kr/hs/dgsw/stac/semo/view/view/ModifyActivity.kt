package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_modify.*
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityModifyBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.ModifyViewModel
import kr.hs.dgsw.stac.semo.widget.`object`.ImageManager
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.semo.widget.extension.shortToastMessage
import kr.hs.dgsw.stac.semo.widget.extension.startActivityExtra
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ModifyActivity : BaseActivity<ActivityModifyBinding, ModifyViewModel>() {

    override val viewModel: ModifyViewModel
        get() = getViewModel(ModifyViewModel::class)

    override fun init() {
        viewModel.myLaundryModel = intent.getSerializableExtra("myLaundryModel") as MyLaundryModel
        Glide.with(applicationContext).load(viewModel.myLaundryModel.imageUri).into(laundryImage)
        Glide.with(applicationContext).load(viewModel.myLaundryModel.imageUri).into(imageView)

        viewModel.setData()
        viewModel.setList()
    }

    override fun observerViewModel() {
        with(viewModel) {
            onImageEvent.observe(this@ModifyActivity, Observer {
                startActivityExtra(Intent(applicationContext, ImageActivity::class.java).putExtra("imageUri", myLaundryModel.imageUri))
            })
            onCameraEvent.observe(this@ModifyActivity, Observer {
                startActivityExtra(Intent(applicationContext, CameraKitActivity::class.java).putExtra("checkCamera", 1))
            })
            onFailEvent.observe(this@ModifyActivity, Observer {
                shortToastMessage("입력한 정보들을 다시 한 번 확인해주세요.")
            })
            onSuccessEvent.observe(this@ModifyActivity, Observer {
                startActivityWithFinish(applicationContext, MainActivity::class.java)
            })
        }
    }

    override fun onResume() {
        super.onResume()

        if (ImageManager.byteArray.isNotEmpty()) {
            viewModel.imageByteArray = ImageManager.byteArray

            var bitmap = BitmapFactory.decodeByteArray(viewModel.imageByteArray, 0, viewModel.imageByteArray.size)
            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false)
            imageView.setImageBitmap(bitmap)
            laundryImage.setImageBitmap(bitmap)
        }
    }
}
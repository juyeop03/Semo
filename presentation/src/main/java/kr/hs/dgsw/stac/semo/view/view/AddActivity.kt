package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add.*
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityAddBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.AddViewModel
import kr.hs.dgsw.stac.semo.widget.`object`.ImageManager
import kr.hs.dgsw.stac.semo.widget.extension.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    override val viewModel: AddViewModel
        get() = getViewModel(AddViewModel::class)

    override fun init() {
        viewModel.selectLaundryList = intent.getStringArrayListExtra("laundryList")!!
        viewModel.setSelectSymbolList()
    }
    override fun observerViewModel() {
        with(viewModel) {
            onCameraEvent.observe(this@AddActivity, Observer {
                startActivityExtra(Intent(applicationContext, CameraKitActivity::class.java).putExtra("checkCamera", 1))
            })
            onFailEvent.observe(this@AddActivity, Observer {
                shortToastMessage("입력한 정보들을 다시 한 번 확인해주세요.")
            })
            onImageEvent.observe(this@AddActivity, Observer {
                shortToastMessage("이미지를 추가해주시기 바랍니다.")
            })
            onCompleteEvent.observe(this@AddActivity, Observer {
                shortToastMessage("세탁법을 저장했습니다.")
                startActivityWithFinish(applicationContext, MainActivity::class.java)
            })
            onFailureData.observe(this@AddActivity, Observer {
                longToastMessage(it.message.toString())
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
        }
    }
}
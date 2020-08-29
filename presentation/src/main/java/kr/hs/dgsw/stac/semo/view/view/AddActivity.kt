package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add.*
import kr.hs.dgsw.stac.domain.UserMethodModel
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityAddBinding
import kr.hs.dgsw.stac.semo.viewmodel.AddViewModel
import kr.hs.dgsw.stac.semo.widget.`object`.ImageObject
import kr.hs.dgsw.stac.semo.widget.`object`.UserObject
import kr.hs.dgsw.stac.semo.widget.extension.dateFormat
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithExtraNoFinish
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    private var imageByteArray = ByteArray(0)

    override val viewModel: AddViewModel
        get() = getViewModel(AddViewModel::class)

    override fun init() {
        viewModel.laundryList = intent.getStringArrayListExtra("laundryList")!!
        viewModel.setSelectSymbolList()
    }
    override fun observerViewModel() {
        with(viewModel) {
            onCameraEvent.observe(this@AddActivity, Observer {
                startActivityWithExtraNoFinish(Intent(applicationContext, CameraKitActivity::class.java).putExtra("onCameraEvent", 1))
            })
            onFailEvent.observe(this@AddActivity, Observer {
                Toast.makeText(applicationContext, "입력한 정보들을 다시 한 번 확인해주세요.", Toast.LENGTH_SHORT).show()
            })
            onSaveEvent.observe(this@AddActivity, Observer {
                if (imageByteArray.isNotEmpty()) {
                    val imageName = Date().dateFormat()
                    val mStorageRef = FirebaseStorage.getInstance().reference
                    val riverRef = mStorageRef.child(UserObject.userUid + "/" + imageName)

                    riverRef.putBytes(imageByteArray)
                        .addOnSuccessListener { task ->
                            task.storage.downloadUrl.addOnSuccessListener { uri ->
                                viewModel.date.value = task.storage.name
                                viewModel.imageUrl.value = uri.toString()
                                setUserWasher()
                            }
                        }
                } else {
                    Toast.makeText(applicationContext, "이미지를 추가해주시기 바랍니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setUserWasher() {
        with(viewModel) {
            val userMethodModel = UserMethodModel(date.value!!, title.value!!, content.value!!, imageUrl.value!!, laundryList)
            val fireStore = FirebaseFirestore.getInstance()
            fireStore.collection("userWasher").document(UserObject.userUid).collection("date").document(date.value!!)
                .set(userMethodModel)
                .addOnCompleteListener {
                    startActivityWithFinish(applicationContext, MainActivity::class.java)
                    Toast.makeText(applicationContext, "세탁법을 저장했습니다.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onResume() {
        super.onResume()
        imageByteArray = ByteArray(0)

        if (ImageObject.byteArray.isNotEmpty()) {
            imageByteArray = ImageObject.byteArray

            var bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false)
            imageView.setImageBitmap(bitmap)
        }
    }
}
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
import kr.hs.dgsw.stac.semo.widget.extension.dateFormat
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithExtraNoFinish
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*

class ModifyActivity : BaseActivity<ActivityModifyBinding, ModifyViewModel>() {

    private var imageByteArray = ByteArray(0)

    override val viewModel: ModifyViewModel
        get() = getViewModel(ModifyViewModel::class)

    override fun init() {
        viewModel.myLaundryModel = intent.getSerializableExtra("myLaundryModel") as MyLaundryModel
        Glide.with(applicationContext).load(viewModel.myLaundryModel.imageUri).into(imageView)

        viewModel.setData()
        viewModel.setList()
    }

    override fun observerViewModel() {
        with(viewModel) {
            onCameraEvent.observe(this@ModifyActivity, Observer {
                startActivityWithExtraNoFinish(Intent(applicationContext, CameraKitActivity::class.java).putExtra("onCameraEvent", 1))
            })
            onFailEvent.observe(this@ModifyActivity, Observer {
                Toast.makeText(applicationContext, "입력한 정보들을 다시 한 번 확인해주세요.", Toast.LENGTH_SHORT).show()
            })
            onModifyEvent.observe(this@ModifyActivity, Observer {
                Toast.makeText(applicationContext, "처리중입니다, 잠시만 기다려주세요", Toast.LENGTH_SHORT).show()

                if (imageByteArray.isNotEmpty()) {
                    val imageName = myLaundryModel.date
                    val mStorageRef = FirebaseStorage.getInstance().reference
                    val riverRef = mStorageRef.child(SharedPreferencesManager.getUserUid(applicationContext) + "/" + imageName)

                    riverRef.delete()
                        .addOnSuccessListener {
                            val imageName2 = myLaundryModel.date
                            val riverRef2 = mStorageRef.child(SharedPreferencesManager.getUserUid(applicationContext) + "/" + imageName2)

                            riverRef2.putBytes(imageByteArray)
                                .addOnSuccessListener { task ->
                                    task.storage.downloadUrl.addOnSuccessListener { uri ->
                                        viewModel.date.value = task.storage.name
                                        viewModel.imageUrl.value = uri.toString()
                                        modifyUserWasher()
                                    }
                                }
                        }
                } else {
                    date.value = myLaundryModel.date
                    imageUrl.value = myLaundryModel.imageUri
                    modifyUserWasher()
                }
            })
        }
    }

    private fun modifyUserWasher() {
        with(viewModel) {
            val userMethodModel = MyLaundryModel(date.value!!, title.value!!, content.value!!, imageUrl.value!!, laundryList)
            val fireStore = FirebaseFirestore.getInstance()
            fireStore.collection("userWasher").document(SharedPreferencesManager.getUserUid(applicationContext).toString()).collection("date").document(myLaundryModel.date)
                .delete()
                .addOnCompleteListener {
                    fireStore.collection("userWasher").document(SharedPreferencesManager.getUserUid(applicationContext).toString()).collection("date").document(date.value!!)
                        .set(userMethodModel)
                        .addOnCompleteListener {
                            startActivityWithFinish(applicationContext, MainActivity::class.java)

                            ImageManager.byteArray = ByteArray(0)
                            Toast.makeText(applicationContext, "세탁법을 수정했습니다.", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                }
        }
    }

    override fun onResume() {
        super.onResume()

        if (ImageManager.byteArray.isNotEmpty()) {
            imageByteArray = ImageManager.byteArray

            var bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false)
            imageView.setImageBitmap(bitmap)
        }
    }
}
package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add.*
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityAddBinding
import kr.hs.dgsw.stac.semo.viewmodel.AddViewModel
import kr.hs.dgsw.stac.semo.widget.`object`.ImageObject
import kr.hs.dgsw.stac.semo.widget.`object`.UserObject
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithExtraNoFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    private lateinit var imageByteArray : ByteArray

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
            onSaveEvent.observe(this@AddActivity, Observer {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val imageName = simpleDateFormat.format(Date())

                viewModel.date = imageName
                viewModel.image = imageName

                val mStorageRef = FirebaseStorage.getInstance().reference
                val riverRef = mStorageRef.child(UserObject.userUid + "/" + imageName)

                riverRef.putBytes(imageByteArray)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Log.e("Test", it.toString())
                    }
            })
        }
    }

    override fun onResume() {
        super.onResume()

        if (ImageObject.byteArray.isNotEmpty()) {
            imageByteArray = ImageObject.byteArray

            var bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray!!.size)
            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false)
            imageView.setImageBitmap(bitmap)
        }
    }
}
package kr.hs.dgsw.stac.semo.viewmodel.view

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.`object`.ImageManager
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.semo.widget.extension.dateFormat
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.SelectSymbolAdapter
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class AddViewModel(
    val context: Context
) : BaseViewModel() {

    var imageByteArray = ByteArray(0)

    var selectLaundryList = ArrayList<String>()
    val selectSymbolAdapter = SelectSymbolAdapter()

    val firebaseFireStore = FirebaseFirestore.getInstance()

    val date = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    val onCameraEvent = SingleLiveEvent<Unit>()
    val onFailEvent = SingleLiveEvent<Unit>()
    val onImageEvent = SingleLiveEvent<Unit>()
    val onCompleteEvent = SingleLiveEvent<Unit>()

    fun setSelectSymbolList() {
        selectSymbolAdapter.setList(selectLaundryList)
        selectSymbolAdapter.notifyDataSetChanged()
    }

    fun cameraEvent() {
        onCameraEvent.call()
    }
    fun saveEvent() {
        if (!title.value.isNullOrBlank() && !content.value.isNullOrBlank()) save()
        else onFailEvent.call()
    }

    fun save() {
        if (imageByteArray.isNotEmpty()) {
            setIsLoadingTrue()

            val imageName = Date().dateFormat()
            val mStorageRef = FirebaseStorage.getInstance().reference
            val riverRef = mStorageRef.child(SharedPreferencesManager.getUserUid(context) + "/" + imageName)

            riverRef.putBytes(imageByteArray)
                .addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener { uri ->
                        date.value = task.storage.name
                        imageUrl.value = uri.toString()
                        setUserWasher()
                    }
                }
        } else onImageEvent.call()
    }
    fun setUserWasher() {
        val userMethodModel = MyLaundryModel(date.value.toString(), title.value.toString(), content.value.toString(), imageUrl.value.toString(), selectLaundryList)
        firebaseFireStore.collection("userWasher").document(SharedPreferencesManager.getUserUid(context).toString()).collection("date").document(date.value!!)
            .set(userMethodModel)
            .addOnCompleteListener {
                setIsLoadingFalse()

                ImageManager.byteArray = ByteArray(0)
                onCompleteEvent.call()
            }
            .addOnFailureListener {
                setIsLoadingFalse()
                onFailureData.value = it
            }
    }
}
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
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.SelectSymbolAdapter
import java.lang.Exception

class ModifyViewModel(
    val context: Context
) : BaseViewModel() {

    lateinit var myLaundryModel: MyLaundryModel

    var imageByteArray = ByteArray(0)
    val firebaseFireStore = FirebaseFirestore.getInstance()

    var laundryList = ArrayList<String>()
    val selectSymbolAdapter = SelectSymbolAdapter()

    val onCameraEvent = SingleLiveEvent<Unit>()
    val onFailEvent = SingleLiveEvent<Unit>()
    val onSuccessEvent = SingleLiveEvent<Unit>()

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    var imageUrl = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    fun setData() {
        title.value = myLaundryModel.title
        content.value = myLaundryModel.content
        imageUrl.value = myLaundryModel.imageUri
        laundryList = myLaundryModel.laundry as ArrayList<String>
    }
    fun setList() {
        selectSymbolAdapter.setList(laundryList)
        selectSymbolAdapter.notifyDataSetChanged()
    }

    fun cameraEvent() {
        onCameraEvent.call()
    }
    fun modifyEvent() {
        if (!title.value.isNullOrBlank() && !content.value.isNullOrBlank()) modify()
        else onFailEvent.call()
    }

    fun modify() {
        setIsLoadingTrue()
        if (imageByteArray.isNotEmpty()) {
            val imageName = myLaundryModel.date
            val mStorageRef = FirebaseStorage.getInstance().reference
            val riverRef = mStorageRef.child(SharedPreferencesManager.getUserUid(context) + "/" + imageName)

            riverRef.delete()
                .addOnSuccessListener {
                    val imageName2 = myLaundryModel.date
                    val riverRef2 = mStorageRef.child(SharedPreferencesManager.getUserUid(context) + "/" + imageName2)

                    riverRef2.putBytes(imageByteArray)
                        .addOnSuccessListener { task ->
                            task.storage.downloadUrl.addOnSuccessListener { uri ->
                                imageUrl.value = uri.toString()
                                modifyUserWasher()
                            }
                        }
                }
        } else modifyUserWasher()
    }

    fun modifyUserWasher() {
        val updateData: MutableMap<String, Any> = HashMap()
        updateData["title"] = title.value.toString()
        updateData["content"] = content.value.toString()
        updateData["imageUri"] = imageUrl.value.toString()

        firebaseFireStore.collection("userWasher").document(SharedPreferencesManager.getUserUid(context).toString()).collection("date").document(myLaundryModel.date)
            .update(updateData)
            .addOnSuccessListener {
                setIsLoadingFalse()

                ImageManager.byteArray = ByteArray(0)
                onSuccessEvent.call()
            }
            .addOnFailureListener {
                setIsLoadingFalse()
                onFailureData.value = it
            }
    }
}
package kr.hs.dgsw.stac.semo.viewmodel.view

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.view.view.MainActivity
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.semo.widget.extension.startActivityWithFinish
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.LaundryInfoAdapter

class MyLaundryViewModel(
    val context: Context
): BaseViewModel() {

    lateinit var myLaundryModel: MyLaundryModel

    val firebaseFireStore = FirebaseFirestore.getInstance()

    val laundryInfoList = ArrayList<LaundryInfoModel>()
    val laundryInfoAdapter = LaundryInfoAdapter()

    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    val onBackEvent = SingleLiveEvent<Unit>()
    val onModifyEvent = SingleLiveEvent<Unit>()
    val onDeleteEvent = SingleLiveEvent<Unit>()
    val onCompleteEvent = SingleLiveEvent<Unit>()

    fun getLaundryInfo() {
        firebaseFireStore.collection("washerMethod")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        for (laundry in myLaundryModel.laundry) {
                            if (document.id == laundry) {
                                laundryInfoList.add(LaundryInfoModel(laundry, document["title"].toString(), document["sub"].toString()))
                            }
                        }
                    }
                    setData()
                    setList()
                }
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }

    fun setData() {
        title.value = myLaundryModel.title
        content.value = myLaundryModel.content
    }
    fun setList() {
        laundryInfoAdapter.setList(laundryInfoList)
        laundryInfoAdapter.notifyDataSetChanged()
    }

    fun deleteLaundryInfo() {
        firebaseFireStore.collection("userWasher").document(SharedPreferencesManager.getUserUid(context).toString())
            .collection("date").document(myLaundryModel.date)
            .delete()
            .addOnSuccessListener {
                val mStorageRef = FirebaseStorage.getInstance().reference.storage
                val photoRef = mStorageRef.getReferenceFromUrl(myLaundryModel.imageUri)
                photoRef.delete().addOnSuccessListener {
                    onCompleteEvent.call()
                }
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }

    fun backEvent() {
        onBackEvent.call()
    }
    fun modifyEvent() {
        onModifyEvent.call()
    }
    fun deleteEvent() {
        onDeleteEvent.call()
    }
}
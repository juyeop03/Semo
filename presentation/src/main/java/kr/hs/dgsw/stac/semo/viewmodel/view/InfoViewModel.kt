package kr.hs.dgsw.stac.semo.viewmodel.view

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.LaundryInfoAdapter
import java.lang.Exception

class InfoViewModel : BaseViewModel() {

    var laundryList = ArrayList<String>()
    val laundryInfoList = ArrayList<LaundryInfoModel>()
    val laundryInfoAdapter = LaundryInfoAdapter()

    val firebaseFireStore = FirebaseFirestore.getInstance()

    val onFailureData = MutableLiveData<Exception>()

    val onSaveEvent = SingleLiveEvent<Unit>()

    fun getLaundryInfo() {
        firebaseFireStore.collection("washerMethod")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        for (laundry in laundryList) {
                            if (document.id == laundry) {
                                laundryInfoList.add(LaundryInfoModel(laundry, document["title"].toString(), document["sub"].toString()))
                            }
                        }
                    }
                    setList()
                }
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }

    fun setList() {
        laundryInfoAdapter.setList(laundryInfoList)
        laundryInfoAdapter.notifyDataSetChanged()
    }

    fun saveEvent() {
        onSaveEvent.call()
    }
}
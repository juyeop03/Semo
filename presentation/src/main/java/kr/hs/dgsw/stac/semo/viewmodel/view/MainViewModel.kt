package kr.hs.dgsw.stac.semo.viewmodel.view

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.MyLaundryAdapter
import java.lang.Exception

class MainViewModel(
    val context: Context
) : BaseViewModel() {

    val firebaseFireStore = FirebaseFirestore.getInstance()

    val onRecentEvent = SingleLiveEvent<Unit>()
    val onFirstEvent = SingleLiveEvent<Unit>()
    val onSecondEvent = SingleLiveEvent<Unit>()
    val onThirdEvent = SingleLiveEvent<Unit>()
    val onAddEvent = SingleLiveEvent<Unit>()
    val onMoreEvent = SingleLiveEvent<Unit>()

    val firstLaundry = MutableLiveData<MyLaundryModel>()
    val secondLaundry = MutableLiveData<MyLaundryModel>()
    val thirdLaundry = MutableLiveData<MyLaundryModel>()
    val onFailureData = MutableLiveData<Exception>()

    val myLaundryList = ArrayList<MyLaundryModel>()
    val myLaundryAdapter = MyLaundryAdapter()

    fun getMyMethod() {
        setIsLoadingTrue()
        firebaseFireStore.collection("userWasher").document(SharedPreferencesManager.getUserUid(context).toString())
            .collection("date")
            .get()
            .addOnCompleteListener { task ->
                setIsLoadingFalse()
                if (task.isSuccessful && task.result?.size() != 0) {
                    for ((idx, document) in task.result!!.withIndex()) {
                        val result = document.data
                        myLaundryList.add(MyLaundryModel(result["date"].toString(), result["title"].toString(), result["content"].toString(), result["imageUri"].toString(), result["laundry"] as List<String>))

                        if (idx == task.result!!.size() - 1) {
                            myLaundryAdapter.setList(myLaundryList)
                            myLaundryAdapter.notifyDataSetChanged()

                            setList()
                        }
                    }
                }
            }
            .addOnFailureListener {
                setIsLoadingFalse()
                onFailureData.value = it
            }
    }
    fun setList() {
        myLaundryAdapter.setList(myLaundryList)
        myLaundryAdapter.notifyDataSetChanged()
        onRecentEvent.call()
    }

    fun firstEvent() {
        if (firstLaundry.value != null) onFirstEvent.call()
    }
    fun secondEvent() {
        if (secondLaundry.value != null) onSecondEvent.call()
    }
    fun thirdEvent() {
        if (thirdLaundry.value != null) onThirdEvent.call()
    }
    fun addEvent() {
        onAddEvent.call()
    }
    fun moreEvent() {
        onMoreEvent.call()
    }
}
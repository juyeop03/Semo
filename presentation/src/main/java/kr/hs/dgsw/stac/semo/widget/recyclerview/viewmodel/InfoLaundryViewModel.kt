package kr.hs.dgsw.stac.semo.widget.recyclerview.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.semo.base.BaseViewModel

class InfoLaundryViewModel : BaseViewModel() {

    val title = MutableLiveData<String>()
    val sub = MutableLiveData<String>()

    fun bind(model: LaundryInfoModel) {
        title.value = model.title
        sub.value = model.sub
    }
}
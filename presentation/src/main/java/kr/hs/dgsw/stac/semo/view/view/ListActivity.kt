package kr.hs.dgsw.stac.semo.view.view

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import kr.hs.dgsw.stac.semo.base.BaseActivity
import kr.hs.dgsw.stac.semo.databinding.ActivityListBinding
import kr.hs.dgsw.stac.semo.viewmodel.view.ListViewModel
import kr.hs.dgsw.stac.semo.widget.extension.shortToastMessage
import kr.hs.dgsw.stac.semo.widget.extension.startActivityExtraWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ListActivity : BaseActivity<ActivityListBinding, ListViewModel>() {

    private val laundryList = ArrayList<String>()
    
    override val viewModel: ListViewModel
        get() = getViewModel(ListViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onCheckEvent.observe(this@ListActivity, Observer {
                laundryList.clear()
                for (select in viewModel.waterListAdapter.selectSymbolList) laundryList.add(select)
                for (select in viewModel.bleachingListAdapter.selectSymbolList) laundryList.add(select)
                for (select in viewModel.ironingListAdapter.selectSymbolList) laundryList.add(select)
                for (select in viewModel.dryCleaningListAdapter.selectSymbolList) laundryList.add(select)
                for (select in viewModel.dryListAdapter.selectSymbolList) laundryList.add(select)

                if (laundryList.size != 0) {
                    startActivityExtraWithFinish(Intent(applicationContext, InfoActivity::class.java).putExtra("laundryList", laundryList))
                } else {
                    shortToastMessage("최소 한 개 이상의 세탁 라벨을 선택해주세요!")
                }
            })
        }
    }
}
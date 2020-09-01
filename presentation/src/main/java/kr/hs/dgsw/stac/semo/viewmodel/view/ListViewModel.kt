package kr.hs.dgsw.stac.semo.viewmodel.view

import kr.hs.dgsw.stac.semo.R
import kr.hs.dgsw.stac.semo.base.BaseViewModel
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent
import kr.hs.dgsw.stac.semo.widget.recyclerview.adpater.SymbolLaundryAdapter

class ListViewModel : BaseViewModel() {

    private val waterList = ArrayList<Int>()
    private val bleachingList = ArrayList<Int>()
    private val ironingList = ArrayList<Int>()
    private val dryCleaningList = ArrayList<Int>()
    private val dryList = ArrayList<Int>()

    val waterListAdapter = SymbolLaundryAdapter(0)
    val bleachingListAdapter = SymbolLaundryAdapter(1)
    val ironingListAdapter = SymbolLaundryAdapter(2)
    val dryCleaningListAdapter = SymbolLaundryAdapter(3)
    val dryListAdapter = SymbolLaundryAdapter(4)

    val onCheckEvent = SingleLiveEvent<Unit>()

    init {
        setWaterList()
        setBleachingList()
        setIroningList()
        setDryCleaningList()
        setDryList()

        waterListAdapter.setImageList(waterList)
        bleachingListAdapter.setImageList(bleachingList)
        ironingListAdapter.setImageList(ironingList)
        dryCleaningListAdapter.setImageList(dryCleaningList)
        dryListAdapter.setImageList(dryList)
    }

    private fun setWaterList() {
        waterList.add(R.drawable.img_water1)
        waterList.add(R.drawable.img_water2)
        waterList.add(R.drawable.img_water3)
        waterList.add(R.drawable.img_water4)
        waterList.add(R.drawable.img_water5)
        waterList.add(R.drawable.img_water6)
        waterList.add(R.drawable.img_water7)
    }
    private fun setBleachingList() {
        bleachingList.add(R.drawable.img_bleaching1)
        bleachingList.add(R.drawable.img_bleaching2)
        bleachingList.add(R.drawable.img_bleaching3)
        bleachingList.add(R.drawable.img_bleaching4)
        bleachingList.add(R.drawable.img_bleaching5)
        bleachingList.add(R.drawable.img_bleaching6)
    }
    private fun setIroningList() {
        ironingList.add(R.drawable.img_ironing1)
        ironingList.add(R.drawable.img_ironing2)
        ironingList.add(R.drawable.img_ironing3)
        ironingList.add(R.drawable.img_ironing4)
        ironingList.add(R.drawable.img_ironing5)
        ironingList.add(R.drawable.img_ironing6)
        ironingList.add(R.drawable.img_ironing7)
    }
    private fun setDryCleaningList() {
        dryCleaningList.add(R.drawable.img_drycleaning1)
        dryCleaningList.add(R.drawable.img_drycleaning2)
        dryCleaningList.add(R.drawable.img_drycleaning3)
        dryCleaningList.add(R.drawable.img_drycleaning4)
    }
    private fun setDryList() {
        dryList.add(R.drawable.img_dry1)
        dryList.add(R.drawable.img_dry2)
        dryList.add(R.drawable.img_dry3)
        dryList.add(R.drawable.img_dry4)
        dryList.add(R.drawable.img_dry5)
        dryList.add(R.drawable.img_dry6)
        dryList.add(R.drawable.img_dry7)
        dryList.add(R.drawable.img_dry8)
    }

    fun checkEvent() {
        onCheckEvent.call()
    }
}
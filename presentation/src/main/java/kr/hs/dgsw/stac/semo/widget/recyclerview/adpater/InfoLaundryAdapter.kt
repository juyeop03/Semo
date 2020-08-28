package kr.hs.dgsw.stac.semo.widget.recyclerview.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.stac.domain.LaundryInfoModel
import kr.hs.dgsw.stac.semo.R
import kr.hs.dgsw.stac.semo.databinding.ItemInfoLaundryBinding
import kr.hs.dgsw.stac.semo.widget.recyclerview.viewmodel.InfoLaundryViewModel

class InfoLaundryAdapter : RecyclerView.Adapter<InfoLaundryAdapter.ViewHolder>() {

    private var laundryList = ArrayList<LaundryInfoModel>()

    fun setList(laundryList: ArrayList<LaundryInfoModel>) {
        this.laundryList = laundryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_info_laundry,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(laundryList[position])
    }

    override fun getItemCount(): Int {
        return laundryList.size
    }
    
    class ViewHolder(val binding: ItemInfoLaundryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val infoLaundryAdapter = InfoLaundryAdapter()
        private val viewModel = InfoLaundryViewModel()

        fun bind(model: LaundryInfoModel) {
            viewModel.bind(model)
            binding.imageView.setImageResource(infoLaundryAdapter.checkData(model.name))
            binding.viewModel = viewModel
        }
    }

    private fun checkData(data : String) : Int{
        when (data) {
            "water1" -> return R.drawable.img_water1
            "water2" -> return R.drawable.img_water2
            "water3" -> return R.drawable.img_water3
            "water4" -> return R.drawable.img_water4
            "water5" -> return R.drawable.img_water5
            "water6" -> return R.drawable.img_water6
            "water7" -> return R.drawable.img_water7
            "bleaching1" -> return R.drawable.img_bleaching1
            "bleaching2" -> return R.drawable.img_bleaching2
            "bleaching3" -> return R.drawable.img_bleaching3
            "bleaching4" -> return R.drawable.img_bleaching4
            "bleaching5" -> return R.drawable.img_bleaching5
            "bleaching6" -> return R.drawable.img_bleaching6
            "ironing1" -> return R.drawable.img_ironing1
            "ironing2" -> return R.drawable.img_ironing2
            "ironing3" -> return R.drawable.img_ironing3
            "ironing4" -> return R.drawable.img_ironing4
            "ironing5" -> return R.drawable.img_ironing5
            "ironing6" -> return R.drawable.img_ironing6
            "ironing7" -> return R.drawable.img_ironing7
            "drycleaning1" -> return R.drawable.img_drycleaning1
            "drycleaning2" -> return R.drawable.img_drycleaning2
            "drycleaning3" -> return R.drawable.img_drycleaning3
            "drycleaning4" -> return R.drawable.img_drycleaning4
            "dry1" -> return R.drawable.img_dry1
            "dry2" -> return R.drawable.img_dry2
            "dry3" -> return R.drawable.img_dry3
            "dry4" -> return R.drawable.img_dry4
            "dry5" -> return R.drawable.img_dry5
            "dry6" -> return R.drawable.img_dry6
            "dry7" -> return R.drawable.img_dry7
            "dry8" -> return R.drawable.img_dry8
        }
        return R.drawable.img_water1
    }
}
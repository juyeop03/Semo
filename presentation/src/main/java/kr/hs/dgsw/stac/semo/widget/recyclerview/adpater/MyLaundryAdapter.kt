package kr.hs.dgsw.stac.semo.widget.recyclerview.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.hs.dgsw.stac.domain.MyLaundryModel
import kr.hs.dgsw.stac.semo.R
import kr.hs.dgsw.stac.semo.databinding.ItemMyLaundryBinding
import kr.hs.dgsw.stac.semo.widget.SingleLiveEvent

class MyLaundryAdapter : RecyclerView.Adapter<MyLaundryAdapter.ViewHolder>() {

    private var myLaundryModelList = ArrayList<MyLaundryModel>()
    val onItemClickEvent = SingleLiveEvent<MyLaundryModel>()

    fun setList(myLaundryModelList: ArrayList<MyLaundryModel>) {
        this.myLaundryModelList = myLaundryModelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_my_laundry, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myLaundryModelList[position])
        holder.binding.layout.setOnClickListener {
            onItemClickEvent.value = myLaundryModelList[position]
        }
    }

    override fun getItemCount(): Int {
        return myLaundryModelList.size
    }

    class ViewHolder(val binding : ItemMyLaundryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MyLaundryModel) {
            Glide.with(binding.root).load(model.imageUri).into(binding.imageView)
        }
    }
}
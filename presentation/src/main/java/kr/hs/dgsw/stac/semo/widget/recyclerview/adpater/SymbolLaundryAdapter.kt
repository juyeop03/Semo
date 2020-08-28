package kr.hs.dgsw.stac.semo.widget.recyclerview.adpater

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.stac.semo.R
import kr.hs.dgsw.stac.semo.databinding.ItemSymbolLaundryBinding

class SymbolLaundryAdapter(private val symbol: Int) : RecyclerView.Adapter<SymbolLaundryAdapter.ViewHolder>() {

    private var imageList = ArrayList<Int>()
    var selectSymbolList = ArrayList<String>()

    fun setImageList(imageList : ArrayList<Int>) {
        this.imageList = imageList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_symbol_laundry, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var click = 0

        holder.bind(imageList[position])
        holder.binding.imageView.setOnClickListener {
            if (click == 0) {
                click = 1
                it.setBackgroundColor(Color.parseColor("#3F8EBF"))
                selectSymbolList.add(checkSymbol(symbol, position))
            } else {
                click = 0
                it.setBackgroundColor(Color.parseColor("#FFFFFF"))
                selectSymbolList.remove(checkSymbol(symbol, position))
            }
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    private fun checkSymbol(symbol : Int, position: Int) : String{
        when (symbol) {
            0 -> {
                when (position) {
                    0 -> return "water1"
                    1 -> return "water2"
                    2 -> return "water3"
                    3 -> return "water4"
                    4 -> return "water5"
                    5 -> return "water6"
                    6 -> return "water7"
                }
            }
            1 -> {
                when (position) {
                    0 -> return "bleaching1"
                    1 -> return "bleaching2"
                    2 -> return "bleaching3"
                    3 -> return "bleaching4"
                    4 -> return "bleaching5"
                    5 -> return "bleaching6"
                }
            }
            2 -> {
                when (position) {
                    0 -> return "ironing1"
                    1 -> return "ironing2"
                    2 -> return "ironing3"
                    3 -> return "ironing4"
                    4 -> return "ironing5"
                    5 -> return "ironing6"
                    6 -> return "ironing7"
                }
            }
            3 -> {
                when (position) {
                    0 -> return "drycleaning1"
                    1 -> return "drycleaning2"
                    2 -> return "drycleaning3"
                    3 -> return "drycleaning4"
                }
            }
            4 -> {
                when (position) {
                    0 -> return "dry1"
                    1 -> return "dry2"
                    2 -> return "dry3"
                    3 -> return "dry4"
                    4 -> return "dry5"
                    5 -> return "dry6"
                    6 -> return "dry7"
                    7 -> return "dry8"
                }
            }
        }
        return ""
    }

    class ViewHolder(val binding: ItemSymbolLaundryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image : Int) {
            binding.imageView.setImageResource(image)
        }
    }
}
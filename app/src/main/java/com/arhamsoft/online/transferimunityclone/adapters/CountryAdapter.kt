package com.arhamsoft.online.transferimunityclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.online.transferimunityclone.databinding.CountrySingleItemBinding
import com.arhamsoft.online.transferimunityclone.models.countryModel.CountrySingle

class CountryAdapter(private var countryList: List<CountrySingle>, private val listener: OnCountrySelectListener) :
    RecyclerView.Adapter<CountryAdapter.CountryViewAdapter>() {

    private lateinit var binding: CountrySingleItemBinding

    class CountryViewAdapter(val bind: CountrySingleItemBinding) :
        RecyclerView.ViewHolder(bind.root) {
            fun onBind(country: CountrySingle,listener: OnCountrySelectListener){
                bind.txtCountryName.setOnClickListener {
                    listener.onCountrySelect(country)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewAdapter {

        binding =
            CountrySingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CountryViewAdapter(binding)
    }

    override fun onBindViewHolder(holder: CountryViewAdapter, position: Int) {

        holder.onBind(countryList[position],listener)
        holder.bind.txtCountryName.text = countryList[position].name
    }

    override fun getItemCount(): Int {

        return countryList.size
    }

    fun updateDataIntoCountryAdapter(list: ArrayList<CountrySingle>){
        this.countryList = list
        notifyDataSetChanged()
    }

    interface OnCountrySelectListener{
        fun onCountrySelect(country: CountrySingle)
    }
}
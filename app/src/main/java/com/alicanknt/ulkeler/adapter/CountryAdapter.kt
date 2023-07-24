package com.alicanknt.ulkeler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.alicanknt.ulkeler.R
import com.alicanknt.ulkeler.databinding.ItemCountryBinding
import com.alicanknt.ulkeler.model.Country
import com.alicanknt.ulkeler.util.dowloadFromUrl
import com.alicanknt.ulkeler.util.placeHolderProgressBar
import com.alicanknt.ulkeler.view.FeedFragmentDirections

class CountryAdapter(var countryList:ArrayList<Country>):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){
    class CountryViewHolder(var view :ItemCountryBinding):RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
    holder.view.country = countryList[position]

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.goCountry(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }



    }
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()

    }

}
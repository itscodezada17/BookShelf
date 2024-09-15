package com.example.testapp.auth.signUp.countryListDialogFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.auth.signUp.callbacks.SignUpCallBack
import com.example.testapp.databinding.FragmentCountryListItemBinding

class CountrtyListAdapter(
    var cityList: List<String>,
    val callBack: SignUpCallBack,
    val countrtyListFragment: CountrtyListFragment
) : RecyclerView.Adapter<CountrtyListAdapter.CityListViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CityListViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val binding = FragmentCountryListItemBinding.inflate(inflater, p0, false)
        return CityListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        holder.bind(cityList[position])
    }


    inner class CityListViewHolder(private val binding: FragmentCountryListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(country: String) {
            binding.country = country
            binding.countryName.setOnClickListener {
                callBack.updateCountry(country)
                countrtyListFragment.dismiss()
            }
        }

    }

}
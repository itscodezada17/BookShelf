package com.example.testapp.auth.signUp.countryListDialogFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.auth.signUp.Country
import com.example.testapp.auth.signUp.SignUpViewModel
import com.example.testapp.auth.signUp.callbacks.SignUpCallBack
import com.example.testapp.databinding.FragmentCountryListBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountrtyListFragment : BottomSheetDialogFragment() {
    private var city: String? = ""

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
    }

    private val callBacks by lazy {
        SignUpCallBack(viewModel)
    }

    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!
    companion object {
        val TAG : String = CountrtyListFragment::class.java.simpleName
        fun newInstance(country: String?): CountrtyListFragment {
            val args = Bundle()
            args.putString("country", country)
            val f: CountrtyListFragment = CountrtyListFragment()
            f.setArguments(args)
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCountryListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        city = arguments?.getString("city")
        viewModel.countryLivedata.value.let {
            it?.let { it1->
                setStoreList(it1)
            }
        }


        binding.searchText.doOnTextChanged { _, _, _, _ ->
            val searchString = binding.searchText.text?:""
            if(searchString.isNotEmpty()){
                viewModel.countryLivedata.value?.let {
                    val listOfCountries: MutableList<String> = mutableListOf()
                    for (countries in it){
                        countries.country?.let { country ->
                            if(country.lowercase()?.contains(searchString.toString().lowercase()) == true) {
                                listOfCountries.add(country)
                            }
                        }

                    }

                    binding.storeListRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    binding.storeListRV.adapter = CountrtyListAdapter( listOfCountries, callBacks,this@CountrtyListFragment)
                }
            }
        }



    }

    private fun setStoreList(storeList: List<Country>) {
        if(!storeList.isNullOrEmpty()){
            val listOfStores: MutableList<String> = mutableListOf()
            for (stores in storeList){
                stores.country?.let {
                    listOfStores.add(it)
                }
            }

            binding.storeListRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            binding.storeListRV.adapter = CountrtyListAdapter( listOfStores,callBacks, this@CountrtyListFragment)

        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: BottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                    as FrameLayout?
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet)?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }


}

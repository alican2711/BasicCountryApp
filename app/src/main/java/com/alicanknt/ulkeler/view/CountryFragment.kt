package com.alicanknt.ulkeler.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.alicanknt.ulkeler.view.CountryFragmentArgs
import com.alicanknt.ulkeler.R
import com.alicanknt.ulkeler.databinding.FragmentCountryBinding
import com.alicanknt.ulkeler.util.dowloadFromUrl
import com.alicanknt.ulkeler.util.placeHolderProgressBar
import com.alicanknt.ulkeler.viewmodel.CountryViewModel

class CountryFragment : Fragment() {
    private var countryUuid = 0
    private lateinit var binding : FragmentCountryBinding
    private lateinit var viewModel : CountryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid

        }

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataRoom(countryUuid)


        observeLiveData()

    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {
                binding.selected=country


            }
        })

    }

}
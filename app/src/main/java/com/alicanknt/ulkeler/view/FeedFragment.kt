package com.alicanknt.ulkeler.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alicanknt.ulkeler.view.FeedFragmentDirections
import com.alicanknt.ulkeler.R
import com.alicanknt.ulkeler.adapter.CountryAdapter
import com.alicanknt.ulkeler.databinding.FragmentFeedBinding
import com.alicanknt.ulkeler.util.CustomSharedPreferences
import com.alicanknt.ulkeler.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private lateinit var binding : FragmentFeedBinding
    private lateinit var viewmodel:FeedViewModel
    private var countryAdapter= CountryAdapter(arrayListOf())
    private var customSharedPreferences = CustomSharedPreferences()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewmodel= ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewmodel.refreshData()
        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = countryAdapter



       /* binding.go.setOnClickListener {
            val action = FeedFragmentDirections.goCountry()
            Navigation.findNavController(it).navigate(action)



        }*/
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility=View.GONE
            binding.countryLoading.visibility=View.VISIBLE
            binding.countryError.visibility=View.GONE
            viewmodel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing=false
        }
        observeLiveData()
    }

    fun observeLiveData(){
        viewmodel.countries.observe(viewLifecycleOwner, Observer { countries->
            countries?.let {
                binding.countryList.visibility =View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })
        viewmodel.countryError.observe(viewLifecycleOwner, Observer { countryError->
            countryError?.let {
                if (it){
                    binding.countryError.visibility = View.VISIBLE
                }else{
                    binding.countryError.visibility = View.GONE
                }

            }
        })
        viewmodel.countryLoading.observe(viewLifecycleOwner, Observer { countryLoading->
            countryLoading?.let {
                if (it){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryError.visibility=View.GONE
                }else{
                    binding.countryLoading.visibility = View.GONE

                }
            }
        })
    }


}
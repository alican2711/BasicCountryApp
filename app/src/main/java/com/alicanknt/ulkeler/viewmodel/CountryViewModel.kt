package com.alicanknt.ulkeler.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alicanknt.ulkeler.model.Country
import com.alicanknt.ulkeler.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application):BaseViewModel(application) {
     var countryLiveData = MutableLiveData<Country>()

    fun getDataRoom(uuid:Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDAO()
            val  country =dao.getCountry(uuid)
            countryLiveData.value =country
        }




    }



}
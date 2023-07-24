package com.alicanknt.ulkeler.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alicanknt.ulkeler.model.Country
import com.alicanknt.ulkeler.service.CountryAPIService
import com.alicanknt.ulkeler.service.CountryDao
import com.alicanknt.ulkeler.service.CountryDatabase
import com.alicanknt.ulkeler.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):BaseViewModel(application) {
    private var customSharedPreferences =CustomSharedPreferences(getApplication())
    private val countryAPIservice= CountryAPIService()
    private val disposable =CompositeDisposable()
    private var refreshTime =0.1*60*1000*1000*1000L


    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()


    fun refreshData(){
        val updateTime = customSharedPreferences.getTime()
        if (updateTime !=null && updateTime!=0L && System.nanoTime()- updateTime<refreshTime){
            getDataFromSQLite()

        }else{
            getDataFromAPI()

        }





    }
    fun refreshFromAPI(){
        getDataFromAPI()

    }
    private fun getDataFromSQLite(){
        countryLoading.value =true
        launch {
           val countries= CountryDatabase(getApplication()).countryDAO().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"SQLİTE",Toast.LENGTH_LONG).show()
        }
    }
    private fun getDataFromAPI(){
        countryLoading.value = true
        disposable.addAll(
            countryAPIservice.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                    storeInSQLite(t)
                        Toast.makeText(getApplication(),"API",Toast.LENGTH_LONG).show()



                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value=false
                        countryError.value=true
                        e.printStackTrace()


                    }

                }
                ))


    }
    private fun showCountries(countryList:List<Country>){
        countries.value=countryList
        countryLoading.value =false
        countryError.value=false
    }
    private fun storeInSQLite(list:List<Country>){
        launch {
            val dao = CountryDatabase(getApplication()).countryDAO()
           dao.deleteAllCountries()
           val listLong = dao.insertAll(*list.toTypedArray())
            var i =0
            while (i<list.size){
                list[i].uuid=listLong[i].toInt()
                i += 1
            }
            showCountries(list)
        }
        customSharedPreferences.saveTime(System.nanoTime())


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}
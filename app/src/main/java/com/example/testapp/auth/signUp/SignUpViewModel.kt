package com.example.testapp.auth.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.auth.model.User
import com.example.testapp.auth.signUp.source.SignUpDataSource
import com.example.testapp.di.DispatcherModule
import com.example.testapp.utils.pref.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher val coroutineDispatcher: CoroutineDispatcher,
    private var repo: SignUpDataSource,
    private val userPref: UserPref
): ViewModel() {

    private val _isExistingUserLivedata: MutableLiveData<Boolean> = MutableLiveData()
    val isExistingUserLivedata: LiveData<Boolean> = _isExistingUserLivedata

    private val _signUpLivedata: MutableLiveData<Boolean> = MutableLiveData()
    val signUpLivedata: LiveData<Boolean> = _signUpLivedata

    private val _countryLivedata: MutableLiveData<List<Country>> = MutableLiveData()
    val countryLivedata: LiveData<List<Country>> = _countryLivedata

    private val _countryMapLivedata: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    val countryMapLivedata: LiveData<HashMap<String, String>> = _countryMapLivedata

    private val _countryCurrentLivedata: MutableLiveData<Country> = MutableLiveData()
    val countryCurrentLivedata: LiveData<Country> = _countryCurrentLivedata

    var countryCurrent = ""

    init {
        getCountrylist()
    }

    private fun getCountrylist(){
        viewModelScope.launch(coroutineDispatcher){
            val response1 = viewModelScope.async {
                repo.getCountries()
            }
            val response2 = viewModelScope.async {
                repo.getMapCountry()
            }
            val response3 = viewModelScope.async {
                repo.getCurrentCountry()
            }
            val a= response1.await()
            val b = response2.await()
            val c = response3.await()
            a.onSuccess {
                _countryLivedata.postValue(it)
            }
            a.onFailure {
                Timber.e(it)
            }
            b.onSuccess {
                _countryMapLivedata.postValue(it)
            }
            b.onFailure {
                Timber.e(it)
            }
            c.onSuccess {
                _countryCurrentLivedata.postValue(it)
            }
            c.onFailure {
                Timber.e(it)
            }
        }
    }

    fun updateCurrentCountry(country: String){
        countryCurrent = countryCurrent
        _countryCurrentLivedata.postValue(Country(country))
    }



    fun isExistingUser(email: String){
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.checkEmail(email)
            response.onSuccess {
                _isExistingUserLivedata.postValue(it)
                Timber.d("isExistingUser $it")
            }
            response.onFailure {
                Timber.d("Failed in isExistingUser $it")
            }
        }
    }

    fun signUpUser(user: User){
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.signUpUser(user)
            response.onSuccess {
                _signUpLivedata.postValue(it)
                Timber.d("signUpUser $it")
            }
            response.onFailure {
                Timber.d("Failed in signUpUser $it")
            }
        }
    }

    fun saveData(email: String, name: String, country: String) {
        viewModelScope.launch(coroutineDispatcher) {
            userPref.saveLogIn(email,name, country)
        }
    }

}
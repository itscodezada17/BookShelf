package com.example.testapp.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.di.DispatcherModule
import com.example.testapp.utils.pref.UserPref
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher val coroutineDispatcher: CoroutineDispatcher,
    private val userPref: UserPref
): ViewModel() {

    private val _isLoggedInLivedata: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedInLivedata: LiveData<Boolean> = _isLoggedInLivedata

    private val _userEmailLivedata: MutableLiveData<String> = MutableLiveData()
    val userEmailLivedata: LiveData<String> = _userEmailLivedata
    private val _userNameLivedata: MutableLiveData<String> = MutableLiveData()
    val userNameLivedata: LiveData<String> = _userNameLivedata
    private val _userCountryLivedata: MutableLiveData<String> = MutableLiveData()
    val userCountryLivedata: LiveData<String> = _userCountryLivedata

    init {
        userDetails()
    }

    private fun userDetails(){
        viewModelScope.launch(coroutineDispatcher){
            val isLogIn = viewModelScope.async{
                userPref.isLogIn()
            }

            val userEmail = viewModelScope.async{
                userPref.userEmail()
            }

            val userName = viewModelScope.async{
                userPref.userName()
            }
            val userCountry = viewModelScope.async{
                userPref.userCountry()
            }

            _userEmailLivedata.postValue(userEmail.await())
            _isLoggedInLivedata.postValue(isLogIn.await())
            _userNameLivedata.postValue(userName.await())
            _userCountryLivedata.postValue(userCountry.await())
        }
    }

    fun clearData(){
        viewModelScope.launch(coroutineDispatcher){
            userPref.clearData()
        }
    }

}
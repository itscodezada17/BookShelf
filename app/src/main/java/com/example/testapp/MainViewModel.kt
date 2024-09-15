package com.example.testapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.di.DispatcherModule
import com.example.testapp.utils.pref.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher val coroutineDispatcher: CoroutineDispatcher,
    private val userPref: UserPref
): ViewModel() {

    private val _isLoggedInLivedata: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedInLivedata: LiveData<Boolean> = _isLoggedInLivedata

    private val _userEmailLivedata: MutableLiveData<String> = MutableLiveData()
    val userEmailLivedata: LiveData<String> = _userEmailLivedata



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
            _userEmailLivedata.postValue(userEmail.await())
            _isLoggedInLivedata.postValue(isLogIn.await())
        }
    }
//    val userEmail = userPref.userEmail().stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(),
//        initialValue = ""
//    )
//    val isLogIn = userPref.isLogIn().stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(),
//        initialValue = false
//    )

//    fun saveData(email: String) {
//        viewModelScope.launch(coroutineDispatcher) {
//            userPref.saveLogIn(email)
//        }
//    }
}
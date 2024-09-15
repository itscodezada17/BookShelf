package com.example.testapp.auth.logIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.auth.logIn.source.LogInDataSource
import com.example.testapp.auth.model.LogInResponse
import com.example.testapp.auth.model.User
import com.example.testapp.di.DispatcherModule
import com.example.testapp.utils.pref.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher val coroutineDispatcher: CoroutineDispatcher,
    private var repo: LogInDataSource,
    private val userPref: UserPref
): ViewModel() {

    private val _logInLivedata: MutableLiveData<LogInResponse> = MutableLiveData()
    val logInLivedata: LiveData<LogInResponse> = _logInLivedata

    private val _isLoggedInLivedata: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedInLivedata: LiveData<Boolean> = _isLoggedInLivedata

    private val _userEmailLivedata: MutableLiveData<String> = MutableLiveData()
    val userEmailLivedata: LiveData<String> = _userEmailLivedata

    init {
        userDetails()
    }

    fun logInUser(email: String, password: String){
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.loginUser(email, password)
            response.onSuccess {
                _logInLivedata.postValue(it)
                Timber.d("logInUser $it")
            }
            response.onFailure {
                Timber.d("Failed in logInUser $it")
            }
        }
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

    fun saveData(email: String, name: String, country: String) {
        viewModelScope.launch(coroutineDispatcher) {
            userPref.saveLogIn(email, name, country)
        }
    }

}
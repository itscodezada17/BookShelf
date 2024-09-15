package com.example.testapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.auth.logIn.LogInActivity
import com.example.testapp.books.BooksListActivity
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.utils.pref.UserPref
import com.example.testapp.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private var email: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLogIn()
    }

    private fun checkLogIn() {
        viewModel.isLoggedInLivedata.observe(this){ isLogIn ->
            if (isLogIn){
                Timber.d("MainActivity UserEmail: ${viewModel.userEmailLivedata.value} \n and User is LoggedIn: $isLogIn")
                shortToast("UserEmail: ${viewModel.userEmailLivedata.value} \n and User is LoggedIn: $isLogIn")
                val intent = Intent(this, BooksListActivity::class.java)
                intent.putExtra("email", viewModel.userEmailLivedata.value)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        viewModel.userEmailLivedata.observe(this){
            email = it
        }

//        viewModel.isLogIn.value.let { isLogIn ->
//            if (isLogIn){
//                Timber.d("MainActivity UserEmail: ${viewModel.userEmail.value} \n and User is LoggedIn: ${viewModel.isLogIn.value}")
//                shortToast("UserEmail: ${viewModel.userEmail.value} \n and User is LoggedIn: ${viewModel.isLogIn.value}")
//                val intent = Intent(this, BooksListActivity::class.java)
//                intent.putExtra("email", viewModel.userEmail.value)
//                startActivity(intent)
//                finish()
//            }else{
//                val intent = Intent(this, LogInActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//        }

    }


}
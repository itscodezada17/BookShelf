package com.example.testapp.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.auth.logIn.LogInActivity
import com.example.testapp.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding

    private val viewModel by viewModels<ProfileViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.logOutBtn.setOnClickListener {
            viewModel.clearData()
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUi(email: String, name: String, country: String) {
        binding.userEmail = email
        binding.userCountry = country
        binding.userName = name

    }

    private fun setObservers() {
        viewModel.isLoggedInLivedata.observe(this){ isLogIn ->

        }
        viewModel.userEmailLivedata.observe(this){
            it?.let { email->
                //setUi(email, "", "")
            }
        }
        viewModel.userNameLivedata.observe(this){
            it?.let { name->
               // setUi(email, it, "")
            }
        }
        viewModel.userCountryLivedata.observe(this){
            it?.let {
                setUi(viewModel.userEmailLivedata.value?:"", viewModel.userNameLivedata.value?:"", viewModel.userCountryLivedata.value?:"")
            }
        }

    }


}
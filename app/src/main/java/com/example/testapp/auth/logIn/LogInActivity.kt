package com.example.testapp.auth.logIn

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.auth.logIn.callbacks.LogInCallback
import com.example.testapp.auth.signUp.SignUpActivity
import com.example.testapp.books.BooksListActivity
import com.example.testapp.databinding.ActivityLogInBinding
import com.example.testapp.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    //ktx
    private val viewModel by viewModels<LogInViewModel>()
    private val callback: LogInCallback by lazy {
        LogInCallback(viewModel)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntents()
        setObservers()
        setOnClickListener()
    }

    private fun handleIntents() {
        if (intent.hasExtra("email")){
            val email = intent.getStringExtra("email")
            binding.editTextEmail.setText(email)
        }
    }

    private fun setObservers() {
        viewModel.isLoggedInLivedata.observe(this){ isLogIn ->

        }
        viewModel.userEmailLivedata.observe(this){

        }
        viewModel.logInLivedata.observe(this){
            it?.let {
                if (it.status){
                    shortToast("Welcome")
                    viewModel.saveData(binding.editTextEmail.text.toString(), "", "")
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this, BooksListActivity::class.java)
                        intent.putExtra("email", viewModel.userEmailLivedata.value)
                        startActivity(intent)
                        finish()
                    },2000)
                }else{
                    shortToast("Wrong Password Please Try Again")
                }
            }
        }
    }

    private fun setOnClickListener() {

        binding.existing.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra("email", binding.editTextEmail.text.toString())
            startActivity(intent)
            finish()
        }


        binding.logInBtn.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                viewModel.logInUser(email, password)
            }
        }
    }


}
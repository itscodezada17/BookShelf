package com.example.testapp.auth.signUp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.testapp.auth.logIn.LogInActivity
import com.example.testapp.auth.model.User
import com.example.testapp.auth.signUp.callbacks.SignUpCallBack
import com.example.testapp.auth.signUp.countryListDialogFragment.CountrtyListFragment
import com.example.testapp.books.BooksListActivity
import com.example.testapp.databinding.ActivitySignUpActivtityBinding
import com.example.testapp.utils.longToast
import com.example.testapp.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpActivtityBinding

    //ktx
    private val viewModel by viewModels<SignUpViewModel>()
    private val callback: SignUpCallBack by lazy {
        SignUpCallBack(viewModel)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpActivtityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntents()
        setObservers()
        setOnClickListener()

    }

    private fun handleIntents() {
        val email = intent.getStringExtra("email")
        binding.editTextEmail.setText(email)
    }

    private fun setOnClickListener() {

        binding.textInputMobile.setOnClickListener {
            val countryFragment = CountrtyListFragment.newInstance(viewModel.countryCurrent)
            countryFragment.show(
                supportFragmentManager,
                CountrtyListFragment.TAG
            )
        }

        binding.existing.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            intent.putExtra("email", binding.editTextEmail.text.toString())
            startActivity(intent)
            finish()
        }


        binding.logInBtn.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val country = binding.editTextCountry.text.toString()
            if(isValidPassword(password)){
                if(name.isNotEmpty() && email.isNotEmpty() &&
                    password.isNotEmpty() && country.isNotEmpty()){
                    viewModel.isExistingUser(email = email)
                }
            }else{
                android.app.AlertDialog.Builder(this)
                    .setMessage("Password should contains\n Minimum of 8 characters, including at least one number\n, one special character, one lowercase letter, and one uppercase letter.")
                    .setNeutralButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .show()
            }
        }
    }

    private fun setObservers() {
        
        viewModel.countryLivedata.observe(this){
            it?.let { list->
                setCountryRv(list)
            }
        }

        viewModel.countryCurrentLivedata.observe(this){
            it?.let { country->
                viewModel.countryCurrent = country.country?:""
                binding.editTextCountry.setText(viewModel.countryMapLivedata.value?.get(country.country?:"IN"))
            }
        }

        viewModel.countryMapLivedata.observe(this){
            it?.let { country ->
               //binding.editTextCountry.setText(country[viewModel.countryCurrentLivedata.value?.country])
            }
        }


        
        
        viewModel.isExistingUserLivedata.observe(this){
            if(it){
                shortToast("Existing User")
                val intent = Intent(this, LogInActivity::class.java)
                intent.putExtra("email", binding.editTextEmail.text.toString())
                startActivity(intent)
                finish()
            }else{
                shortToast("New User")
                val name = binding.editTextName.text.toString()
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()
                val country = binding.editTextCountry.text.toString()
                if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && country.isNotEmpty()){
                    viewModel.signUpUser(User(name = name, email = email, password = password, country = country))
                }
            }
        }

        viewModel.signUpLivedata.observe(this){
            it?.let {
                shortToast("Welcome to BookShelf!")
                viewModel.saveData(binding.editTextEmail.text.toString(), binding.editTextName.text.toString(), binding.editTextCountry.text.toString())
                val intent = Intent(this, BooksListActivity::class.java)
                intent.putExtra("user", binding.editTextEmail.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setCountryRv(list: List<Country>) {
        // Set this list in for showing to user
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }


}
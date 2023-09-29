package com.hazel.expensemanager.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hazel.expensemanager.*
import com.hazel.expensemanager.Database.AppDatabase
import com.hazel.expensemanager.Repositories.UserRepository
import com.hazel.expensemanager.ViewModelFactory.UserViewModelFactory
import com.hazel.expensemanager.ProjectViewModels.UserViewModel
import com.hazel.expensemanager.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSHrePreference()

        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)
        val userDao = AppDatabase.getInstance(this).userDao()
        val userRepository = UserRepository(userDao)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(
            UserViewModel::class.java)

        binding.btn.setOnClickListener{
            checkLogin()
        }
        binding.tvSignup.setOnClickListener{
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }
    }

    private fun checkLogin(){
        CoroutineScope(Dispatchers.IO).launch{
            if(userViewModel.findByEmail(binding.etEmail.text.toString())!=null){
                if(userViewModel.matchPassToValidate(binding.etEmail.text.toString())==binding.etPass.text.toString()){
                    withContext(Dispatchers.Main){
                        updateSharePreference()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }
                }
                else{
                    withContext(Dispatchers.Main){
                        binding.etPass.error="Password Incorrect"
                        binding.etEmail.error=null
                    }
                }
            }
            else{
                withContext(Dispatchers.Main){
                    binding.etPass.error=null
                    binding.etEmail.error="Email not Exists"
                }
            }
        }
    }
    private fun updateSharePreference(){
        val spManager = SharedPreferenceManager(this)
        spManager.saveLogin(resources.getString(R.string.checkLogin), true)
    }
    private fun getSHrePreference(){
        val spManager = SharedPreferenceManager(this)
        val isLogged = spManager.getLogin(resources.getString(R.string.checkLogin), false)
        if(isLogged){
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }
    }

}
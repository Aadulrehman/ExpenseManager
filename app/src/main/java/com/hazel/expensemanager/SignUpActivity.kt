package com.hazel.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hazel.expensemanager.databinding.ActivitySignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_sign_up)

        val userDao = AppDatabase.getInstance(this).userDao()
        val userRepository = UserRepository(userDao)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(userRepository)).get(LoginViewModel::class.java)

        binding.btn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                if(loginViewModel.findByEmail(binding.etEmail.text.toString())==null){
                    val user=User(0,binding.etEmail.text.toString(),binding.etName.text.toString(),binding.etPass.text.toString())
                    loginViewModel.insertUser(user)
                }
                else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@SignUpActivity,"User Already Exists",Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }
}
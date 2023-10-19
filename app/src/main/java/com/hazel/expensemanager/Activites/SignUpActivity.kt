package com.hazel.expensemanager.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hazel.expensemanager.*
import com.hazel.expensemanager.Database.AppDatabase
import com.hazel.expensemanager.Entities.User
import com.hazel.expensemanager.Repositories.UserRepository
import com.hazel.expensemanager.ViewModelFactory.UserViewModelFactory
import com.hazel.expensemanager.ProjectViewModels.UserViewModel
import com.hazel.expensemanager.databinding.ActivitySignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        val userDao = AppDatabase.getInstance(this).userDao()
        val userRepository = UserRepository(userDao)
        val spManager = SharedPreferenceManager(this)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository)).get(UserViewModel::class.java)

        binding.btn.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                if(userViewModel.findByEmail(binding.etEmail.text.toString())==null){
                    val user= User(0,binding.etEmail.text.toString(),binding.etName.text.toString(),binding.etPass.text.toString())
                    userViewModel.insertUser(user)
                    spManager.saveLogin(resources.getString(R.string.checkLogin), true)
                    startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                }
                else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@SignUpActivity,resources.getString(R.string.userExists),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnSignIn.setOnClickListener{
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }
    }

}
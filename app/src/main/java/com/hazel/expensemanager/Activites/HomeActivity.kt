package com.hazel.expensemanager.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.hazel.expensemanager.Fragments.AddExpeneseFragment
import com.hazel.expensemanager.Fragments.FilterFragment
import com.hazel.expensemanager.Fragments.HomeFragment
import com.hazel.expensemanager.R
import com.hazel.expensemanager.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinder:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)
        replaceFragment(HomeFragment())
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        viewBinder.bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.add -> replaceFragment(AddExpeneseFragment())
                R.id.filter -> replaceFragment(FilterFragment())
            }
            true
        }

    }
    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}
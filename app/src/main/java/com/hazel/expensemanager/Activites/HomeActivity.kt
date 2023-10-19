package com.hazel.expensemanager.Activites

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hazel.expensemanager.Fragments.AddExpeneseFragment
import com.hazel.expensemanager.Fragments.FilterFragment
import com.hazel.expensemanager.Fragments.HomeFragment
import com.hazel.expensemanager.R
import com.hazel.expensemanager.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinder:ActivityHomeBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var currentFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinder= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinder.root)

        fragmentManager = supportFragmentManager
        currentFragment = HomeFragment()

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, currentFragment).commit()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        viewBinder.bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.home -> selectedFragment =HomeFragment()
                R.id.add -> selectedFragment=AddExpeneseFragment()
                R.id.filter -> selectedFragment=FilterFragment()
            }
            if (selectedFragment != null && selectedFragment.javaClass.simpleName != currentFragment.javaClass.simpleName) {
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, selectedFragment)
                transaction.addToBackStack(null)
                transaction.commit()
                currentFragment = selectedFragment
            }
            true
        }

    }
}
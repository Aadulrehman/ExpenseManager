package com.hazel.expensemanager.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazel.expensemanager.Database.AppDatabase
import com.hazel.expensemanager.Entities.ExpenseManager
import com.hazel.expensemanager.ExpenseAdapter
import com.hazel.expensemanager.ProjectViewModels.AddExpenseViewModel
import com.hazel.expensemanager.ProjectViewModels.ExpenseManagerViewModel
import com.hazel.expensemanager.R
import com.hazel.expensemanager.Repositories.ExpenseManagerRepository
import com.hazel.expensemanager.ViewModelFactory.ExpenseManagerViewModelFactory
import com.hazel.expensemanager.databinding.FragmentAddExpeneseBinding
import com.hazel.expensemanager.databinding.FragmentHomeBinding
import java.lang.Math.abs


class HomeFragment : Fragment() {
    private lateinit var viewModelManager: ExpenseManagerViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)

        val managerDao = AppDatabase.getInstance(requireContext()).expenseManagerDao()
        val managerRepository = ExpenseManagerRepository(managerDao)
        viewModelManager = ViewModelProvider(this, ExpenseManagerViewModelFactory(managerRepository)).get(ExpenseManagerViewModel::class.java)

        recyclerView=binding.recyclerview
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        binding.btnAll.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))

        viewModelManager.allExpenses.observe(viewLifecycleOwner, Observer { expences ->
            val adapter = ExpenseAdapter(expences as ArrayList<ExpenseManager>)
            recyclerView.adapter = adapter
        })

       // binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExpense.setOnClickListener {
            statusButtonColors(binding.btnExpense, binding.btnIncome, binding.btnAll)
            setExpenseAdapter()
        }
        binding.btnIncome.setOnClickListener {
            statusButtonColors(binding.btnIncome, binding.btnExpense, binding.btnAll)
            setIncomeAdapter()
        }
        binding.btnAll.setOnClickListener {
            statusButtonColors(binding.btnAll, binding.btnExpense, binding.btnIncome)
            setAAlldapter()
        }

        viewModelManager.calculateProfit().observe(viewLifecycleOwner, Observer { profit ->
            if (profit >= 0) {
                binding.tvProfit.text = getString(R.string.profit_format, profit)
                binding.tvLoss.text = "0.0"
            } else {
                binding.tvLoss.text = getString(R.string.loss_format, abs(profit))
                binding.tvProfit.text = "0.0"
            }
        })
    }
    private fun statusButtonColors(selectedButton: Button, deselectedButton: Button, deselectedButton2: Button) {
        selectedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        deselectedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
        deselectedButton2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
    }
    private fun setAAlldapter(){
        viewModelManager.allExpenses.observe(viewLifecycleOwner, Observer { expences ->
            val adapter = ExpenseAdapter(expences as ArrayList<ExpenseManager>)
            recyclerView.adapter = adapter
        })
    }
    private fun setIncomeAdapter(){
        viewModelManager.allIncomeList.observe(viewLifecycleOwner, Observer { expences ->
            val adapter = ExpenseAdapter(expences as ArrayList<ExpenseManager>)
            recyclerView.adapter = adapter
        })
    }
    private fun setExpenseAdapter(){
        viewModelManager.allExpenseList.observe(viewLifecycleOwner, Observer { expences ->
            val adapter = ExpenseAdapter(expences as ArrayList<ExpenseManager>)
            recyclerView.adapter = adapter
        })
    }

}
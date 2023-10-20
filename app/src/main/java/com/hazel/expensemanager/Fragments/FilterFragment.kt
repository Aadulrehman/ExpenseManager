package com.hazel.expensemanager.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hazel.expensemanager.Database.AppDatabase
import com.hazel.expensemanager.Entities.ExpenseManager
import com.hazel.expensemanager.ExpenseAdapter
import com.hazel.expensemanager.ProjectViewModels.ExpenseManagerViewModel
import com.hazel.expensemanager.R
import com.hazel.expensemanager.Repositories.ExpenseManagerRepository
import com.hazel.expensemanager.ViewModelFactory.ExpenseManagerViewModelFactory
import com.hazel.expensemanager.databinding.FragmentFilterBinding


class FilterFragment : Fragment() {
    private lateinit var viewModelManager: ExpenseManagerViewModel
    lateinit var binding: FragmentFilterBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_filter,container,false)

        val managerDao = AppDatabase.getInstance(requireContext()).expenseManagerDao()
        val managerRepository = ExpenseManagerRepository(managerDao)
        viewModelManager = ViewModelProvider(this, ExpenseManagerViewModelFactory(managerRepository)).get(ExpenseManagerViewModel::class.java)

        recyclerView=binding.recyclerview
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModelManager.getResultsForLastNDays(1).observe(viewLifecycleOwner,Observer{expences->
            val adapter = ExpenseAdapter(expences as ArrayList<ExpenseManager>)
            recyclerView.adapter = adapter
        })

        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateSlider()
        viewModelManager.getSelectedValue().observe(viewLifecycleOwner, Observer { value ->
            viewModelManager.getResultsForLastNDays(value).observe(viewLifecycleOwner,Observer{expences->
                val adapter = ExpenseAdapter(expences as ArrayList<ExpenseManager>)
                recyclerView.adapter = adapter
            })
        })

        viewModelManager.expenses.observe(viewLifecycleOwner, Observer {value->
            binding.tvExpense.text="$value"
        })
        viewModelManager.income.observe(viewLifecycleOwner, Observer {value->
            binding.tvIncome.text="$value"
        })
    }

    private fun updateSlider(){
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvDays.text = "Last $progress days"
                viewModelManager.setSelectedValue(progress+1)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}
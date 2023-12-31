package com.hazel.expensemanager.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hazel.expensemanager.Database.AppDatabase
import com.hazel.expensemanager.R
import com.hazel.expensemanager.ProjectViewModels.ExpenseManagerViewModel
import com.hazel.expensemanager.Repositories.ExpenseManagerRepository
import com.hazel.expensemanager.Validation
import com.hazel.expensemanager.ViewModelFactory.ExpenseManagerViewModelFactory
import com.hazel.expensemanager.databinding.FragmentAddExpeneseBinding
import java.text.SimpleDateFormat
import java.util.*

class AddExpeneseFragment : Fragment() {
    private var daySelected: String=""
    private var monthSelected:String=""
    private var yearSelected:String=""
    private lateinit var status:String
    lateinit var binding:FragmentAddExpeneseBinding
    private lateinit var viewModelManager: ExpenseManagerViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_add_expenese,container,false)

        val managerDao = AppDatabase.getInstance(requireContext()).expenseManagerDao()
        val managerRepository = ExpenseManagerRepository(managerDao)
        viewModelManager = ViewModelProvider(this, ExpenseManagerViewModelFactory(managerRepository)).get(ExpenseManagerViewModel::class.java)

        status=resources.getString(R.string.Income)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDate.setOnClickListener{
            showDatePickerDialog()
        }
        binding.btnAdd.setOnClickListener{
            if(validateInput()){
                viewModelManager.insertData(status,binding.etTitle.text.toString(),binding.descriptionEditText.text.toString(),binding.etAmount.text.toString(),daySelected,monthSelected,yearSelected)
                clearFields()
            }
        }
        binding.btnExpense.setOnClickListener { statusButtonColors(binding.btnExpense, binding.btnIncome, resources.getString(R.string.Expense))}
        binding.btnIncome.setOnClickListener { statusButtonColors(binding.btnIncome, binding.btnExpense, resources.getString(R.string.Income))}
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val showDate = dateFormat.format(calendar.time)
                binding.etDate.setText(showDate)

                daySelected=dayOfMonth.toString()
                monthSelected=month.toString()
                yearSelected=year.toString()
            },
            year, month, dayOfMonth
        )
        datePickerDialog.datePicker.maxDate=System.currentTimeMillis()-1000
        datePickerDialog.show()
    }
    private fun validateInput():Boolean{
        if(Validation.checkBlank(requireContext(),binding.etTitle.text.toString(),resources.getString(R.string.emptyTitle)) &&
            Validation.checkBlank(requireContext(),binding.descriptionEditText.text.toString(),resources.getString(R.string.emptyDescription)) &&
            Validation.checkBlank(requireContext(),binding.etAmount.text.toString(),resources.getString(R.string.emptyAmount))){
            if(daySelected.isNotBlank() && monthSelected.isNotEmpty() && yearSelected.isNotEmpty()){
                return true
            }else{
                Toast.makeText(requireContext(),resources.getString(R.string.emptyDate),Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }
    private fun statusButtonColors(selectedButton: Button, deselectedButton: Button, newStatus:String) {
        selectedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        deselectedButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey))
        status=newStatus
    }
    private fun clearFields(){
        binding.etDate.text.clear()
        binding.etAmount.text.clear()
        binding.etTitle.text.clear()
        binding.descriptionEditText.text?.clear()
    }

}
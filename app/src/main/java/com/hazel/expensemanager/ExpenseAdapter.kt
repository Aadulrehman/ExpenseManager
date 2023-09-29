package com.hazel.expensemanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hazel.expensemanager.Entities.ExpenseManager
import com.hazel.expensemanager.databinding.ItemLayoutBinding

class ExpenseAdapter(private val dataList: ArrayList<ExpenseManager>): RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=dataList[position]
        holder.bind(currentItem)
    }

    class ViewHolder(private val binding: ItemLayoutBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(item: ExpenseManager) {
            binding.tvAmount.text=item.amount
            binding.tvDescription.text=item.description
            binding.tvTitle.text=item.title
            binding.tvStatus.text=item.status
            binding.tvDate.text="${item.day}-${item.month}-${item.year}"
            binding.executePendingBindings()
        }
    }

}
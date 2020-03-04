package com.ballchalu.ui.ledgers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemCoinLedgersBinding


class LedgersAdapter : RecyclerView.Adapter<LedgersAdapter.ViewHolder>() {
    private var list: List<String>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCoinLedgersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.get(position), position)
    }

    fun setItemList(list: List<String>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return 10// list?.size ?: 10
    }


    inner class ViewHolder(val binding: ItemCoinLedgersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(s: String?, position: Int) {
            with(binding) {

            }

        }
    }

}
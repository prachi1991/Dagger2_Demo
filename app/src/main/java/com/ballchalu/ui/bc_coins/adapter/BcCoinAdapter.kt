package com.ballchalu.ui.bc_coins.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemAllContestBinding
import com.ballchalu.databinding.ItemBcCoinsContestBinding


class BcCoinAdapter : RecyclerView.Adapter<BcCoinAdapter.ViewHolder>() {
    private var list: List<String>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBcCoinsContestBinding.inflate(
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
        return list?.size ?: 2
    }


    inner class ViewHolder(val binding: ItemBcCoinsContestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(s: String?, position: Int) {
            with(binding) {
//                if (s?.isNotEmpty() == true) tvPlayNow.visibility = View.VISIBLE
//                else tvPlayNow.visibility = View.GONE
            }

        }
    }

}
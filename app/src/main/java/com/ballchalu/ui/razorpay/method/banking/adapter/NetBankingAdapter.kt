package com.ballchalu.ui.razorpay.method.banking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemNetbankingBinding


class NetBankingAdapter(val listener: OnClickListener?) :
    RecyclerView.Adapter<NetBankingAdapter.ViewHolder>() {
    private var list: ArrayList<Pair<String, String>>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNetbankingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { holder.setData(it) }
    }

    fun setItemList(list: ArrayList<Pair<String, String>>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class ViewHolder(val binding: ItemNetbankingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(pair: Pair<String, String>) {
            with(binding) {
                tvName.text = pair.second
                tvName.setOnClickListener {
                    listener?.onClicked(pair)
                }
            }
        }
    }

    interface OnClickListener {
        fun onClicked(pair: Pair<String, String>)
    }
}
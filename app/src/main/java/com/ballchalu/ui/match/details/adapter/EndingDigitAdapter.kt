package com.ballchalu.ui.match.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemEndingDigitListBinding
import com.ccpp.shared.domain.match_details.RunnersItem


class EndingDigitAdapter : RecyclerView.Adapter<EndingDigitAdapter.ViewHolder>() {
    private var list: List<RunnersItem>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEndingDigitListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.get(position), position)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun setItemList(market: List<RunnersItem>?) {
        this.list = market
    }


    inner class ViewHolder(val binding: ItemEndingDigitListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(runnersItem: RunnersItem?, position: Int) {
            with(binding) {
                binding.tvEndingDigitValue.text = position.toString()
            }

        }
    }

}
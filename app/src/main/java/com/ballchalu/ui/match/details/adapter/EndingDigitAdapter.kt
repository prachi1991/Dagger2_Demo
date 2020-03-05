package com.ballchalu.ui.match.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemEndingDigitListBinding
import com.ccpp.shared.domain.match_details.Market
import com.ccpp.shared.domain.match_details.RunnersItem


class EndingDigitAdapter : RecyclerView.Adapter<EndingDigitAdapter.ViewHolder>() {
    private var list: Market? = null
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

        holder.setData(list?.runners?.get(position), position)
    }

    override fun getItemCount(): Int {
        return 10// list.size
    }

    fun setItemList(market: Market) {
        this.list = market
    }


    inner class ViewHolder(val binding: ItemEndingDigitListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(s: RunnersItem?, position: Int) {
            with(binding) {
                binding.tvEndingDigitValue.text = position.toString()
            }

        }
    }

}
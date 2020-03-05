package com.ballchalu.ui.match.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemEvenOddListBinding
import com.ccpp.shared.domain.match_details.Market
import com.ccpp.shared.domain.match_details.RunnersItem


class EvenOddAdapter : RecyclerView.Adapter<EvenOddAdapter.ViewHolder>() {
    private var list: Market? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEvenOddListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.runners?.get(position), list?.betfairMarketType)
    }

    fun setItemList(market: Market) {
        this.list = market
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return 1// list.size
    }


    inner class ViewHolder(val binding: ItemEvenOddListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(runnerItem: RunnersItem?, type: String?) {
            with(binding) {
                //                model=EvenOddAdapterData(runnerItem,type)
            }

        }
    }

    data class EvenOddAdapterData(
        var runnerItem: RunnersItem?,
        val type: String?
    ) {

        val evenValue =
            if (runnerItem?.runner?.canBack == true) runnerItem?.runner?.back.toString() else ""
        val oddValue =
            if (runnerItem?.runner?.canBack == true) runnerItem?.runner?.back.toString() else ""
    }

}
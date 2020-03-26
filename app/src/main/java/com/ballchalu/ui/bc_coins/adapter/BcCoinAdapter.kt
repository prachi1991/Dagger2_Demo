package com.ballchalu.ui.bc_coins.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemBcCoinsContestBinding
import com.ccpp.shared.domain.bccoins.BcCoinContest


class BcCoinAdapter(val listener: OnBcCoinListener?) :
    RecyclerView.Adapter<BcCoinAdapter.ViewHolder>() {
    private var list: List<BcCoinContest>? = null
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
        holder.setData(list?.get(position))
    }

    fun setItemList(list: List<BcCoinContest>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class ViewHolder(val binding: ItemBcCoinsContestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(bcCoinContest: BcCoinContest?) {
            with(binding) {
                model = bcCoinContest
                btnBuyNow.setOnClickListener {
                    bcCoinContest?.let { it1 -> listener?.onBuyNowClicked(it1) }
                }
            }
        }
    }

    interface OnBcCoinListener {
        fun onBuyNowClicked(bcCoinContest: BcCoinContest)
    }
}
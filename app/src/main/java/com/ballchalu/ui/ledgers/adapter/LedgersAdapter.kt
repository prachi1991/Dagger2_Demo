package com.ballchalu.ui.ledgers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemCoinLedgersBinding
import com.ccpp.shared.domain.bccoins.BcCoinsLedgerData


class LedgersAdapter : RecyclerView.Adapter<LedgersAdapter.ViewHolder>() {
    private var list: ArrayList<BcCoinsLedgerData> = arrayListOf()
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
        list[position].let { holder.setData(it) }
    }

    fun setItemList(list: List<BcCoinsLedgerData>) {
        val size = this.list.size
        this.list.addAll(list)
        val sizeNew = this.list.size
        notifyItemRangeChanged(size, sizeNew)
    }

    override fun getItemCount(): Int {
        return list.size ?: 0
    }


    inner class ViewHolder(val binding: ItemCoinLedgersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: BcCoinsLedgerData) {
            with(binding) {
                model = item
            }
        }
    }

}
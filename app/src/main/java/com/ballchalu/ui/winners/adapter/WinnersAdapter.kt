package com.ballchalu.ui.winners.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemWinnersBinding
import com.ballchalu.shared.domain.winner.RanksItem


class WinnersAdapter :
    RecyclerView.Adapter<WinnersAdapter.ViewHolder>() {
    private var arrayList: ArrayList<RanksItem> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWinnersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        arrayList[position].let { holder.setData(it) }
    }

    fun setItemList(arrayList: List<RanksItem>) {
        this.arrayList.addAll(arrayList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    inner class ViewHolder(val binding: ItemWinnersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: RanksItem) {
            binding.position.text = item.rank.toString()
            binding.name.text = item.userName.toString()
            binding.price.text = item.wonCoins.toString()
        }
    }

    fun clear() {
        arrayList.clear()
        notifyDataSetChanged()
    }

}
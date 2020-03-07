package com.ballchalu.ui.winners.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemAllContestBinding
import com.ballchalu.databinding.ItemWinnersBinding
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.domain.winner.WinnerRes
import com.ccpp.shared.util.ConstantsBase


class WinnersAdapter(val arrayList: ArrayList<WinnerRes>) :
    RecyclerView.Adapter<WinnersAdapter.ViewHolder>() {

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
        holder.setData(position,arrayList[position])
    }


    override fun getItemCount(): Int {
        return arrayList.size?:0
    }


    inner class ViewHolder(val binding: ItemWinnersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(position: Int,arrayList: WinnerRes) {
            binding.position.text = arrayList.position.toString()
            binding.name.text = arrayList.winnerName.toString()
            binding.price.text = arrayList.winnerPrice.toString()
        }
    }

    fun clear() {
//        list?.clear()
//        notifyDataSetChanged()
    }

}
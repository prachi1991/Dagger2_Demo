package com.ballchalu.ui.match.details.my_bets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemAllContestBinding
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.domain.contest.UserContest
import com.ccpp.shared.domain.my_bets.UserBet
import com.ccpp.shared.util.ConstantsBase


class MyBetsMatchWinnerAdapter(private var onItemClickListener: OnItemClickListener?) :
    RecyclerView.Adapter<MyBetsMatchWinnerAdapter.ViewHolder>() {
    private var list: ArrayList<UserBet>? = null
    private var isMyContest: Boolean = false

    interface OnItemClickListener {
        fun onPlayNowClicked(contestModel: UserContest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAllContestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.get(position), position)
    }

    fun setItemList(list: ArrayList<UserBet>?, status: Boolean = false) {
        isMyContest = status
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class ViewHolder(val binding: ItemAllContestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(userBet: UserBet?, position: Int) {
            with(binding) {
              //  binding.contest = userContest?.contest

            }
        }
    }

    fun clear() {
        list?.clear()
        notifyDataSetChanged()
    }

}
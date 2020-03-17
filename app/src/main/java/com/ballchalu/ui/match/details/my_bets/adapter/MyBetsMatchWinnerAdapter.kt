package com.ballchalu.ui.match.details.my_bets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemMyBetEndingDigitBinding
import com.ballchalu.databinding.ItemMyBetEvenOddBinding
import com.ballchalu.databinding.ItemMyBetMatchWinnerBinding
import com.ballchalu.databinding.ItemMyBetSessionBinding
import com.ccpp.shared.domain.contest.UserContest
import com.ccpp.shared.domain.my_bets.UserMyBet
import com.ccpp.shared.util.ConstantsBase

class MyBetsMatchWinnerAdapter(var type: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: ArrayList<UserMyBet>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            ConstantsBase.MATCH_WINNER ->
                MatchWinnerHolder(ItemMyBetMatchWinnerBinding.inflate(inflater, parent, false))
            ConstantsBase.SESSION ->
                SessionHolder(ItemMyBetSessionBinding.inflate(inflater, parent, false))
            ConstantsBase.EVEN_ODD ->
                EvenOddHolder(ItemMyBetEvenOddBinding.inflate(inflater, parent, false))
            else ->
                EndingDigitHolder(ItemMyBetEndingDigitBinding.inflate(inflater, parent, false))

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MatchWinnerHolder) holder.setData(list?.get(position))
        if (holder is SessionHolder) holder.setData(list?.get(position))
    }

    fun setItemList(list: ArrayList<UserMyBet>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class MatchWinnerHolder(val binding: ItemMyBetMatchWinnerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(userMyBet: UserMyBet?) {
            binding.model = userMyBet

        }
    }

    inner class SessionHolder(val binding: ItemMyBetSessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(userMyBet: UserMyBet?) {
            binding.model = userMyBet
        }
    }

    inner class EvenOddHolder(val binding: ItemMyBetEvenOddBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(userMyBet: UserMyBet?) {
            binding.model = userMyBet
        }
    }

    inner class EndingDigitHolder(val binding: ItemMyBetEndingDigitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(userMyBet: UserMyBet?) {
            binding.model = userMyBet
        }
    }


    fun clear() {
        list?.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onPlayNowClicked(contestModel: UserContest)
    }
}
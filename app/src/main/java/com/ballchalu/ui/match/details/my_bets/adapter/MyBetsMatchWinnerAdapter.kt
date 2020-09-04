package com.ballchalu.ui.match.details.my_bets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.R
import com.ballchalu.databinding.ItemMyBetEndingDigitBinding
import com.ballchalu.databinding.ItemMyBetEvenOddBinding
import com.ballchalu.databinding.ItemMyBetMatchWinnerBinding
import com.ballchalu.databinding.ItemMyBetSessionBinding
import com.ballchalu.utils.StringUtils
import com.ballchalu.shared.domain.contest.UserContest
import com.ballchalu.shared.domain.my_bets.UserMyBet
import com.ballchalu.shared.util.ConstantsBase

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
        if (holder is EvenOddHolder) holder.setData(list?.get(position))
        if (holder is EndingDigitHolder) holder.setData(list?.get(position))
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
            userMyBet?.getInfoModelObject()?.market?.runners?.forEach {
                if (userMyBet.runnerId == it?.runner?.id.toString()) {
                    binding.tvRunnerName.text = StringUtils.getString(
                        R.string.runner_s,
                        it?.runner?.betfairRunnerName ?: " "
                    )
                    binding.tvRate.text =
                        StringUtils.getString(R.string.rate_s, it?.runner?.back ?: "")
                }

            }
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
            userMyBet?.getInfoModelObject()?.market?.runners?.forEach {
                if (userMyBet.runnerId == it?.runner?.id.toString()) {
                    binding.tvRuns.text = StringUtils.getString(
                        R.string.runner_s,
                        it?.runner?.betfairRunnerName ?: " "
                    )
                    binding.tvRate.text =
                        StringUtils.getString(R.string.rate_s, it?.runner?.back ?: "")
                }

            }
        }
    }

    inner class EndingDigitHolder(val binding: ItemMyBetEndingDigitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(userMyBet: UserMyBet?) {
            binding.model = userMyBet
            userMyBet?.getInfoModelObject()?.market?.runners?.forEach {
                if (userMyBet.runnerId == it?.runner?.id.toString()) {
                    binding.tvRuns.text = StringUtils.getString(
                        R.string.runner_s,
                        it?.runner?.betfairRunnerName ?: " "
                    )
                    binding.tvRate.text =
                        StringUtils.getString(R.string.rate_s, it?.runner?.back ?: "")
                }

            }
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
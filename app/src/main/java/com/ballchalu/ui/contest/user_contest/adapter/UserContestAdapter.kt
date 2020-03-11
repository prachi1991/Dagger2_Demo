package com.ballchalu.ui.contest.user_contest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemAllContestBinding
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.domain.contest.UserContest
import com.ccpp.shared.util.ConstantsBase


class UserContestAdapter(private var onItemClickListener: OnItemClickListener?) :
    RecyclerView.Adapter<UserContestAdapter.ViewHolder>() {
    private var list: ArrayList<UserContest>? = null
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

    fun setItemList(list: ArrayList<UserContest>?, status: Boolean = false) {
        isMyContest = status
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class ViewHolder(val binding: ItemAllContestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(userContest: UserContest?, position: Int) {
            with(binding) {
                binding.contest = userContest?.contest
                if (userContest?.contest?.isParticipated == false && !isMyContest) {
                    binding.tvPlayNow.visibility = View.VISIBLE
                    binding.tvPlayNow.text = ConstantsBase.buy_now_key
                } else if (isMyContest) {
                    binding.tvPlayNow.visibility = View.VISIBLE
                    binding.tvPlayNow.text = ConstantsBase.play_now_key
                } else {
                    binding.tvPlayNow.visibility = View.GONE
                }
            }

            binding.tvPlayNow.setOnClickListener {
                if (binding.tvPlayNow.text == ConstantsBase.play_now_key) {
                    userContest?.contest?.let {
                        onItemClickListener?.onPlayNowClicked(userContest)
                    }
                }

            }

            val spotFilled = userContest?.contest?.availableSpots?.toInt()?.let { (userContest.contest!!.spots)?.minus(it) }
            val spot = userContest?.contest?.spots
            val div: Float = spot?.toFloat()?.let { spotFilled?.toFloat()?.div(it) } ?: 0f
            val percentage = div * 100
            binding.progressBar.progress = percentage.toInt()
        }
    }

    fun clear() {
        list?.clear()
        notifyDataSetChanged()
    }

}
package com.ballchalu.ui.contest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemAllContestBinding
import com.ccpp.shared.domain.contest.Contest
import com.ccpp.shared.util.ConstantsBase


class ContestAdapter(
    private var onItemClickListener: OnItemClickListener?,
    private var declared: Boolean? = false
) :
    RecyclerView.Adapter<ContestAdapter.ViewHolder>() {
    private var list: List<Contest>? = null
    private var isMyContest: Boolean = false

    interface OnItemClickListener {
        fun onBuyNowClicked(contestModel: Contest)
        fun onPlayNowClicked(contestModel: Contest)
        fun onResultClicked(contestModel: Contest)
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

    fun setItemList(list: List<Contest>?, status: Boolean = false) {
        isMyContest = status
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class ViewHolder(val binding: ItemAllContestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(contest: Contest?, position: Int) {
            with(binding) {
                this.contest = contest
                if (declared == false) {
                    if (contest?.isParticipated == false && !isMyContest) {
                        tvPlayNow.visibility = View.VISIBLE
                        tvPlayNow.text = ConstantsBase.buy_now_key
                    } else if (isMyContest) {
                        tvPlayNow.visibility = View.VISIBLE
                        tvPlayNow.text = ConstantsBase.play_now_key
                    } else {
                        tvPlayNow.visibility = View.GONE
                    }

                    tvPlayNow.setOnClickListener {
                        if (!isMyContest && tvPlayNow.text == ConstantsBase.buy_now_key) {
                            tvPlayNow.visibility = View.GONE
                            contest?.let {
                                onItemClickListener?.onBuyNowClicked(contest)
                            }

                        } else if (binding.tvPlayNow.text == ConstantsBase.play_now_key) {
                            contest?.let {
                                onItemClickListener?.onPlayNowClicked(contest)
                            }
                        }
                    }
                } else {
                    tvPlayNow.text = ConstantsBase.RESULT
                    tvPlayNow.isVisible = true
                    tvPlayNow.setOnClickListener {
                        if (tvPlayNow.text == ConstantsBase.RESULT && contest != null)
                            onItemClickListener?.onResultClicked(contest)
                    }

                }


                val spotFilled =
                    contest?.availableSpots?.toInt()?.let { (contest.spots)?.minus(it) }
                val spot = contest?.spots
                val div: Float = spot?.toFloat()?.let { spotFilled?.toFloat()?.div(it) } ?: 0f
                val percentage = div * 100
                progressBar.progress = percentage.toInt()
            }
        }
    }

    fun clear() {
        list = null
        notifyDataSetChanged()
    }

}
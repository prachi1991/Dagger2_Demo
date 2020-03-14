package com.ballchalu.ui.match.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemEndingDigitListBinding
import com.ccpp.shared.domain.match_details.Runner
import com.ccpp.shared.domain.match_details.RunnersItem
import com.ccpp.shared.domain.position.PositionMarketItem
import com.ccpp.shared.util.ConstantsBase


class EndingDigitAdapter(val listener: OnItemClickListener?) :
    RecyclerView.Adapter<EndingDigitAdapter.ViewHolder>() {
    private var marketId: Int? = null
    private var positionMarketItem: PositionMarketItem? = null
    private var list: ArrayList<RunnersItem>? = arrayListOf()
    private var isSuspend: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEndingDigitListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.get(position)?.runner, position)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    fun setItemList(
        runnersItemList: List<RunnersItem>?,
        status: String?,
        marketId: Int?
    ) {
        this.list?.clear()
        this.marketId = marketId
        runnersItemList?.let { this.list?.addAll(it) }
        this.isSuspend = status.equals(ConstantsBase.suspend, true)
        notifyDataSetChanged()
    }

    fun updateEndingDigit(
        runnersList: List<RunnersItem>?,
        status: String?
    ) {
        this.isSuspend = status.equals(ConstantsBase.suspend, true)
        runnersList?.forEach { runnersItem ->
            list?.forEach {
                if (it.runner?.id == runnersItem.id) {
                    it.runner?.canBack = runnersItem.canBack
                    it.runner?.back = runnersItem.B
                    it.runner?.marketId = runnersItem.marketId
                    it.runner?.betfairRunnerName = it.runner?.betfairRunnerName
                }
            }
        }
        notifyDataSetChanged()
    }

    fun setPositionList(positionMarketItem: PositionMarketItem?) {
        this.positionMarketItem = positionMarketItem
        list?.forEach { runnerItem ->
            positionMarketItem?.runners?.forEachIndexed { _, item ->
                if (runnerItem.runner?.id ?: 0 == item?.runnerId) {
                    runnerItem.runner?.runnerPosition = item.runnerPosition
                    return@forEachIndexed
                }
            }
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemEndingDigitListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(runner: Runner?, position: Int) {
            with(binding) {
                runner?.let {
                    model = it
                    it.marketId = marketId
                }
                tvTeam1Back.text =
                    if (runner?.canBack == true && !isSuspend) runner.back ?: "" else ""

                tvTeam1Back.setOnClickListener {
                    if (!isSuspend)
                        runner?.let { it1 -> listener?.onBackClicked(it1) }
                }
            }

        }
    }

    interface OnItemClickListener {
        fun onBackClicked(runner: Runner)
    }
}
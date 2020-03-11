package com.ballchalu.ui.match.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemEndingDigitListBinding
import com.ccpp.shared.domain.match_details.Runner
import com.ccpp.shared.domain.match_details.RunnersItem
import com.ccpp.shared.util.ConstantsBase


class EndingDigitAdapter : RecyclerView.Adapter<EndingDigitAdapter.ViewHolder>() {
    private var list: List<RunnersItem>? = null
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


    fun setItemList(runnersItemList: List<RunnersItem>?, status: String?) {
        this.list = runnersItemList
        this.isSuspend = status.equals(ConstantsBase.suspend, true)
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemEndingDigitListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(runnersItem: Runner?, position: Int) {
            with(binding) {
                runnersItem?.let {
                    model = it
                }
                tvTeam1Back.text =
                    if (runnersItem?.canBack == true && !isSuspend) runnersItem.back.toString() else ""
            }

        }
    }

}
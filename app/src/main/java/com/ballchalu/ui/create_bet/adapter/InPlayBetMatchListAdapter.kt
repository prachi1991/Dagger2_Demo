package com.ballchalu.ui.create_bet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.RowBetLivePlayMatchBinding
import com.ballchalu.databinding.RowInPlayMatchListingBinding
import com.ccpp.shared.domain.MatchListingItem
import com.ccpp.shared.util.ConstantsBase


class InPlayBetMatchListAdapter() :
    RecyclerView.Adapter<InPlayBetMatchListAdapter.ViewHolder>() {
    private var list: List<MatchListingItem>? = null
    private var matchStatus: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowBetLivePlayMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.get(position), position)
    }

    fun setItemList(list: List<MatchListingItem>?, match_status: String
    ) {
        this.list = list
        this.matchStatus = match_status
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    interface OnItemClickListener {
        fun onMatchClicked(matchListingItem: MatchListingItem)
    }

    inner class ViewHolder(val binding: RowBetLivePlayMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(matchListingItem: MatchListingItem?, position: Int) {
            with(binding) {
                txtMatchName.text = matchListingItem?.match?.title

//                if(matchStatus.equals(ConstantsBase.UPCOMING,true)){
//                    txtMatchDate.visibility = View.VISIBLE
//                    txtMatchDate.text = matchListingItem?.match?.startTime
//                }
//                else{
//                    txtMatchDate.visibility = View.GONE
//                }

            }

        }
    }

}
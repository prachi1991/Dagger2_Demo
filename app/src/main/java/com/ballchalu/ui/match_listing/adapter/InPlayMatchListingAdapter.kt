package com.ballchalu.ui.match_listing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.RowInPlayMatchListingBinding
import com.ccpp.shared.domain.MatchListingItem
import com.ccpp.shared.util.ConstantsBase


class InPlayMatchListingAdapter(val listener: OnItemClickListener?) :
    RecyclerView.Adapter<InPlayMatchListingAdapter.ViewHolder>() {
    private var list: List<MatchListingItem>? = null
    private var matchStatus: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowInPlayMatchListingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.get(position), position)
    }

    fun setItemList(
        list: List<MatchListingItem>?, match_status: String
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

    inner class ViewHolder(val binding: RowInPlayMatchListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(matchListingItem: MatchListingItem?, position: Int) {
            with(binding) {
                txtMatchName.text = matchListingItem?.match?.title

                if (matchStatus.equals(ConstantsBase.UPCOMING, true)) {
                    txtMatchStatus.visibility = View.GONE
                    txtMatchDate.visibility = View.VISIBLE
                    txtMatchDate.text = matchListingItem?.match?.startTime
                } else {
                    txtMatchStatus.visibility = View.VISIBLE
                    txtMatchDate.visibility = View.GONE
                }

                if (matchListingItem?.match?.isJoin == true) {
                    tvJoined.text = "Joined"
                } else {
                    tvJoined.text = "Join Now"
                }

                root.setOnClickListener {
                    matchListingItem?.let { it1 -> listener?.onMatchClicked(it1) }
                }

            }

        }
    }

}
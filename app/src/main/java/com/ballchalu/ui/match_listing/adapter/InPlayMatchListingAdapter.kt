package com.ballchalu.ui.match_listing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.RowInPlayMatchListingBinding
import com.ccpp.shared.domain.MatchListingItem
import com.ccpp.shared.util.ConstantsBase


class InPlayMatchListingAdapter : RecyclerView.Adapter<InPlayMatchListingAdapter.ViewHolder>() {
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

    fun setItemList(list: List<MatchListingItem>?, match_status: String
    ) {
        this.list = list
        this.matchStatus = match_status
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class ViewHolder(val binding: RowInPlayMatchListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(s: MatchListingItem?, position: Int) {
            with(binding) {
                binding.txtMatchName.text=s?.match?.title

                if(matchStatus.equals(ConstantsBase.UPCOMING,true)){
                    binding.txtMatchStatus.visibility=View.GONE
                    binding.txtMatchDate.visibility=View.VISIBLE
                    binding.txtMatchDate.text=s?.match?.startTime
                }
                else{
                    binding.txtMatchStatus.visibility=View.VISIBLE
                    binding.txtMatchDate.visibility=View.GONE
                }
            }

        }
    }

}
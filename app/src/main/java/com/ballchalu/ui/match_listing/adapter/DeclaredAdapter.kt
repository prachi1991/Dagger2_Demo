package com.ballchalu.ui.match_listing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemDeclaredListingBinding
import com.ccpp.shared.domain.MatchListing
import com.ccpp.shared.domain.MatchListingItem


class DeclaredAdapter(val listener: OnItemClickListener?) :
    RecyclerView.Adapter<DeclaredAdapter.ViewHolder>() {
    private var list: List<MatchListingItem>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDeclaredListingBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.get(position)?.match, position)
    }

    fun setItemList(list: List<MatchListingItem>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    interface OnItemClickListener {
        fun onMatchClicked(matchListing: MatchListing)
    }

    inner class ViewHolder(val binding: ItemDeclaredListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(matchListingItem: MatchListing?, position: Int) {
            with(binding) {
                model = matchListingItem
                root.setOnClickListener {
                    matchListingItem?.let { it1 -> listener?.onMatchClicked(it1) }
                }
            }
        }
    }

}
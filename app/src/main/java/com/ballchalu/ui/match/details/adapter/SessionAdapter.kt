package com.ballchalu.ui.match.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemSessionListBinding
import com.ccpp.shared.domain.match_details.SessionsItem


class SessionAdapter : RecyclerView.Adapter<SessionAdapter.ViewHolder>() {
    private var list: List<SessionsItem>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSessionListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(list?.get(position), position)
    }

    fun setItemList(sessionList: List<SessionsItem>?) {
        this.list = sessionList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    inner class ViewHolder(val binding: ItemSessionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(sessionsItem: SessionsItem?, position: Int) {
            with(binding) {
                model = SessionAdapterData(sessionsItem?.session)
            }

        }
    }

}
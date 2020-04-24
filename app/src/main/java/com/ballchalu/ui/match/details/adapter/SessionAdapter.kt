package com.ballchalu.ui.match.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemSessionListBinding
import com.ccpp.shared.domain.match_details.Session
import com.ccpp.shared.domain.match_details.SessionsItem
import com.ccpp.shared.util.ConstantsBase
import java.util.*


class SessionAdapter(val listener: OnItemClickListener?) :
    RecyclerView.Adapter<SessionAdapter.ViewHolder>() {
    private var list: ArrayList<SessionsItem>? = arrayListOf()
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
        this.list?.clear()
        sessionList?.let {
            this.list?.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun getSessionsList(): List<SessionsItem>? {
        return list
    }

    fun removeSessionItem(sessionsItem: SessionsItem) {
        this.list?.remove(sessionsItem)
        this.list?.sort()
        notifyDataSetChanged()
    }

    fun addSession(response: SessionsItem?) {
        response?.let {
            this.list?.add(it)
            this.list?.sort()
            notifyDataSetChanged()
        }
    }

    fun updateSession(sessionsItem: SessionsItem?, position: Int) {
        this.list?.get(position)?.session = sessionsItem?.session
        notifyItemChanged(position)
    }

    inner class ViewHolder(val binding: ItemSessionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(sessionsItem: SessionsItem?, position: Int) {
            with(binding) {
                model = SessionAdapterData(sessionsItem?.session)
                tvYesValue.setOnClickListener {
                    sessionsItem?.session?.let { it1 ->
                        if (it1.status?.equals(ConstantsBase.open, true) == true)
                            listener?.onYesClicked(it1)
                    }
                }
                tvNoValue.setOnClickListener {
                    sessionsItem?.session?.let { it1 ->
                        if (it1.status?.equals(ConstantsBase.open, true) == true)
                            listener?.onNoClicked(it1)
                    }
                }
            }

        }
    }

    interface OnItemClickListener {
        fun onYesClicked(session: Session)
        fun onNoClicked(session: Session)
    }

}
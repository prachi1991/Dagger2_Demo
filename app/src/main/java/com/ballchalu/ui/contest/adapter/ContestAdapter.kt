package com.ballchalu.ui.contest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemAllContestBinding
import com.ccpp.shared.domain.contest.Contest


class ContestAdapter(private var onItemClickListener : OnItemClickListener?) : RecyclerView.Adapter<ContestAdapter.ViewHolder>() {
    private var list: ArrayList<Contest>? = null
    private var isMyContest: Boolean = false

    interface OnItemClickListener {
        fun onClick(contestModel: Contest?)
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

    fun setItemList(list: ArrayList<Contest>?,status:Boolean = false) {
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
                binding.contest = contest
                if(!contest!!.isParticipated && !isMyContest)
                {
                    binding.tvPlayNow.visibility = View.VISIBLE
                    binding.tvPlayNow.text = "Buy Now"
                }
                else if(isMyContest)
                {
                    binding.tvPlayNow.visibility = View.VISIBLE
                    binding.tvPlayNow.text = "Playing Now"
                }
                else{
                    binding.tvPlayNow.visibility = View.GONE
                }
            }

            binding.tvPlayNow.setOnClickListener {

                if(!isMyContest && binding.tvPlayNow.text == "Buy Now")
                {
                    binding.tvPlayNow.visibility = View.GONE
                    onItemClickListener?.onClick(contest)

                }else{

                }

            }

        }
    }

    fun clear() {
        val size: Int = list!!.size
        list?.clear()
        notifyItemRangeRemoved(0, size)
    }

}
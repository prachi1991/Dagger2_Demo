package com.ballchalu.ui.how_to_play.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ballchalu.databinding.ItemHowToPlayLayoutBinding


class HowToPlayAdapter :
    RecyclerView.Adapter<HowToPlayAdapter.ViewHolder>() {
    // private var list: ArrayList<Contest>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHowToPlayLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //  holder.setData(list?.get(position), position)
    }

//    fun setItemList(list: ArrayList<Contest>?) {
//        this.list = list
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        return 2
    }


    inner class ViewHolder(val binding: ItemHowToPlayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        fun setData(contest: Contest?, position: Int) {
//            with(binding) {
//                binding.contest = contest
//
//            }
//
//        }
    }

//    fun clear() {
//        list?.clear()
//        notifyDataSetChanged()
//    }

}
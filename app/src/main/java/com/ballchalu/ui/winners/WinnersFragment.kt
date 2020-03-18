package com.ballchalu.ui.winners

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentWinnersBinding
import com.ballchalu.ui.winners.adapter.WinnersAdapter
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class WinnersFragment : BaseFragment() {

    private lateinit var binding: FragmentWinnersBinding

    private var winnAdapter: WinnersAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WinnersViewModel

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentWinnersBinding.inflate(inflater, container, false).apply {

            linearLayoutManager = LinearLayoutManager(context)
            rvWinners.layoutManager = linearLayoutManager
            winnAdapter = WinnersAdapter(viewModel.winnerList())
            rvWinners.adapter = winnAdapter


        }
        return binding.root
    }

}

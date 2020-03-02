package com.ballchalu.ui.match.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchDetailsBinding
import com.ballchalu.ui.match.details.adapter.SessionAdapter
import com.ccpp.shared.util.viewModelProvider
import kotlinx.android.synthetic.main.fragment_match_details.*
import javax.inject.Inject

class MatchDetailsFragment : BaseFragment() {
    private var sessionAdapter: SessionAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentMatchDetailsBinding
    private lateinit var viewModel: MatchDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentMatchDetailsBinding.inflate(inflater).apply {
            lifecycleOwner = this@MatchDetailsFragment
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
    }


    private fun initAdapter() {
        sessionAdapter = SessionAdapter()
        rvSession.adapter = sessionAdapter
    }
}
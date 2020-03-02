package com.ballchalu.ui.match.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMatchDetailsBinding
import com.ballchalu.ui.match.details.adapter.EndingDigitAdapter
import com.ballchalu.ui.match.details.adapter.EvenOddAdapter
import com.ballchalu.ui.match.details.adapter.SessionAdapter
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class MatchDetailsFragment : BaseFragment() {
    private var sessionAdapter: SessionAdapter? = null
    private var evenOddAdapter: EvenOddAdapter? = null
    private var endingDigitAdapter: EndingDigitAdapter? = null
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
        initSessionAdapterAdapter()
        initEvenOddAdapter()
        initEndingDigitAdapterAdapter()
    }


    private fun initSessionAdapterAdapter() {
        sessionAdapter = SessionAdapter()
        binding.rvSession.adapter = sessionAdapter
    }

    private fun initEndingDigitAdapterAdapter() {
        endingDigitAdapter = EndingDigitAdapter()
        binding.rvEndingDigit.adapter = endingDigitAdapter
    }

    private fun initEvenOddAdapter() {
        evenOddAdapter = EvenOddAdapter()
        binding.rvEvenOdd.adapter = evenOddAdapter
    }
}
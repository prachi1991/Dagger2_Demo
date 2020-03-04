package com.ballchalu.ui.ledgers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentBcCoinsLedgersBinding
import com.ballchalu.ui.ledgers.adapter.LedgersAdapter
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

class BcCoinsLedgersFragment : BaseFragment() {
    private lateinit var adapter: LedgersAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBcCoinsLedgersBinding
    private lateinit var viewModel: BcCoinsLedgersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentBcCoinsLedgersBinding.inflate(inflater).apply {
            lifecycleOwner = this@BcCoinsLedgersFragment
        }
        initLedgeAdapter()

        return binding.root
    }

    private fun initLedgeAdapter() {
        adapter = LedgersAdapter()
        binding.rcCoinLedgers.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
package com.ballchalu.ui.ledgers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentBcCoinsLedgersBinding
import com.ballchalu.ui.ledgers.adapter.LedgersAdapter
import com.ballchalu.utils.PaginationScrollListener
import com.ccpp.shared.core.result.EventObserver
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

        return binding.root
    }

    private fun initLedgeAdapter() {
        adapter = LedgersAdapter()
        binding.rcCoinLedgers.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rcCoinLedgers.layoutManager = layoutManager
        binding.rcCoinLedgers.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return viewModel.isLastPage
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading
            }

            override fun loadMoreItems() {
                viewModel.isLoading = true
                viewModel.page++
                //you have to call loadmore items to get more data
                viewModel.callBcCoinsLedgers()
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLedgeAdapter()
        viewModel.bcCoinsListObserver.observe(viewLifecycleOwner, EventObserver {
            adapter.setItemList(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.callBcCoinsLedgers()

    }

}
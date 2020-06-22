package com.ballchalu.ui.match.details.my_bets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMyBetsBinding
import com.ballchalu.ui.match.details.my_bets.adapter.MyBetsMatchWinnerAdapter
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

/**
 * A simple [MyBetsFragment] subclass.
 */
class MyBetsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentMyBetsBinding
    private lateinit var viewModel: MyBetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentMyBetsBinding.inflate(inflater, container, false).apply {

        }
        arguments?.let {
            viewModel.providerId = it.getInt(ConstantsBase.KEY_CONTESTS_MATCH_ID)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.betMatchWinnerObserver.observe(viewLifecycleOwner, EventObserver {
            binding.tvWinnerLabel.isGone = it.isNullOrEmpty()
            binding.rvWinnerMarker.isGone = it.isNullOrEmpty()
            val adapter = MyBetsMatchWinnerAdapter(ConstantsBase.MATCH_WINNER)
            adapter.setItemList(it)
            binding.rvWinnerMarker.adapter = adapter
        })
        viewModel.betSessionObserver.observe(viewLifecycleOwner, EventObserver {
            binding.tvSessionLabel.isGone = it.isNullOrEmpty()
            binding.rvSessionMarket.isGone = it.isNullOrEmpty()
            val adapter = MyBetsMatchWinnerAdapter(ConstantsBase.SESSION)
            adapter.setItemList(it)
            binding.rvSessionMarket.adapter = adapter
        })
        viewModel.betEvenOddObserver.observe(viewLifecycleOwner, EventObserver {
            binding.tvEvenOddLabel.isGone = it.isNullOrEmpty()
            binding.rvEvenOddMarket.isGone = it.isNullOrEmpty()
            val adapter = MyBetsMatchWinnerAdapter(ConstantsBase.EVEN_ODD)
            adapter.setItemList(it)
            binding.rvEvenOddMarket.adapter = adapter
        })
        viewModel.betEndingDigitObserver.observe(viewLifecycleOwner, EventObserver {
            binding.tvEndingDigitLabel.isGone = it.isNullOrEmpty()
            binding.rvEndingDigitMarket.isGone = it.isNullOrEmpty()
            val adapter = MyBetsMatchWinnerAdapter(ConstantsBase.ENDING_DIGIT)
            adapter.setItemList(it)
            binding.rvEndingDigitMarket.adapter = adapter
        })
        viewModel.isEmpty.observe(viewLifecycleOwner, EventObserver {
            binding.tvEmptyBets.isVisible = it
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.callMyBetsDetailsAsync()


    }


}

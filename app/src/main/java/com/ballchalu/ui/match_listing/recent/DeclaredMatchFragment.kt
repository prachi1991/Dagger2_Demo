package com.ballchalu.ui.match_listing.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentDeclaredMatchBinding
import com.ballchalu.ui.match_listing.adapter.DeclaredAdapter
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.domain.MatchListing
import com.ballchalu.shared.util.ConstantsBase
import com.ballchalu.shared.util.viewModelProvider
import javax.inject.Inject

class DeclaredMatchFragment : BaseFragment(), DeclaredAdapter.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentDeclaredMatchBinding
    private lateinit var viewModel: DeclaredMatchViewModel

    private var adapter: DeclaredAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentDeclaredMatchBinding.inflate(inflater).apply {
            lifecycleOwner = this@DeclaredMatchFragment
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.pullRefreshLayout.setOnRefreshListener {
            getMatchesListing()
        }
        getMatchesListing()
    }

    private fun getMatchesListing() {
        viewModel.callMatchListing(ConstantsBase.EVENT_TYPE, ConstantsBase.DECLARED)

        viewModel.matchListObserver.observe(viewLifecycleOwner, EventObserver {
            if (it.isNotEmpty())
                binding.llInplay.visibility = View.VISIBLE

            adapter = DeclaredAdapter(this)
            binding.rvMatchList.adapter = adapter
            adapter?.setItemList(it)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            if (!it) binding.pullRefreshLayout.isRefreshing = false
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })

    }

    override fun onMatchClicked(matchListing: MatchListing) {
        val bundle = Bundle().apply {
            putSerializable(ConstantsBase.KEY_MATCH_ITEM, matchListing)
            putBoolean(ConstantsBase.KEY_DECLARED, true)
        }
        findNavController().navigate(R.id.nav_contest, bundle)
    }
}

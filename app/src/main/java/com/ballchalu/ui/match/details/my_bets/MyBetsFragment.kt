package com.ballchalu.ui.match.details.my_bets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentMyBetsBinding
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MyBetsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentMyBetsBinding
    private lateinit var fragmentViewModel: MyBetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyBetsBinding.inflate(inflater, container, false).apply {

            fragmentViewModel.callMyBetsDetailsAsync()

            fragmentViewModel.myBetResult.observe(viewLifecycleOwner,EventObserver{

            })

        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentViewModel = viewModelProvider(viewModelFactory)
        arguments?.let {
            fragmentViewModel.matchId = it.getInt(ConstantsBase.KEY_CONTESTS_MATCH_ID)
        }
    }
}

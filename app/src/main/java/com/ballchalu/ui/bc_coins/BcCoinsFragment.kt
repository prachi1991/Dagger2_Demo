package com.ballchalu.ui.bc_coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentBcCoinsBinding
import com.ballchalu.ui.bc_coins.adapter.BcCoinAdapter
import com.ballchalu.ui.navigation.NavigationViewModel
import com.bumptech.glide.Glide
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.domain.bccoins.BcCoinContest
import com.ballchalu.shared.util.activityViewModelProvider
import com.ballchalu.shared.util.viewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

/**
 * A simple [BcCoinsFragment] subclass.
 */
class BcCoinsFragment : BaseFragment(), BcCoinAdapter.OnBcCoinListener {

    private var bcCoinAdapter: BcCoinAdapter? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var sharedPreferenceStorage: SharedPreferenceStorage

    private lateinit var binding: FragmentBcCoinsBinding
    private lateinit var viewModel: BcCoinsViewModel
    private lateinit var model: NavigationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentBcCoinsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@BcCoinsFragment
            tvEmail.text = sharedPreferenceStorage.userEmail
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSessionAdapterAdapter()
        viewModel.bcCoinListObserver.observe(viewLifecycleOwner, EventObserver {

            bcCoinAdapter?.setItemList(it)
        })

        viewModel.userDetails.observe(viewLifecycleOwner, EventObserver {
            Glide.with(requireContext())
                .load(it?.profileUrl)
                .into(binding.imgUserprofile)

            binding.tvEmail.text = it?.firstName +" "+it?.lastName
            binding.tvBcCoinBalance.text = it?.bc_coins.toString()
        })
        viewModel.bcCoinBuyObserver.observe(viewLifecycleOwner, EventObserver {
            model = activityViewModelProvider(viewModelFactory)
            model.userDetails.postValue(Event(it.bc_coins_transaction?.user))
            openLogoutDialog()
        })

        viewModel.callUserDetails()
        viewModel.callBcCoinsList()
    }

    private fun initSessionAdapterAdapter() {
        bcCoinAdapter = BcCoinAdapter(this)
        binding.rvBcCoin.adapter = bcCoinAdapter
    }

    override fun onBuyNowClicked(bcCoinContest: BcCoinContest) {
        findNavController().navigate(R.id.paymentSelectionActivity)

     //  viewModel.callBuyNow(bcCoinContest)
    }

    private fun openLogoutDialog() {
        val builder = MaterialAlertDialogBuilder(context, R.style.MyMaterialAlertDialog)
        builder.setTitle(R.string.dialog_title)
        builder.setMessage(R.string.dialog_desc)
        builder.setIcon(R.drawable.ic_success)
        builder.setPositiveButton("Ok") { _, _ ->

        }
        builder.show()

    }
}

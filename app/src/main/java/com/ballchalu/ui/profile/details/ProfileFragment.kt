package com.ballchalu.ui.profile.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentProfileBinding
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.util.viewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPreferenceStorage


    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater).apply {
            lifecycleOwner = this@ProfileFragment
            tvEmailValue.text = sharedPref.userName
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        viewModel.userDetails.observe(viewLifecycleOwner, EventObserver {
            it?.let {
                binding.llBody.isVisible = true
                binding.tvNameValue.text = "John Smith"
                binding.tvEmailValue.text = it.email.toString()
                binding.tvBcCoinValue.text = it.bc_coins.toString()
                binding.tvAvailCoinValue.text = it.available_coins.toString()
                binding.tvUserNameValue.text = it.user_name.toString()
                binding.tvCoinValue.text = it.coins.toString()
            }
        })
        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.callUserDetails()
    }


    private fun openLogoutDialog() {
        val builder = MaterialAlertDialogBuilder(context, R.style.MyMaterialAlertDialog)
        builder.setTitle("User password Changed Successfully")
        builder.setIcon(R.drawable.ic_success)
        builder.setPositiveButton("Ok") { _, _ ->
            findNavController().navigateUp()
        }
        builder.setCancelable(false)
        builder.show()

    }
}

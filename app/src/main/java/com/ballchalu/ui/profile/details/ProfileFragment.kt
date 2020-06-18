package com.ballchalu.ui.profile.details

import android.graphics.Bitmap
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
import com.ballchalu.utils.BlurBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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
        viewModel = viewModelProvider(viewModelFactory)
        binding = FragmentProfileBinding.inflate(inflater).apply {
            lifecycleOwner = this@ProfileFragment
            tvEmailValue.text = sharedPref.userName
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.userDetails.observe(viewLifecycleOwner, EventObserver {
            it?.let {
                binding.llBody.isVisible = true
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
        viewModel.loadProfile.observe(viewLifecycleOwner, EventObserver {
            val resultBmp: Bitmap = BlurBuilder.blur(requireContext(), it)
            Glide.with(requireContext())
                .load(resultBmp)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.ivProfileBlur)

            Glide.with(requireContext())
                .load(it)
                .into(binding.ivProfile)
        })
        viewModel.loadProfileImage()
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

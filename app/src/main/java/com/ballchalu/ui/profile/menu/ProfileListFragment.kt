package com.ballchalu.ui.profile.menu

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentProfileListBinding
import com.ballchalu.utils.BlurBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject


class ProfileListFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPreferenceStorage

    private lateinit var viewModel: ProfileListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileListBinding.inflate(inflater).apply {
            lifecycleOwner = this@ProfileListFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        viewModel.userDetails.observe(viewLifecycleOwner, EventObserver {

        })
        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.callUserDetails()

        binding.rlProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
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


}

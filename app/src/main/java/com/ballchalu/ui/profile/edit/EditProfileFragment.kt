package com.ballchalu.ui.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentEditProfileBinding
import com.bumptech.glide.Glide
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.util.viewModelProvider
import javax.inject.Inject


class EditProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentEditProfileBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPreferenceStorage

    private lateinit var viewModel: EditProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater).apply {
            lifecycleOwner = this@EditProfileFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        viewModel.userDetails.observe(viewLifecycleOwner, EventObserver {
            binding.tvEmailValue.text = it?.email.toString()
            Glide.with(requireContext())
                .load(it?.profileUrl)
                .into(binding.ivProfile)
        })
        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivEditProfile.setOnClickListener {
//            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePicture, 0)
        }
        viewModel.callUserDetails()


    }
}


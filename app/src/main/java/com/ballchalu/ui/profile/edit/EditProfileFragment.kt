package com.ballchalu.ui.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentEditProfileBinding
import com.bumptech.glide.Glide
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.profile.EditProfileReq
import com.ccpp.shared.util.viewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
            binding.edtEmailValue.setText(it?.email.toString())
            binding.edtFirstNameValue.setText(it?.firstName.toString())
            binding.edtLastNameValue.setText(it?.lastName.toString())
            Glide.with(requireContext())
                .load(it?.profileUrl)
                .into(binding.ivProfile)
        })
        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.isVisible = it
        })
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.editUserDetails.observe(viewLifecycleOwner, EventObserver {
            openLogoutDialog(it.message)
        })
        binding.ivEditProfile.setOnClickListener {
//            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePicture, 0)
        }
        binding.tvSave.setOnClickListener {
            if (binding.edtFirstNameValue.text.toString().isEmpty()) {
                binding.edtFirstNameValue.error = "First Name is Empty"
                return@setOnClickListener
            }
            if (binding.edtLastNameValue.text.toString().isEmpty()) {
                binding.edtLastNameValue.error = "Last Name is Empty"
                return@setOnClickListener
            }
            viewModel.saveDetails(
                EditProfileReq(
                    binding.edtFirstNameValue.text.toString(),
                    binding.edtLastNameValue.text.toString()
                )
            )
        }
        viewModel.callUserDetails()
    }

    private fun openLogoutDialog(message: String?) {
        val builder = MaterialAlertDialogBuilder(context, R.style.MyMaterialAlertDialog)
        builder.setTitle(R.string.dialog_title)
        builder.setMessage(message)
        builder.setIcon(R.drawable.ic_success)
        builder.setPositiveButton("Cancel") { dialog, _ ->
            findNavController().navigateUp()
            dialog.dismiss()
        }
        builder.show()

    }
}


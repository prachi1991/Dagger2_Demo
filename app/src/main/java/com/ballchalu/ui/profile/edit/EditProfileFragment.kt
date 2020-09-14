package com.ballchalu.ui.profile.edit

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.Drawable

import android.os.Bundle
import android.provider.MediaStore
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
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import timber.log.Timber
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
            viewModel.userData=it
            binding.edtEmailValue.setText(it?.email.toString())
            binding.edtFirstNameValue.setText(it?.firstName.toString())
            binding.edtLastNameValue.setText(it?.lastName.toString())
            binding.edtUserNameValue.setText(it?.user_name.toString())
            it?.profileUrl?.let { it1 -> loadImage(it1) }
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
            it.user?.profileUrl?.let { it1 -> loadImage(it1) }?:loadImage(R.drawable.ic_user)
            openLogoutDialog(it.message)
        })
        binding.ivEditProfile.setOnClickListener {
            checkPermission()
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
                    binding.edtLastNameValue.text.toString(),
                    binding.edtEmailValue.text.toString(),
                    binding.edtUserNameValue.text.toString()
                )
                , binding.ivProfile.drawable
            )
        }
        viewModel.callUserDetails()
    }

    private fun openLogoutDialog(message: String?) {
        val builder = MaterialAlertDialogBuilder(context, R.style.MyMaterialAlertDialog)
        builder.setTitle(R.string.dialog_title)
        builder.setMessage(message)
        builder.setIcon(R.drawable.ic_success)
        builder.setPositiveButton("Ok") { dialog, _ ->
            findNavController().navigateUp()
            dialog.dismiss()
        }
        builder.show()

    }


    private fun checkPermission() {
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            openImageSelection()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Timber.e(it.name)
            }
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK && null != data?.data) {
            data.data?.let { uri ->
                loadImage(uri)
            }
        } else if (requestCode == REQUEST_CAMERA_CODE) {
            if (resultCode == RESULT_OK) {
                data?.extras?.get("data")?.let {
                    loadImage(it)
                }
            } else {

            }
        }
    }

    private fun loadImage(path: Any) {
        Glide.with(requireContext())
            .load(path)
            .into(binding.ivProfile)
    }


    private fun openImageSelection() {

        var values = arrayOf<CharSequence>()
        if (viewModel.userData?.profileUrl==null) {
            values = arrayOf<CharSequence>(
                GALLERY_PICKER,
                CAMERA_PICKER

            )
        } else {
            values = arrayOf<CharSequence>(
                GALLERY_PICKER,
                CAMERA_PICKER, REMOVE)
        }


        val builder = MaterialAlertDialogBuilder(requireActivity(), R.style.MyMaterialAlertDialog)
        builder.setTitle("Choose Options")
        builder.setItems(values) { dialog, item ->
            if (item == 0)
                openGalleryIntent()
            else if (item == 1) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA_CODE)
            } else if (item == 2) {

                viewModel.removeProfile(
                    EditProfileReq(
                        binding.edtFirstNameValue.text.toString(),
                        binding.edtLastNameValue.text.toString(),
                        binding.edtEmailValue.text.toString(),
                        binding.edtUserNameValue.text.toString()
                    )

                )
            }
            dialog.dismiss()

        }
        builder.setNegativeButton("Ok") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    companion object {
        const val REQUEST_GALLERY_CODE: Int = 1020
        const val REQUEST_CAMERA_CODE: Int = 1030
        const val GALLERY_PICKER = "Gallery"
        const val CAMERA_PICKER = "Camera"
        const val REMOVE = "Remove Photo"
    }


    private fun openGalleryIntent() {
        val takePicture = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(takePicture, REQUEST_GALLERY_CODE)
    }

}



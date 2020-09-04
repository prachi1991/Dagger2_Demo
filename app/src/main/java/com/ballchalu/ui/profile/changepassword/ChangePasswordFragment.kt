package com.ballchalu.ui.profile.changepassword

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentChangePasswordBinding
import com.ballchalu.utils.Utils
import com.ballchalu.shared.core.result.EventObserver
import com.ballchalu.shared.database.prefs.SharedPreferenceStorage
import com.ballchalu.shared.domain.profile.ChangePasswordReq
import com.ballchalu.shared.util.viewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class ChangePasswordFragment : BaseFragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPreferenceStorage
    private var  mIsShowpass:Boolean=false

    private lateinit var viewModel: ChangePasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(inflater).apply {
            lifecycleOwner = this@ChangePasswordFragment
            tvEmailValue.text = sharedPref.userEmail
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelProvider(viewModelFactory)
        binding.btnChangePassword.setOnClickListener {
            hideSoftKeyBoard()
            validate()
        }
        viewModel.changePasswordObserver.observe(viewLifecycleOwner, EventObserver {
            openLogoutDialog()
        })
        viewModel.failure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnChangePassword.isEnabled = !it
        })
        binding.imgShowHideOldPassword.setOnClickListener {
            mIsShowpass=!mIsShowpass
            Utils.showPassword(binding.edtOldPass, binding.imgShowHideOldPassword,mIsShowpass)
        //    showOldPassword(mIsShowpass)
        }
        binding.imgShowHideNewPassword.setOnClickListener {
            mIsShowpass=!mIsShowpass
            Utils.showPassword(binding.edtNewPass, binding.imgShowHideNewPassword,mIsShowpass)
            //showNewPassword(mIsShowpass)
        }
    }

    private fun validate() {
        if (binding.edtOldPass.text.toString().isEmpty()) {
            binding.edtOldPass.error = "Old password can not be empty"
            return
        }
        if (binding.edtNewPass.text.toString().isEmpty()) {
            binding.edtNewPass.error = "New password can not be empty"
            return
        }
        if (!isPasswordValid(binding.edtNewPass.text.toString())) {
            binding.edtNewPass.error = resources.getString(R.string.invalid_password)
            return
        }

        if (binding.edtOldPass.text.toString() == binding.edtNewPass.text.toString()) {
            binding.edtNewPass.error = "Old password and new password can not be same "
            return
        }
        ChangePasswordReq(
            binding.edtNewPass.text.toString(),
            binding.edtOldPass.text.toString(),
            sharedPref.userEmail
        ).let {
            hideSoftKeyBoard()
            viewModel.callChangePasswordAsync(it)
        }

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


    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}

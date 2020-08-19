package com.ballchalu.ui.profile.menu

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BaseFragment
import com.ballchalu.databinding.FragmentProfileListBinding
import com.ballchalu.ui.login.container.LoginActivity
import com.ballchalu.utils.BlurBuilder
import com.ballchalu.utils.ThemeHelper
import com.ballchalu.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ccpp.shared.core.result.EventObserver
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.rxjava.RxBus
import com.ccpp.shared.rxjava.RxEvent
import com.ccpp.shared.util.ConstantsBase
import com.ccpp.shared.util.viewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment).navigateUp()
        }
        binding.rlProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        binding.rlChangePassword.setOnClickListener {
            findNavController().navigate(R.id.changePasswordFragment)
        }
        binding.rlBuyBcCoin.setOnClickListener {
            findNavController().navigate(R.id.bcCoinsFragment)
        }
        binding.rlBuyLegders.setOnClickListener {
            findNavController().navigate(R.id.bcCoinsLedgersFragment)
        }
        binding.rlTheme.setOnClickListener {
            openThemeSelection()
        }
        binding.rlLogout.setOnClickListener {
            openLogoutDialog()
        }

        viewModel.loadProfile.observe(viewLifecycleOwner, EventObserver { image ->
            val resultBmp: Bitmap = BlurBuilder.blur(requireContext(), image)
            Glide.with(requireContext())
                .load(resultBmp)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.ivProfileBlur)

            Glide.with(requireContext())
                .load(image)
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.ic_user))
                .into(binding.ivProfile)
            RxBus.publish(RxEvent.UpdateProfile(bitmap = image))
        })
    }

    private fun openThemeSelection() {
        val values = arrayOf<CharSequence>(
            ConstantsBase.THEME_DARK,
            ConstantsBase.THEME_LIGHT,
            ConstantsBase.THEME_DEFAULT
        )
        val builder = MaterialAlertDialogBuilder(requireActivity(), R.style.MyMaterialAlertDialog)
        builder.setTitle("Select theme")
        builder.setSingleChoiceItems(
            values, viewModel.getSelectedTheme()
        ) { dialog, item ->
            dialog.dismiss()
            sharedPref.theme = ThemeHelper.getSelectedTheme(item)
        }
        builder.show()
    }

    private fun openLogoutDialog() {
        val builder = MaterialAlertDialogBuilder(requireActivity(), R.style.MyMaterialAlertDialog)
        builder.setTitle(R.string.dialogTitle)
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            dialogInterface.dismiss()
            sharedPref.token = ""
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
        }
        builder.show()

    }
}

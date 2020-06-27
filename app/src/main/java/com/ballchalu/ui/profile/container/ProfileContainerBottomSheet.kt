package com.ballchalu.ui.profile.container

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ballchalu.R
import com.ballchalu.base.BottomSheetFragment
import com.ballchalu.databinding.BottomSheetProfileContainerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class ProfileContainerBottomSheet : BottomSheetFragment() {

    private lateinit var navHost: NavHostFragment
    private lateinit var binding: BottomSheetProfileContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetProfileContainerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        navHost = NavHostFragment()
        childFragmentManager.beginTransaction().replace(R.id.nav_host_profile_fragment, navHost)
            .commitNow()
        navHost.navController.setGraph(R.navigation.profile_navigation)

        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog: DialogInterface ->
            val dialogc = dialog as BottomSheetDialog
            val bottomSheet = dialogc.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        return bottomSheetDialog
    }

    override fun onBackEvent() {
        val up = navHost.navController.navigateUp()
        if (!up) findNavController().navigateUp()
    }
}

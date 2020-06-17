package com.ballchalu.base

import android.app.Dialog
import android.os.Bundle
import com.ballchalu.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.android.support.DaggerAppCompatDialogFragment

/** BottomSheetDialogFragment implementation that supports injection with Dagger */
open class BottomSheetFragment : DaggerAppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    //
    override fun getTheme(): Int = R.style.BottomSheet

}
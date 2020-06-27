package com.ballchalu.base

import android.app.Dialog
import android.os.Bundle
import com.ballchalu.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.android.support.DaggerAppCompatDialogFragment

abstract class BottomSheetFragment : DaggerAppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext(), theme) {
            override fun onBackPressed() {
                onBackEvent()
            }
        }
    }

    abstract fun onBackEvent()

    //
    override fun getTheme(): Int = R.style.BottomSheet

}
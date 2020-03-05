package com.ballchalu.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.*
import android.widget.TextView
import com.ballchalu.R
import javax.inject.Singleton


@Singleton
open class NotificationDialog(private val context: Context) {

    private var mDialog: Dialog = Dialog(context)
    private var view : View? = null

    open fun showMesssage(isLoading: Boolean,message: String) {
        if (isLoading) {
            show(message)
        } else {
            hide()
        }
    }

    fun setView(message: String){
        if(view != null){
            val tvMessage = view?.findViewById<TextView>(R.id.tvMessage)
            tvMessage?.text = message
        }
    }

    private fun show(message: String) {
        if (!mDialog.isShowing) {
            view = LayoutInflater.from(context).inflate(R.layout.notification_dialog_loading, null)
            setView(message)
            mDialog.window?.setGravity(Gravity.TOP)
            mDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            mDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            mDialog.setCancelable(false)
            mDialog.setContentView(view!!)
            mDialog.show()

            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) { // TODO Auto-generated method stub
                }

                override fun onFinish() { // TODO Auto-generated method stub
                    hide()
                }
            }.start()
        }
    }

    private fun hide() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
            mDialog = Dialog(context)
        }
    }
}
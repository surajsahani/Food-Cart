package com.example.foodblogs.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import com.example.foodcart.R

object ProgressUtils {

    private var progressDialog: Dialog? = null

    fun showProgressIndicator(context: Context) {
        when {
            progressDialog == null || !progressDialog!!.isShowing -> {
                progressDialog = Dialog(context)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setCanceledOnTouchOutside(false)
                progressDialog?.show()
                progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                progressDialog?.setContentView(R.layout.progress_bar)
            }
        }
    }

    fun closeProgressIndicator() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog?.dismiss()
                progressDialog?.hide()
                progressDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Loader dialog", e.message.toString())
        } finally {
            progressDialog = null
        }
    }
}
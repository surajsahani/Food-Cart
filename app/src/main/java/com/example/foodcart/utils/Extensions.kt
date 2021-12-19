package com.example.foodblogs.utils

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar

object Extensions {

    fun ImageView.setImage(url: String, isCenterCrop: Boolean = false) {
        when {
            isCenterCrop -> Glide.with(this).load(url).apply(RequestOptions().circleCrop()).into(this)
            else -> Glide.with(this).load(url).into(this)
        }
    }

    fun errorShortSnackBar(view: View, text: String) {
        val snackBar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        snackBar.setBackgroundTint(ContextCompat.getColor(view.context, android.R.color.holo_red_dark))
        snackBar.show()
    }

}
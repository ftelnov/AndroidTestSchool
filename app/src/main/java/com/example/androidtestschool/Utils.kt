package com.example.androidtestschool

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.get
import kotlinx.android.synthetic.main.primary_toast.view.*

fun Context.showPrimaryToast(message: CharSequence) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    val toastContainer = LinearLayout(this)
    val inflater: LayoutInflater =
        this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    inflater.inflate(R.layout.primary_toast, toastContainer)
    toastContainer[0].message.text = message
    toast.view = toastContainer
    toast.show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
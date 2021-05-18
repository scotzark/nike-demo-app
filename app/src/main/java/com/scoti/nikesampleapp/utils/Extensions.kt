package com.scoti.nikesampleapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View?.hideKeyboard() {
    if (this != null) {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

inline var View?.visible
    get() = (this?.visibility ?: View.GONE) == View.VISIBLE
    set(value) { this?.visibility = if (value) View.VISIBLE else View.GONE }

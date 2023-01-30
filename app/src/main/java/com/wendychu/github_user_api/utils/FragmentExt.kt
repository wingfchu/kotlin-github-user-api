package com.wendychu.github_user_api.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setTitle(title: String) {
    (requireActivity() as AppCompatActivity)?.supportActionBar?.title = title
}
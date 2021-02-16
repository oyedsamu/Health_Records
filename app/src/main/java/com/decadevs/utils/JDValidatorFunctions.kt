package com.decadevs.utils

import android.widget.EditText

// validate Unique-id

fun EditText.jdValidateUniqueID (uniqueId : String) : Boolean {
    return uniqueId.length <= 8
}

fun EditText.jdValidatePassword (uniqueId : String) : Boolean {
    return uniqueId.length <= 8
}
package com.decadevs.utils

import javax.inject.Inject


class FormValidator @Inject constructor() {

    fun checkIfEmpty(text: String): Boolean {
        if(text.isEmpty()) return true
        return  false
    }

    fun validateEmail(email: String): Boolean {
        if(checkIfEmpty(email)) return false
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if(!email.matches(emailRegex)) return false
        return true
    }
}
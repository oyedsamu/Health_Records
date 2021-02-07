package com.decadevs.utils

import javax.inject.Inject


class FormValidator @Inject constructor() {

    fun checkIfEmpty(text: String): Boolean {
        if(text.isEmpty()) return true
        return  false
    }
}
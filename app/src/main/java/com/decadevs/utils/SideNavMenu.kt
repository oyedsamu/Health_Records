package com.decadevs.utils

import android.view.Menu
import com.decadevs.healthrecords.R


fun showPatientMenuItems(menu: Menu) {
    if(roleName == "Doctor") {
        menu.findItem(R.id.vitalInfoFragment).isVisible = true
        menu.findItem(R.id.addHistoryFragment).isVisible = true
    }
    menu.findItem(R.id.historyFragment).isVisible = true
}

fun hidePatientMenuItems(menu: Menu) {
    menu.findItem(R.id.vitalInfoFragment).isVisible = false
    menu.findItem(R.id.addHistoryFragment).isVisible = false
    menu.findItem(R.id.historyFragment).isVisible = false
}

/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.model.Profile

class LogoutDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog = AlertDialog
        .Builder(requireActivity())
        .apply {
            setTitle(getString(R.string.logout))
            setMessage(getString(R.string.logout_message))
            setPositiveButton("Ok") { _, _ -> logout() }
            setNegativeButton("Cancel") { _, _ -> }
        }
        .run { create() }

    private fun logout() {
        requireActivity()
            .findViewById<DrawerLayout>(R.id.drawer_layout)
            .close()
        Profile.logout()
    }

    companion object {
        const val TAG = "LogoutDialog"
    }
}

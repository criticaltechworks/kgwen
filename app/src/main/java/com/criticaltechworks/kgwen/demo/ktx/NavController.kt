/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import androidx.annotation.IdRes
import androidx.navigation.*

/**
 * Navigates to [resId], if not already there. If [clearStack] is true, the backstack is cleared
 * before navigating.
 */
fun NavController.navigateTo(@IdRes resId: Int, clearStack: Boolean = false) {
    if (clearStack) {
        val clearStackOptions = NavOptions.Builder()
            .setPopUpTo(backQueue.last().destination.id, true)
            .build()
        navigate(resId, null, clearStackOptions)
    } else {
        if (currentDestination?.id != resId) {
            navigate(resId)
        }
    }
}

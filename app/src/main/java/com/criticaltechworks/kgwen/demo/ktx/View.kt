/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import android.view.View
import androidx.core.view.ViewCompat

/** Sets [onClick] as this [View]'s click listener and clears it on the first click to prevent. */
fun View.setOneTimeClickListener(onClick: () -> Unit) {
    setOnClickListener {
        onClick()
        setOnClickListener {  }
    }
}

/** True if [View.getLayoutDirection] is [View.LAYOUT_DIRECTION_RTL], false otherwise. */
val View.isRTL
    get() = layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL

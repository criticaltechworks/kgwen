/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import android.util.DisplayMetrics
import androidx.fragment.app.Fragment

/** Converts density-independent pixels to pixels using the provided [displayMetrics]. */
fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

/** Convenience extension that uses this [Fragment.getResources] to get the [DisplayMetrics]. */
context(Fragment)
fun Int.dpToPx(): Int = dpToPx(resources.displayMetrics)

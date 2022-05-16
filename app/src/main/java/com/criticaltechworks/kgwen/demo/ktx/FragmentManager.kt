/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import androidx.annotation.IdRes
import androidx.fragment.app.*

/** Generic version of [FragmentManager.findFragmentById] with automatic casting. */
@Suppress("UNCHECKED_CAST")
fun <T : Fragment> FragmentManager.find(@IdRes id: Int): T = findFragmentById(id) as T

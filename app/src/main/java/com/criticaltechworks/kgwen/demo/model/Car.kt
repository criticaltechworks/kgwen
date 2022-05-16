/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.model

import androidx.annotation.DrawableRes
import com.criticaltechworks.kgwen.demo.R

/** A car available for carpooling */
data class Car(val brand: Brand, val model: String, @DrawableRes val picture: Int) {
    enum class Brand {
        BMW, MINI;

        @get:DrawableRes
        val logo
            get() = if (this == BMW) R.drawable.bmw_logo else R.drawable.mini_logo
    }
}

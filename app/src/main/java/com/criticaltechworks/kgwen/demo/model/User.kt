/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.model

import androidx.annotation.DrawableRes

/** An app user */
data class User(
    val email: String,
    val name: String,
    val surname: String,
    @DrawableRes val picture: Int
) {
    val displayName get() = "$name $surname"
}

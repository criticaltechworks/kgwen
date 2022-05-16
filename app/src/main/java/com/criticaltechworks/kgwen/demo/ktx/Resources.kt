/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import android.content.res.Resources

/** Android's short animation duration. */
val Resources.shortAnimationTime
    get() = getInteger(android.R.integer.config_shortAnimTime).toLong()

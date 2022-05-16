/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.utils

import com.criticaltechworks.kgwen.verbs.Actions

/**
 * Explicitly indicates that the provided [block] is part of a teardown.
 */
fun teardown(block: Actions.() -> Unit) {
    Actions.block()
}

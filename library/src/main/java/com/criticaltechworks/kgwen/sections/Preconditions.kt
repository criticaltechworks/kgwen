/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.sections

import com.criticaltechworks.kgwen.subject.*

/**
 * Optional initial test section to perform preparatory actions.
 */
object Preconditions

/**
 * Opens a precondition [block] with preparatory actions for the current scenario.
 */
@Suppress("UNUSED")
fun <T> ArrangerRole<T>.precondition(
    block: Preconditions.() -> Unit
) where T : TestSubject, T : ArrangerRole<T> {
    Preconditions.block()
}

/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.verbs

import com.criticaltechworks.kgwen.subject.*

/** Opens a [block] for stating [Has] actions. */
infix fun <T> ArrangerRole<T>.has(
    block: Has<T>.() -> Unit
): ArrangerRole<T> where T : ArrangerRole<T>, T : Has<T> {
    @Suppress("UNCHECKED_CAST") block(this as Has<T>)
    return this
}

/** Opens a [block] for stating [On] assertions. */
infix fun <T> ArrangerRole<T>.on(
    block: On<T>.() -> Unit
): ArrangerRole<T> where T : ArrangerRole<T>, T : On<T> {
    @Suppress("UNCHECKED_CAST") block(this as On<T>)
    return this
}

/** Opens a [block] for stating [Sees] assertions. */
infix fun <T> ArrangerRole<T>.sees(
    block: Sees<T>.() -> Unit
): ArrangerRole<T> where T : ArrangerRole<T>, T : AsserterRole<T>, T : Sees<T> {
    @Suppress("UNCHECKED_CAST") block(this as Sees<T>)
    return this
}

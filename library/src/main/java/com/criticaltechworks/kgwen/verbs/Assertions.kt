/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.verbs

import com.criticaltechworks.kgwen.KGwenDsl
import com.criticaltechworks.kgwen.nodes.*
import com.criticaltechworks.kgwen.subject.*

/**
 * Global assertions object with assertions for all asserters.
 */
@KGwenDsl
object Assertions

/** Assertion indicating that something is visible. */
@KGwenDsl
interface Sees<T> : AsserterRole<T>, And, Negatable<T> where T : Sees<T>

/** Opens a [block] for stating [Sees] assertions. */
infix fun <T> AsserterRole<T>.sees(
    block: Sees<T>.() -> Unit
): AsserterRole<T> where T : Sees<T> {
    @Suppress("UNCHECKED_CAST") block(this as Sees<T>)
    return this
}

/** Assertion indicating that the [TestSubject] is currently on a certain location */
@KGwenDsl
interface On<T> : AsserterRole<T> where T : On<T>

/** Opens a [block] for stating [On] assertions. */
infix fun <T> AsserterRole<T>.on(
    block: On<T>.() -> Unit
): AsserterRole<T> where T : On<T> {
    @Suppress("UNCHECKED_CAST") block(this as On<T>)
    return this
}

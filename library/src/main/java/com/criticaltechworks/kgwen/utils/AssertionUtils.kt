/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.utils

import com.criticaltechworks.kgwen.KGwen
import com.criticaltechworks.kgwen.verbs.*

/**
 * [Conditional test logic](http://xunitpatterns.com/Conditional%20Test%20Logic.html) is often
 * undesirable. However, in certain cases, especially during initial setup, additional actions
 * may need to be taken based on runtime assertions.
 *
 * [onAsserted] returns an [AssertionResult] of type [ResultTrue] if no test related exceptions are
 * thrown in the assertion [block], and [ResultFalse] otherwise. An additional block can be run
 * is case [onAsserted] is successful (entire [block] is asserted) using
 * [AssertionResult.perform].
 *
 * Example:
 * ```
 * onAsserted {
 *     importantButtonVisible()
 * } perform {
 *     clickOn(importantButton)
 * }
 * ```
 *
 * Note: default test-related exceptions include only JUnit exceptions
 */
@Suppress("Unused", "SwallowedException")
fun onAsserted(block: Assertions.() -> Unit): AssertionResult = try {
    Assertions.block()
    ResultTrue
} catch (e: Throwable) {
    if (KGwen.contains(throwable = e)) ResultFalse else throw e
}

/**
 * Similar to [onAsserted] but returning [ResultTrue] when the assertion [block] could not be
 * fully asserted and [ResultFalse] otherwise. An additional block can be run in case
 * [onNotAsserted] is successful (at least one assertion in the [block] fails) using
 * [AssertionResult.perform]. The assertion [block] is short-circuited.
 *
 * @see onAsserted
 */
@Suppress("Unused", "SwallowedException")
fun onNotAsserted(block: Assertions.() -> Unit): AssertionResult = try {
    Assertions.block()
    ResultFalse
} catch (e: Throwable) {
    if (KGwen.contains(throwable = e)) ResultTrue else throw e
}

/**
 * The logical result of an assertion wrapping an action block to be run in case of [ResultTrue], or
 * to be skipped in case of [ResultFalse] with [AssertionResult.perform]. A cleanup action can also
 * be set independently of the assertion result using [AssertionResult.defer].
 */
@Suppress("MemberVisibilityCanBePrivate")
sealed class AssertionResult {
    /**
     * Performs the given [block] when the [AssertionResult] is [ResultTrue] and skips it if it is
     * [ResultFalse].
     */
    abstract infix fun perform(block: Actions.() -> Unit)

    /**
     * An optional [defer] block is always executed after the assertion no matter what the assertion
     * result might be. It can be useful as a way to defer any actions required immediately after
     * performing the assertion, independently of its result.
     *
     * Example:
     *
     * ```
     * // Outer scope...
     *     onAsserted {
     *        tastyChocolate()
     *     } defer {
     *        brushTeeth() // Always brush your teeth!
     *     } perform {
     *         buyMore() // Teeth are already clean when going out to buy more chocolate
     *     }
     * // The teeth are clean here, even if the chocolate was not tasty
     * ```
     */
    infix fun defer(block: Actions.() -> Unit): AssertionResult {
        Actions.block()
        return this
    }
}

/**
 * Result type for a successful assert or not assert operation.
 */
object ResultTrue : AssertionResult() {
    /** Run the [block] on success */
    override fun perform(block: Actions.() -> Unit) = Actions.block()
}

/**
 * Result type for an unsuccessful assert or not assert operation.
 */
object ResultFalse : AssertionResult() {
    /** Skip the [block] on failure */
    override fun perform(block: Actions.() -> Unit) = Unit
}

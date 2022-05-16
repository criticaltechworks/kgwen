/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.utils

import com.criticaltechworks.kgwen.sections.*
import com.criticaltechworks.kgwen.subject.*

/**
 * A test scope that provides a [TestSubject] with [ActorRole] and [AsserterRole] allowing quick
 * action -> assertion statements without the need for repeated references to the subject.
 */
interface CompoundBehaviourExpectationScope<T> where T : ActorRole<T>, T : AsserterRole<T> {
    /** The analog of [Behaviour.whenever] within a [CompoundBehaviourExpectationScope]. */
    infix fun whenever(actions: ActorRole<T>.() -> Unit): CompoundExpectation<T>

    /** The analog of [Behaviour.immediately] within a [CompoundBehaviourExpectationScope]. */
    infix fun immediately(assertions: AsserterRole<T>.() -> Unit)
}

/**
 * The analog of [Expectation] within a [CompoundBehaviourExpectationScope].
 */
fun interface CompoundExpectation<T> where T : AsserterRole<T> {
    /** The analog of [Expectation.then] within a [CompoundExpectation] scope. */
    infix fun then(assertions: AsserterRole<T>.() -> Unit)
}

/**
 * A test section that encompasses both behaviour and expectation statements
 */
object CompoundBehaviourExpectation

/**
 * Provides a scoping mechanism for test [subject]s with both [ActorRole] and [AsserterRole] so that
 * it is possible to provide quick action -> assertion statements in the [block] without the need
 * for an explicit reference to the [subject].
 *
 * Example:
 * ```
 * with(Jedi) {
 *     whenever { summons { theForce } } then { increases { power } }
 *     whenever { fights { sith } } then { gains { experience } }
 * }
 * ```
 */
fun <T> with(
    subject: T,
    block: CompoundBehaviourExpectationScope<T>.() -> Unit
): CompoundBehaviourExpectation where T : ActorRole<T>, T : AsserterRole<T> {
    val compoundExpectation = CompoundExpectation<T> { it ->
        subject.it()
    }

    val scopeInstance = object : CompoundBehaviourExpectationScope<T> {
        override fun whenever(actions: ActorRole<T>.() -> Unit): CompoundExpectation<T> {
            subject.actions()
            return compoundExpectation
        }

        override fun immediately(assertions: AsserterRole<T>.() -> Unit) {
            subject.assertions()
        }
    }

    scopeInstance.block()
    return CompoundBehaviourExpectation
}

/**
 * A [TestSubject] with both [ActorRole] and [AsserterRole].
 */
class CompoundActorAsserter<T>(private val subject: T) where T : ActorRole<T>, T : AsserterRole<T> {
    /** Opens a test scope for abbreviated action -> assertion statements. */
    infix fun check(block: CompoundBehaviourExpectationScope<T>.() -> Unit) = with(subject, block)
}

/**
 * Scoped version of [Scenario.given] returning a [CompoundActorAsserter] that can be used for
 * quick action -> assertion statements using [CompoundActorAsserter.check].
 */
fun <T> given(
    subject: T,
    block: ArrangerRole<T>.() -> Unit
): CompoundActorAsserter<T>
where T : ArrangerRole<T>, T : ActorRole<T>, T : AsserterRole<T> {
    subject.block()
    return CompoundActorAsserter(subject)
}

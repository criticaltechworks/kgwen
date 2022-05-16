/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.sections

import com.criticaltechworks.kgwen.subject.*
import com.criticaltechworks.kgwen.utils.*

/**
 * The entry point for a complete system behaviour specification by
 * [example](https://martinfowler.com/bliki/SpecificationByExample.html).
 */
object Scenario {
    /** Identifies the state of the universe in the [block] before specifying the test behaviour. */
    fun given(block: ArrangerScope.() -> Unit): Behaviour {
        Arranger.block()
        return Behaviour
    }
}

/**
 * The test section to state the behaviour specification.
 */
object Behaviour {
    /** States the specified behaviour for this scenario in the [block]. */
    infix fun whenever(block: ActorScope.() -> Unit): Expectation {
        Actor.block()
        return Expectation
    }

    /** States that no further action is required (i.e. the given scenario is enough) */
    infix fun immediately(then: Unit) = then

    /**
     * Allows opening a [CompoundBehaviourExpectation] test section using a scoped test subject
     * using [with].
     */
    infix fun check(compoundBehaviourExpectation: CompoundBehaviourExpectation) =
        compoundBehaviourExpectation
}

/**
 * The test section to state the expected changes.
 */
object Expectation {
    /** States the expected changes in the [block] caused by the previously specified [Behaviour]. */
    infix fun then(block: AsserterScope.() -> Unit) = Asserter.block()
}

/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.subject

/**
 * Global [TestSubject] with [ArrangerRole] and which defines the [ArrangerScope].
 */
object Arranger : ArrangerRole<Arranger>

/**
 * The scope in which concrete arrangers are made available.
 *
 * Example - Implementing a concrete Arranger:
 * ```
 * val ArrangerScope.anakin: ArrangerRole<Anakin>
 *     get() = Anakin // Anakin object must conform to ArrangerRole
 * ```
 */
typealias ArrangerScope = ArrangerRole<Arranger>

/**
 * Global [TestSubject] with [ActorRole] and which defines the [ActorScope].
 */
object Actor : ActorRole<Actor>

/**
 * The scope in which custom actors are made available.
 *
 * Example - Implementing a concrete Actor:
 * ```
 * val ActorScope.skywalker: ActorRole<Skywalker>
 *     get() = Skywalker // Note: Skywalker object must conform to ActorRole
 * ```
 */
typealias ActorScope = ActorRole<Actor>

/**
 * Global [TestSubject] with [AsserterRole] and which defines the [AsserterScope].
 */
object Asserter : AsserterRole<Asserter>

/**
 * The scope in which custom asserters are made available.
 *
 * Example - Implementing a concrete Asserter:
 * ```
 * val AsserterScope.yoda: AsserterRole<Yoda>
 *     get() = Yoda // Note: Yoda object has fine eyes and thus conforms to AsserterRole
 * ```
 */
typealias AsserterScope = AsserterRole<Asserter>

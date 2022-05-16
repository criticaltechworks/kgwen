/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.annotation

/**
 * An annotation to declare KGwen verbs. Example:
 * ```
 * @KGwenSubject
 * @KGwenVerb(inArrangerScope = true)
 * interface Has<T> : ActorRole<T> where T : Has<T>        // Action verb
 *
 * @KGwenVerb(inArrangerScope = true)
 * interface Sees<T> : AsserterRole<T> where T : Sees<T>  // Assertion verb
 * ```
 * It is applicable to interfaces which must conform to either `ActorRole` (for action verbs) or
 * `AsserterRole` (for assertion verbs).
 *
 * @param inArrangerScope Flag that, when set, will make the declared verb also available in a
 * `given` block.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class KGwenVerb(val inArrangerScope: Boolean = false)

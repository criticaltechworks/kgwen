/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.annotation

/**
 * An annotation to declare KGwen custom keywords that can be used as a `NodeOperation` for object
 * chaining. Example:
 * ```
 * @KGwenKeyword
 * interface Then : NodeOperation
 * ```
 * Now it can be associated with verbs-specific objects:
 * ```
 * @KGwenVerb
 * interface Selects<T> : Then, ActorRole<T> where T : Then<T>
 * ```
 * And used like:
 * ```
 * subject selects { optionA then optionB then optionC }
 * ```
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class KGwenKeyword

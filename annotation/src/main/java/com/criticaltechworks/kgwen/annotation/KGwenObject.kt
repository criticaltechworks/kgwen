/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.annotation

import kotlin.reflect.KClass

/**
 * An annotation to declare KGwen objects that represent test preconditions, actions and assertions.
 * It is applicable to parameterless functions which may however have context receivers. Example:
 * ```
 * @KGwenObject(name = "email", subject = User::class, verb = Types::class)
 * fun Actions.typeEmail() { /* typing logic here */ } // Actions: object used as namespace
 * ```
 * It is now possible to call the e-mail typing logic like so:
 * ```
 * subject types { email }
 * ```
 * @param name The object name which will be used to reference the annotated function inside the
 * block opened by the associated [verb].
 * @param subject The test subject this object is available to
 * @param verb The verb this object will be associated with
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
@Repeatable
@java.lang.annotation.Repeatable(KGwenObjects::class)
annotation class KGwenObject(
    val name: String,
    val subject: KClass<*>,
    val verb: KClass<*>,
    val allowsNodeOperations: Boolean = true
)

@Target(AnnotationTarget.FUNCTION)
annotation class KGwenObjects(val value: Array<KGwenObject>)

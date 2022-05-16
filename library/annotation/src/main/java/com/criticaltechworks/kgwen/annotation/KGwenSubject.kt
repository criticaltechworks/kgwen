/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.annotation

/**
 * An annotation to declare KGwen test subjects. Example:
 * ```
 * @KGwenSubject
 * object User :
 *     ArrangerRole<User>,         // Arranger of test preconditions
 *     Selects<User>, Types<User>, // Action verbs, i.e. ActorRole
 *     Sees<User>, On<User>        // Assertion verbs, i.e. AsserterRole
 * ```
 * It is applicable to objects which conform to at least one test subject role:
 * - `ArrangerRole`: for arranging test preconditions. This role must be explicitly conformed to
 * when declaring a new test subject - the subject will be available in a `given` block
 * - `ActorRole`: for stating behaviour (actions). This role is provided by action verbs like
 * `Selects` - the subject will be available in a `whenever` block
 * - `AsserterRole`: to check expected changes (assertions). This role is provided by assertion
 * verbs like `Sees` - the subject will be available in a `then` block
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class KGwenSubject

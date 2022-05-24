/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.subject

import com.criticaltechworks.kgwen.*

/**.
 * The role of a [TestSubject] responsible for scenario arrangement
 */
@KGwenDsl
interface ArrangerRole<T>: TestSubject where T : ArrangerRole<T>

/**
 * The role of a [TestSubject] responsible for taking the actions prescribed by the behaviour spec.
 */
@KGwenDsl
interface ActorRole<T>: TestSubject where T : ActorRole<T>

/**
 * The role of a [TestSubject] responsible for checking the result of the previously taken actions.
 */
@KGwenDsl
interface AsserterRole<T>: TestSubject where T : AsserterRole<T>

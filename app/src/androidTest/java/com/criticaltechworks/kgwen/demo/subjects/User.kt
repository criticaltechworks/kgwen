/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.subjects

import com.criticaltechworks.kgwen.annotation.KGwenSubject
import com.criticaltechworks.kgwen.demo.verbs.*
import com.criticaltechworks.kgwen.subject.*
import com.criticaltechworks.kgwen.verbs.*

/**
 * Supported user action verbs.
 */
interface UserActions : Opens<User>, Selects<User>, Types<User>, Sends<User>, Has<User>

/**
 * Supported user assertion verbs.
 */
interface UserAssertions : On<User>, Sees<User>

/**
 * A [TestSubject] which represents a user interacting with the app. Includes:
 *  - [ArrangerRole]: for user-related scenarios
 *  - [ActorRole]: for user interactions
 *  - [AsserterRole]: for user assertions
 */
@KGwenSubject
object User : ArrangerRole<User>, UserActions, UserAssertions

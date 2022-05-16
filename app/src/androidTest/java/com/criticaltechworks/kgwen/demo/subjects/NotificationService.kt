/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.subjects

import com.criticaltechworks.kgwen.annotation.KGwenSubject
import com.criticaltechworks.kgwen.demo.verbs.Receives
import com.criticaltechworks.kgwen.subject.*
import com.criticaltechworks.kgwen.verbs.Has

/**
 * A [TestSubject] which represents a notification service.
 */
@KGwenSubject
object NotificationService :
    ArrangerRole<NotificationService>,
    Receives<NotificationService>, Has<NotificationService>

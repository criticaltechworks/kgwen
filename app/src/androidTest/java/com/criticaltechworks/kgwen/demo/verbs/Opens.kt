/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.verbs

import com.criticaltechworks.kgwen.KGwenDsl
import com.criticaltechworks.kgwen.annotation.KGwenVerb
import com.criticaltechworks.kgwen.subject.*

/**
 * Action indicating something was opened.
 */
@KGwenDsl
@KGwenVerb
interface Opens<T> : ActorRole<T> where T : Opens<T>

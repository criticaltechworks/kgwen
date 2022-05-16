/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.verbs

import com.criticaltechworks.kgwen.KGwenDsl
import com.criticaltechworks.kgwen.annotation.KGwenVerb
import com.criticaltechworks.kgwen.subject.*
import com.criticaltechworks.kgwen.nodes.And

/**
 * Action indicating some text was input.
 */
@KGwenDsl
@KGwenVerb
interface Types<T> : ActorRole<T>, And where T : Types<T>

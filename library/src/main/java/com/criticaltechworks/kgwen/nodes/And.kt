/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.nodes

/**
 * Interface providing chaining via the [and] "keyword".
 */
interface And : NodeOperation {
    /** Chain two nodes using `and` keyword. */
    infix fun KGwenNode.and(other: KGwenNode) = other
}

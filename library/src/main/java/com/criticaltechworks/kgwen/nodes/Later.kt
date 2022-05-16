/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.nodes

/**
 * Interface providing chaining via the [later] "keyword".
 */
interface Later : NodeOperation {
    /** Chain two nodes using `later` keyword. */
    infix fun KGwenNode.later(other: KGwenNode) = other
}

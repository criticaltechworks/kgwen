/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.nodes

import com.criticaltechworks.kgwen.subject.AsserterRole
import junit.framework.AssertionFailedError

// TODO: update doc
/**
 * Node transformation that creates an assertion which is the logical negation of another.
 */
interface Negatable<T> : NodeOperation where T : AsserterRole<T>, T : Negatable<T> {
    /**
     * Creates an assertion that is the logical negation of the [Negatable] assertion returned in
     * the [block]. Only test related exceptions are checked and, if the [block] was successfully
     * asserted, then an [AssertionFailedError] is thrown using the label provided with [node] when
     * declaring that assertion node.
     */
    fun no(block: () -> KGwenNode): KGwenNode {
        try {
            val node = block()
            throw AssertionFailedError("${node.label} was asserted!")
        } catch (e: KGwenError) {
            return KGwenNode(label = "no { ${e.node.label} }")
        }
    }
}

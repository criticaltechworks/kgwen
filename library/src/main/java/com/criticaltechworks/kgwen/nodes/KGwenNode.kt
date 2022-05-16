/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.nodes

import com.criticaltechworks.kgwen.KGwen

/**
 * A KGwen node that supports chaining and transformations.
 */
data class KGwenNode internal constructor(internal val label: String)

interface NodeOperation {
    /**
     * Create a [KGwenNode] for the executable [block].
     * @param label A custom identifier for this [KGwenNode], otherwise the node will be labeled with
     * the name of the KGwen object it is called from.
     * @param block The code for this [KGwenNode].
     */
    fun node(label: String = callerName, block: () -> Unit): KGwenNode {
        val node = KGwenNode(label = label.removePrefix(GETTER_PREFIX))
        try {
            block()
        } catch (e: Throwable) {
            throw if (KGwen.contains(throwable = e)) KGwenError(node = node, throwable = e) else e
        }
        return node
    }

    private companion object {
        private val callerName: String
            get() = Thread.currentThread().stackTrace[CALLER_INDEX].methodName

        private const val CALLER_INDEX = 5
        private const val GETTER_PREFIX = "get"
    }
}

class KGwenError internal constructor(internal val node: KGwenNode, val throwable: Throwable) :
    Throwable(throwable.localizedMessage)

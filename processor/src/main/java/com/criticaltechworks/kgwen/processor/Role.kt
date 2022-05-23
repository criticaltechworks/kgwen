package com.criticaltechworks.kgwen.processor

import javax.lang.model.element.Element

/**
 * Helper class for codegen related to subject roles
 */
internal enum class Role {
    ARRANGER, ACTOR, ASSERTER;

    /** The test section scope associated with each role */
    val scope get() = when(this) {
        ARRANGER -> "ArrangerScope"
        ACTOR -> "ActorScope"
        ASSERTER -> "AsserterScope"
    }

    override fun toString() = when(this) {
        ARRANGER -> "ArrangerRole"
        ACTOR -> "ActorRole"
        ASSERTER -> "AsserterRole"
    }

    companion object {
        // TODO improve checks
        /**
         * @return A [Role] instance created from an [Element] or null if [element] is not actually
         * a role.
         */
        fun from(element: Element) = when (element.simpleName.toString()) {
            ARRANGER.toString() -> ARRANGER
            ACTOR.toString() -> ACTOR
            ASSERTER.toString() -> ASSERTER
            else -> null
        }
    }
}

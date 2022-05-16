package com.criticaltechworks.kgwen

import com.criticaltechworks.kgwen.KGwen.configuration
import junit.framework.AssertionFailedError
import kotlin.reflect.KClass

/**
 * Provides a simple API to configure KGwen settings.
 */
interface ConfigScope {
    /**
     * Add a new [Throwable] as a test-related exception. Default throwables are:
     * [AssertionFailedError] and [AssertionError].
     */
    fun <T : Throwable> throwable(init: () -> KClass<T>)
}

/**
 * KGwen settings and configuration object. You can customize it using [configuration].
 *
 * Example usage:
 * ```
 * KGwen configuration {
 *     // your configuration goes here
 * }
 * ```
 */
object KGwen {
    /** The types of throwables KGwen considers test-framework assertion errors */
    internal var throwables: MutableSet<KClass<*>> = mutableSetOf()

    init {
        KGwen configuration {
            throwable { AssertionError::class }       // Thrown by junit.framework
            throwable { AssertionFailedError::class } // Thrown by org.junit
        }
    }

    /** Open a new KGwen configuration block */
    infix fun configuration(config: ConfigScope.() -> Unit) {
        ConfigScopeImpl.config()
    }

    /** Check if [throwables] contain the type or a base of the provided [throwable]. */
    internal fun <T : Throwable> contains(throwable: T) =
        throwables.any { throwable::class == it || it.isInstance(throwable) }

    private object ConfigScopeImpl : ConfigScope {
        override fun <T : Throwable> throwable(init: () -> KClass<T>) {
            throwables.add(init())
        }
    }
}

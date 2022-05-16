/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.verbs

import com.criticaltechworks.kgwen.KGwenDsl
import com.criticaltechworks.kgwen.nodes.*
import com.criticaltechworks.kgwen.subject.ActorRole

/**
 * Global actions object with actions for all actors
 */
@KGwenDsl
object Actions

/**
 * Action indicating something has already been done.
 */
@KGwenDsl
interface Has<T> : ActorRole<T>, And where T : Has<T>

/** Opens a [block] for stating [Has] actions. */
infix fun <T> ActorRole<T>.has(
    block: Has<T>.() -> Unit
): ActorRole<T> where T : Has<T> {
    @Suppress("UNCHECKED_CAST") block(this as Has<T>)
    return this
}

/**
 * Action indicating something is selected.
 */
@KGwenDsl
interface Selects<T> : ActorRole<T>, Later where T : Selects<T>

/** Opens a block for stating [Selects] actions. */
infix fun <T> ActorRole<T>.selects(
    block: Selects<T>.() -> Unit
): ActorRole<T> where  T : Selects<T> {
    @Suppress("UNCHECKED_CAST") block(this as Selects<T>)
    return this
}

/**
 * Action indicating something is pressed.
 */
@KGwenDsl
interface Presses<T> : ActorRole<T> where T : Presses<T>

/** Opens a block for stating [Presses] actions. */
infix fun <T> ActorRole<T>.presses(
    block: Presses<T>.() -> Unit
): ActorRole<T> where T : Presses<T> {
    @Suppress("UNCHECKED_CAST") block(this as Presses<T>)
    return this
}

/**
 * Action indicating something is long pressed.
 */
@KGwenDsl
interface LongPresses<T> : ActorRole<T> where T : LongPresses<T>

/** Opens a block for stating [LongPresses] actions. */
infix fun <T> ActorRole<T>.longpresses(
    block: LongPresses<T>.() -> Unit
): ActorRole<T> where T : LongPresses<T> {
    @Suppress("UNCHECKED_CAST") block(this as LongPresses<T>)
    return this
}

/**
 * Action indicating something is awaited for.
 */
@KGwenDsl
interface Waits<T> : ActorRole<T> where T : Waits<T>

/** Opens a [block] for stating [Waits] actions. */
infix fun <T> ActorRole<T>.waits(
    block: Waits<T>.() -> Unit
): ActorRole<T> where T : Waits<T> {
    @Suppress("UNCHECKED_CAST") block(this as Waits<T>)
    return this
}

/**
 * Action indicating some process was initiated.
 */
@KGwenDsl
interface Starts<T> : ActorRole<T> where T : Starts<T>

/** Opens a [block] for stating [Starts] actions. */
infix fun <T> ActorRole<T>.starts(
    block: Starts<T>.() -> Unit
): ActorRole<T> where T : Starts<T> {
    @Suppress("UNCHECKED_CAST") block(this as Starts<T>)
    return this
}

/**
 * Action indicating some process was completed.
 */
@KGwenDsl
interface Stops<T> : ActorRole<T> where T : Stops<T>

/** Opens a [block] for stating [Starts] actions. */
infix fun <T> ActorRole<T>.stops(
    block: Stops<T>.() -> Unit
): ActorRole<T> where T : Stops<T> {
    @Suppress("UNCHECKED_CAST") block(this as Stops<T>)
    return this
}

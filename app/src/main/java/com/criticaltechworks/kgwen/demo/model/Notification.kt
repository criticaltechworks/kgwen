/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.model

import androidx.annotation.DrawableRes
import com.criticaltechworks.kgwen.demo.R
import java.util.*

/** An app notification */
sealed class Notification(
    val id: String = UUID.randomUUID().toString(),
    open val title: String,
    val content: String,
    @DrawableRes val icon: Int,
    open val read: Boolean
) {
    fun copyAs(read: Boolean) = when(this) {
        is Call -> copy(read = read)
        is Message -> copy(read = read)
        is Event -> copy(read = read)
    }

    /** Missed call notification */
    data class Call(val user: User, override val read: Boolean = false) : Notification(
        title = user.displayName,
        content = "Missed call from ${user.displayName}",
        icon = R.drawable.ic_missed_call,
        read = read
    )

    /** Received message notification */
    data class Message(val user: User, val message: String, override val read: Boolean = false) :
        Notification(
            title = user.displayName,
            content = message,
            icon = R.drawable.ic_message,
            read = read
        )

    /** Upcoming event notification */
    data class Event(
        override val title: String,
        val description: String,
        override val read: Boolean = false
    ) :
        Notification(title = title, content = description, icon = R.drawable.ic_event, read = read)

    companion object {
        const val CHANNEL_ID = "KGwenNotificationChannel"
        const val CHANNEL_DESCRIPTION = "Notification channel for KGwen Demmo App"
    }
}

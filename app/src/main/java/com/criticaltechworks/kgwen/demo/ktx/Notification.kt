/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import android.content.Context
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.model.Notification

val Notification.iconDescription
    get() = when(this) {
        is Notification.Call -> "Missed call"
        is Notification.Message -> "New message"
        is Notification.Event -> "New event"
    }

fun Notification.markButtonDescription(context: Context) =
    context.getString(if (read) R.string.mark_unread else R.string.mark_read)

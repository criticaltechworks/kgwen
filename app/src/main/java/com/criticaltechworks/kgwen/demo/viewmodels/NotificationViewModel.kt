/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.viewmodels

import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.lifecycle.*
import com.criticaltechworks.kgwen.demo.ktx.*
import com.criticaltechworks.kgwen.demo.model.*
import kotlin.random.Random

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableLiveData<List<Notification>>(emptyList())
    val notifications = _notifications.asLiveData()

    fun send(notification: Notification, context: Context) {
        _notifications.postValue(listOf(notification) + _notifications.value!!)
        context.notificationManager.notify(
            Random.nextInt(),
            notification.toSystemNotification(context)
        )
    }

    fun removeNotificationAt(index: Int) {
        _notifications.postValue(
            notifications.value!!
                .toMutableList()
                .apply { removeAt(index) }
        )
    }

    fun markAt(index: Int, read: Boolean) {
        _notifications.postValue(
            notifications.value!!
                .toMutableList()
                .apply { set(index, get(index).copyAs(read = read)) }
        )
    }

    private fun Notification.toSystemNotification(context: Context) = NotificationCompat
        .Builder(context, Notification.CHANNEL_ID)
        .apply {
            setSmallIcon(icon)
            setContentTitle(title)
            setContentText(content)
            setCategory(category)
            largeIcon?.let { setLargeIcon(BitmapFactory.decodeResource(context.resources, it)) }
            style?.let { setStyle(it) }
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
        .run { build() }

    private val Notification.largeIcon
        get() = when (this) {
            is Notification.Call -> user.picture
            is Notification.Message -> user.picture
            is Notification.Event -> null
        }

    private val Notification.style
        get() = when (this) {
            is Notification.Message -> NotificationCompat.BigTextStyle()
            else -> null
        }

    private val Notification.category
        get() = when (this) {
            is Notification.Call -> NotificationCompat.CATEGORY_CALL
            is Notification.Message -> NotificationCompat.CATEGORY_MESSAGE
            is Notification.Event -> NotificationCompat.CATEGORY_EVENT
        }
}

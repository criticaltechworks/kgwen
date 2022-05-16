/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo

import android.app.*
import com.criticaltechworks.kgwen.demo.ktx.notificationManager
import com.criticaltechworks.kgwen.demo.model.Notification

class KGwenDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        NotificationChannel(
            Notification.CHANNEL_ID,
            Notification.CHANNEL_DESCRIPTION,
            NotificationManager.IMPORTANCE_DEFAULT
        ).also { notificationManager.createNotificationChannel(it) }
    }
}

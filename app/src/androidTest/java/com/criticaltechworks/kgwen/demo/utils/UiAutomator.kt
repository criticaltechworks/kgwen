/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.utils

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*

object UiAutomator {
    /** Device, the instrumentation is running on */
    val device: UiDevice = UiDevice.getInstance(instrumentation)

    private val instrumentation
        get() = InstrumentationRegistry.getInstrumentation()

    /** Default project timeout for UiAutomator-related waits. */
    const val TIMEOUT = 2_500L

    fun clearNotifications() {
        val notificationPane = UiSelector()
            .resourceId("com.android.systemui:id/notification_stack_scroller")
            .run { UiScrollable(this) }
        notificationPane.scrollTextIntoView(SystemStrings.CLEAR_ALL_NOTIFICATIONS)

        val clearButton = UiSelector().text(SystemStrings.CLEAR_ALL_NOTIFICATIONS)
        device.findObject(clearButton).click()
    }

    private object SystemStrings {
        const val CLEAR_ALL_NOTIFICATIONS = "Clear all"
    }
}

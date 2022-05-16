/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("IllegalIdentifier")

package com.criticaltechworks.kgwen.demo.tests.notifications

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.criticaltechworks.kgwen.demo.core.UiTest
import com.criticaltechworks.kgwen.demo.subjects.*
import com.criticaltechworks.kgwen.demo.tests.drawer.notifications
import com.criticaltechworks.kgwen.demo.tests.goToNotifications
import com.criticaltechworks.kgwen.demo.verbs.*
import com.criticaltechworks.kgwen.sections.Behaviour.whenever
import com.criticaltechworks.kgwen.sections.Preconditions
import com.criticaltechworks.kgwen.sections.Scenario.given
import com.criticaltechworks.kgwen.utils.*
import com.criticaltechworks.kgwen.verbs.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotificationsTest : UiTest() {

    @Before
    fun setup() {
        Preconditions.goToNotifications()
    }

    @Test
    fun `Notifications show all notification types`() {
        given {
            user on { notifications }
        } whenever {
            notificationService receives { missedCall }
        } then {
            user sees { missedCallNotification }
        }

        whenever {
            notificationService receives { newMessage }
        } then {
            user sees { messageNotification }
        }

        whenever {
            notificationService receives { newEvent }
        } then {
            user sees { eventNotification }
        }
    }

    @Test
    fun `Mark as button toggles notification read status`() {
        given {
            user on { notifications }
            notificationService has { notification }
        } check with(User) {
            immediately { sees { notificationBadge and markAsReadIcon } }
            whenever { selects { markAs } } then { sees { no { notificationBadge } and markAsUnreadIcon } }
            whenever { selects { markAs } } then { sees { notificationBadge and markAsReadIcon } }
        }
    }

    @Test
    fun `Delete button clears notification`() {
        given {
            user on { notifications }
            notificationService has { notification }
        } check with(User) {
            immediately { sees { deleteNotificationButton } }
            whenever { selects { deleteNotification } } then { sees { no { notification } } }
        }
    }

    @Test
    fun `New notifications appear on notification drawer`() {
        try {
            given {
                notificationService has { missedCall and newMessage and newEvent }
            } whenever {
                user opens { notificationDrawer }
            } then {
                user sees { missedCallOnDrawer and newMessageOnDrawer and newEventOnDrawer }
            }
        } finally {
            teardown { clearNotifications() }
        }
    }
}

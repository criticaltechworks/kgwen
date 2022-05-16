/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.notifications

import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItemChild
import com.criticaltechworks.kgwen.annotation.KGwenObject
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.core.MainActivityScenarioTest
import com.criticaltechworks.kgwen.demo.model.*
import com.criticaltechworks.kgwen.demo.subjects.*
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.demo.utils.*
import com.criticaltechworks.kgwen.demo.utils.TestUtils.flakyAction
import com.criticaltechworks.kgwen.demo.verbs.*
import com.criticaltechworks.kgwen.verbs.*

context(MainActivityScenarioTest)
        inline fun <reified T : Notification> Actions.receive() {
    val notification = SampleData.notifications().filterIsInstance<T>().first()
    onActivity { it.send(notification) }
}

@KGwenObject(name = "markAs", subject = User::class, verb = Selects::class)
fun Actions.selectMarkAsRead() {
    flakyAction(sleep = TestUtils.Delay.MARK_AS) {
        clickListItemChild(id = R.id.notification_list, position = 0, childId = R.id.mark_button)
    }
}

context(MainActivityScenarioTest)
@KGwenObject(name = "missedCall", subject = NotificationService::class, verb = Receives::class)
@KGwenObject(name = "missedCall", subject = NotificationService::class, verb = Has::class)
@KGwenObject(name = "notification", subject = NotificationService::class, verb = Has::class)
fun Actions.receiveMissedCallNotification() {
    Actions.receive<Notification.Call>()
}

context(MainActivityScenarioTest)
@KGwenObject(name = "newMessage", subject = NotificationService::class, verb = Receives::class)
@KGwenObject(name = "newMessage", subject = NotificationService::class, verb = Has::class)
fun Actions.receiveNewMessageNotification() {
    Actions.receive<Notification.Message>()
}

context(MainActivityScenarioTest)
@KGwenObject(name = "newEvent", subject = NotificationService::class, verb = Receives::class)
@KGwenObject(name = "newEvent", subject = NotificationService::class, verb = Has::class)
fun Actions.receiveNewEventNotification() {
    Actions.receive<Notification.Event>()
}

@KGwenObject(name = "deleteNotification", subject = User::class, verb = Selects::class)
fun Actions.deleteNotification() {
    flakyAction(sleep = TestUtils.Delay.DELETE_NOTIFICATION) {
        clickListItemChild(
            id = R.id.notification_list,
            position = 0,
            childId = R.id.delete_notification_button
        )
    }
}

@KGwenObject(name = "notificationDrawer", subject = User::class, verb = Opens::class)
fun Actions.openNotificationDrawer() {
    UiAutomator.device.openNotification()
}

fun Actions.clearNotifications() {
    flakyAction(sleep = TestUtils.Delay.CLEAR_NOTIFICATION_DRAWER) {
        UiAutomator.clearNotifications()
    }
}

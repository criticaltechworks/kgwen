/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.notifications

import androidx.annotation.IdRes
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.uiautomator.*
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertCustomAssertionAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.criticaltechworks.kgwen.annotation.KGwenObject
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.ktx.iconDescription
import com.criticaltechworks.kgwen.demo.model.*
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.demo.utils.TestUtils.stringFrom
import com.criticaltechworks.kgwen.demo.utils.UiAutomator
import com.criticaltechworks.kgwen.verbs.*
import org.hamcrest.Matchers.allOf
import org.junit.Assert.assertNotNull

private fun assertNotificationTitleDisplayedAt(position: Int, text: String) {
    assertDisplayedAtPosition(
        listId = R.id.notification_list,
        position = position,
        targetViewId = R.id.notification_title,
        text = text
    )
}

private fun assertNotificationContentDisplayedAt(position: Int, text: String) {
    assertDisplayedAtPosition(
        listId = R.id.notification_list,
        position = position,
        targetViewId = R.id.notification_content,
        text = text
    )
}

private fun assertNotificationListDisplayedAt(position: Int, @IdRes viewId: Int) {
    assertCustomAssertionAtPosition(
        listId = R.id.notification_list,
        position = position,
        targetViewId = viewId,
        viewAssertion = ViewAssertions.matches(isDisplayed())
    )
}

private fun assertNotificationBadgeDisplayedAt(position: Int) {
    assertNotificationListDisplayedAt(position = position, viewId = R.id.notification_badge)
}

private fun assertDeleteIconDisplayedAt(position: Int) {
    assertNotificationListDisplayedAt(position = position, viewId = R.id.delete_notification_button)
}

private fun assertNotificationListDisplayedChildAt(position: Int, contentDescription: String) {
    assertCustomAssertionAtPosition(
        listId = R.id.notification_list,
        position = position,
        targetViewId = R.id.notification_item,
        viewAssertion = ViewAssertions.matches(
            hasDescendant(
                allOf(
                    withContentDescription(contentDescription),
                    isDisplayed()
                )
            )
        )
    )
}

private inline fun <reified T : Notification> assertNotificationVisible() {
    val notification = SampleData.notifications().filterIsInstance<T>().first()

    assertNotificationTitleDisplayedAt(0, text = notification.title)
    assertNotificationContentDisplayedAt(0, text = notification.content)
    assertNotificationListDisplayedChildAt(0, contentDescription = notification.iconDescription)
    assertDeleteIconDisplayedAt(0)
}

@KGwenObject(name = "notificationBadge", subject = User::class, verb = Sees::class)
fun Assertions.notificationBadgeVisible() {
    assertNotificationBadgeDisplayedAt(0)
}

@KGwenObject(name = "markAsReadIcon", subject = User::class, verb = Sees::class)
fun Assertions.markAsReadIconVisible() {
    assertNotificationListDisplayedChildAt(
        position = 0,
        contentDescription = stringFrom(R.string.mark_read)
    )
}

@KGwenObject(name = "markAsUnreadIcon", subject = User::class, verb = Sees::class)
fun Assertions.markAsUnreadIconVisible() {
    assertNotificationListDisplayedChildAt(
        position = 0,
        contentDescription = stringFrom(R.string.mark_unread)
    )
}

@KGwenObject(name = "deleteNotificationButton", subject = User::class, verb = Sees::class)
fun Assertions.deleteNotificationButton() {
    assertNotificationListDisplayedAt(position = 0, viewId = R.id.delete_notification_button)
}

private inline fun <reified T : Notification> assertNotificationOnDrawerVisible() {
    val missedCallNotification = SampleData.notifications().filterIsInstance<T>().first()
    with(UiAutomator) {
        with(missedCallNotification) {
            assertNotNull(
                "Cannot find notification with title '$title'",
                device.wait(Until.findObject(By.text(title)), TIMEOUT)
            )
            assertNotNull(
                "Cannot find notification with content '$content'",
                device.wait(Until.findObject(By.text(content)), TIMEOUT)
            )
        }
    }
}

val Sees<User>.notification get() = node { assertNotificationVisible<Notification.Call>() }
val Sees<User>.missedCallNotification get() = node { assertNotificationVisible<Notification.Call>() }
val Sees<User>.messageNotification get() = node { assertNotificationVisible<Notification.Message>() }
val Sees<User>.eventNotification get() = node { assertNotificationVisible<Notification.Event>() }
val Sees<User>.missedCallOnDrawer get() = node { assertNotificationOnDrawerVisible<Notification.Call>() }
val Sees<User>.newMessageOnDrawer get() = node { assertNotificationOnDrawerVisible<Notification.Message>() }
val Sees<User>.newEventOnDrawer get() = node { assertNotificationOnDrawerVisible<Notification.Event>() }

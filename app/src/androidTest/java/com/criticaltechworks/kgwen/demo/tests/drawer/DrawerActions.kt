/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.drawer

import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaDialogInteractions.clickDialogPositiveButton
import com.adevinta.android.barista.interaction.BaristaDrawerInteractions
import com.criticaltechworks.kgwen.annotation.KGwenObject
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.demo.verbs.Opens
import com.criticaltechworks.kgwen.verbs.*

@KGwenObject(name = "drawer", subject = User::class, verb = Opens::class)
fun Actions.openDrawer() {
    BaristaDrawerInteractions.openDrawer()
}

@KGwenObject(name = "home", subject = User::class, verb = Selects::class)
fun Actions.selectHome() {
    clickOn(R.id.homeFragment)
}

@KGwenObject(name = "messages", subject = User::class, verb = Selects::class)
fun Actions.selectMessages() {
    clickOn(R.id.messagesFragment)
}

@KGwenObject(name = "events", subject = User::class, verb = Selects::class)
fun Actions.selectEvents() {
    clickOn(R.id.eventsFragment)
}

@KGwenObject(name = "notifications", subject = User::class, verb = Selects::class)
fun Actions.selectNotifications() {
    clickOn(R.id.notificationsFragment)
}

@KGwenObject(name = "carpool", subject = User::class, verb = Selects::class)
fun Actions.selectCarpool() {
    clickOn(R.id.carpoolFragment)
}

@KGwenObject(name = "logout", subject = User::class, verb = Selects::class)
fun Actions.selectLogout() {
    clickOn(R.id.logout)
}

fun Actions.selectPositiveButton() {
    clickDialogPositiveButton()
}

@KGwenObject(name = "loggedOut", subject = User::class, verb = Has::class)
fun Actions.hasLogout() {
    with(Actions) {
        openDrawer()
        selectLogout()
        selectPositiveButton()
    }
}

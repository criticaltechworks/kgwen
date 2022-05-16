/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.drawer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.assertion.BaristaImageViewAssertions.assertHasDrawable
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.criticaltechworks.kgwen.annotation.KGwenObject
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.model.Profile
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.demo.utils.TestUtils.assertTextHighlighted
import com.criticaltechworks.kgwen.verbs.*

@KGwenObject(name = "profile", subject = User::class, verb = Sees::class)
fun Assertions.profileVisible() {
    assertDisplayed(R.id.user_profile)
}

@KGwenObject(name = "home", subject = User::class, verb = On::class)
fun Assertions.homeVisible() {
    assertDisplayed(R.id.homeFragment)
}

@KGwenObject(name = "messages", subject = User::class, verb = On::class)
fun Assertions.messagesVisible() {
    assertDisplayed(R.id.messagesFragment)
}

@KGwenObject(name = "events", subject = User::class, verb = On::class)
fun Assertions.eventsVisible() {
    assertDisplayed(R.id.eventsFragment)
}

fun Assertions.notificationsVisible() {
    assertDisplayed(R.id.notificationsFragment)
}

fun Assertions.carpoolVisible() {
    assertDisplayed(R.id.carpoolFragment)
}

@KGwenObject(name = "navButtons", subject = User::class, verb = Sees::class)
fun Assertions.navButtonsVisible() {
    homeVisible()
    messagesVisible()
    eventsVisible()
    notificationsVisible()
    carpoolVisible()
}

@KGwenObject(name = "accountButtons", subject = User::class, verb = Sees::class)
fun Assertions.accountButtonsVisible() {
    assertDisplayed(R.id.settings)
    assertDisplayed(R.id.logout)
}

@KGwenObject(name = "profilePicture", subject = User::class, verb = Sees::class)
fun Assertions.profilePictureVisible() {
    assertHasDrawable(R.id.profile_picture, Profile.user.value!!.picture)
}

@KGwenObject(name = "displayName", subject = User::class, verb = Sees::class)
fun Assertions.displayNameVisible() {
    assertDisplayed(R.id.display_name, Profile.user.value!!.displayName)
}

@KGwenObject(name = "email", subject = User::class, verb = Sees::class)
fun Assertions.emailVisible() {
    assertDisplayed(R.id.email, Profile.user.value!!.email)
}

@KGwenObject(name = "homeHighlighted", subject = User::class, verb = Sees::class)
fun Assertions.homeHighlighted() {
    assertTextHighlighted(R.string.home)
}

@KGwenObject(name = "messagesHighlighted", subject = User::class, verb = Sees::class)
fun Assertions.messagesHighlighted() {
    assertTextHighlighted(R.string.messages)
}

@KGwenObject(name = "eventsHighlighted", subject = User::class, verb = Sees::class)
fun Assertions.eventsHighlighted() {
    assertTextHighlighted(R.string.events)
}

@KGwenObject(name = "notificationsHighlighted", subject = User::class, verb = Sees::class)
@KGwenObject(name = "notifications", subject = User::class, verb = On::class)
fun Assertions.notificationsHighlighted() {
    assertTextHighlighted(R.string.notifications)
}

@KGwenObject(name = "carpoolHighlighted", subject = User::class, verb = Sees::class)
@KGwenObject(name = "carpool", subject = User::class, verb = On::class)
fun Assertions.carpoolHighlighted() {
    assertTextHighlighted(R.string.carpool)
}

@KGwenObject(name = "logoutDialog", subject = User::class, verb = Sees::class)
fun Assertions.logoutDialogVisible() {
    onView(withText(R.string.logout_message))
        .inRoot(isDialog())
        .check(matches(isDisplayed()))
}

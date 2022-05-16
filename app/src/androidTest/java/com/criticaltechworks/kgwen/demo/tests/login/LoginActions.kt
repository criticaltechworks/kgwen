/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.login

import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.typeTo
import com.criticaltechworks.kgwen.annotation.KGwenObject
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.model.*
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.demo.verbs.Types
import com.criticaltechworks.kgwen.verbs.*

@KGwenObject(name = "email", subject = User::class, verb = Types::class)
fun Actions.typeEmail() {
    typeTo(R.id.email_field, SampleData.Users.ScrumKnight.email)
}

@KGwenObject(name = "password", subject = User::class, verb = Types::class)
fun Actions.typePassword() {
    typeTo(R.id.password_field, Profile.MASTER_PASSWORD)
}

@KGwenObject(name = "login", subject = User::class, verb = Selects::class)
fun Actions.selectLogin() {
    clickOn(R.id.login_button)
}

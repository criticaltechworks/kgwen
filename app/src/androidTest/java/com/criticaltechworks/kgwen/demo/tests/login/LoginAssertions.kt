/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.login

import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.criticaltechworks.kgwen.annotation.KGwenObject
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.verbs.*

@KGwenObject(name = "loginDialog", subject = User::class, verb = Sees::class)
fun Assertions.loginDialogVisible() {
    assertDisplayed(R.id.login_activity)
}

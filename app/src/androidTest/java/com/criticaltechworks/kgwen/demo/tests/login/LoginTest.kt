/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("IllegalIdentifier")

package com.criticaltechworks.kgwen.demo.tests.login

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.criticaltechworks.kgwen.demo.core.UiTest
import com.criticaltechworks.kgwen.demo.subjects.user
import com.criticaltechworks.kgwen.demo.tests.drawer.*
import com.criticaltechworks.kgwen.demo.verbs.types
import com.criticaltechworks.kgwen.sections.Scenario.given
import com.criticaltechworks.kgwen.verbs.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest : UiTest() {
    @Test
    fun `When logged in home is shown`() {
        given {
            user has { loggedOut } sees { loginDialog }
        } whenever {
            user types { email and password } selects { login }
        } then {
            user on { home }
        }
    }
}

/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("IllegalIdentifier")

package com.criticaltechworks.kgwen.demo.tests.drawer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.criticaltechworks.kgwen.demo.core.UiTest
import com.criticaltechworks.kgwen.demo.subjects.*
import com.criticaltechworks.kgwen.demo.verbs.opens
import com.criticaltechworks.kgwen.sections.Scenario.given
import com.criticaltechworks.kgwen.utils.given
import com.criticaltechworks.kgwen.verbs.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationDrawerTest : UiTest() {
    @Test
    fun `Click on drawer toggle opens navigation drawer`() {
        given {
            user on { home }
        } whenever {
            user opens { drawer }
        } then {
            user sees { profile and navButtons and accountButtons }
        }
    }

    @Test
    fun `Navigation drawer profile shows user details`() {
        given {
            user on { home }
        } whenever {
            user opens { drawer }
        } then {
            user sees { profilePicture and displayName and email }
        }
    }

    @Test
    fun `Click on nav buttons does navigate`() {
        given(User) {
            on { home }
        } check {
            whenever { opens { drawer } } then { sees { homeHighlighted } }
            whenever { selects { messages } } then { on { messages } }
            whenever { opens { drawer } } then { sees { messagesHighlighted } }
            whenever { selects { events } } then { on { events } }
            whenever { opens { drawer } } then { sees { eventsHighlighted } }
            whenever { selects { notifications } } then { on { notifications } }
            whenever { opens { drawer } } then { sees { notificationsHighlighted } }
            whenever { selects { carpool } } then { on { carpool } }
            whenever { opens { drawer } } then { sees { carpoolHighlighted } }
            whenever { selects { home } } then { on { home } }
        }
    }

    @Test
    fun `Click on logout button shows dialog`() {
        given {
            user on { home }
        } whenever {
            user opens { drawer } selects { logout }
        } then {
            user sees { logoutDialog }
        }
    }
}

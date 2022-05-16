/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("IllegalIdentifier")

package com.criticaltechworks.kgwen.demo.tests.events

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.criticaltechworks.kgwen.demo.core.UiTest
import com.criticaltechworks.kgwen.demo.subjects.user
import com.criticaltechworks.kgwen.demo.tests.drawer.events
import com.criticaltechworks.kgwen.demo.tests.goToEvents
import com.criticaltechworks.kgwen.sections.Preconditions
import com.criticaltechworks.kgwen.sections.Scenario.given
import com.criticaltechworks.kgwen.verbs.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EventsTest : UiTest() {

    @Before
    fun setup() {
        Preconditions.goToEvents()
    }

    @Test
    fun `Grid layout is shown on glance tab`() {
        given {
            user on { events }
        } whenever {
            user selects { glanceTab }
        } then {
            user sees { gridLayout }
        }
    }

    @Test
    fun `List layout is shown on details tab`() {
        given {
            user on { events }
        } whenever {
            user selects { detailsTab }
        } then {
            user sees { listLayout }
        }
    }
}

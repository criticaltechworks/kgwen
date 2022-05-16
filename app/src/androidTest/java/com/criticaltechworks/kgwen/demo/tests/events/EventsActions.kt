/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.events

import com.adevinta.android.barista.interaction.BaristaClickInteractions
import com.criticaltechworks.kgwen.annotation.KGwenObject
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.verbs.*

@KGwenObject(name = "glanceTab", subject = User::class, verb = Selects::class)
fun Actions.selectGlanceTab() {
    BaristaClickInteractions.clickOn(R.string.compact_events_tab)
}

@KGwenObject(name = "detailsTab", subject = User::class, verb = Selects::class)
fun Actions.selectDetailsTab() {
    BaristaClickInteractions.clickOn(R.string.compact_events_tab)
}

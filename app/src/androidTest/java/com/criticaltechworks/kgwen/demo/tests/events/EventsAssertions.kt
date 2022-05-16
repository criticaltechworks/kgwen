/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.events

import androidx.recyclerview.widget.*
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.core.MainActivityScenarioTest
import com.criticaltechworks.kgwen.demo.presentation.fragments.events.CompactEventsFragment
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.verbs.*
import org.junit.Assert

//region Assertions
context(MainActivityScenarioTest)
fun Assertions.gridLayoutVisible() {
    scenarioRule.scenario.onActivity { activity ->
        val eventsGrid = activity.findViewById<RecyclerView>(R.id.compact_events_grid)
        val spanCount = (eventsGrid.layoutManager as? GridLayoutManager)?.spanCount
        Assert.assertTrue(
            "Expected ${CompactEventsFragment.SPAN_COUNT} columns but got $spanCount instead.",
            spanCount == CompactEventsFragment.SPAN_COUNT
        )
    }
}

context(MainActivityScenarioTest)
fun Assertions.listLayoutVisible() {
    onActivity { activity ->
        val eventsGrid = activity.findViewById<RecyclerView>(R.id.compact_events_grid)
        val orientation = (eventsGrid.layoutManager as? LinearLayoutManager)?.orientation
        Assert.assertTrue(
            "Did not get expected vertical linear layout",
            orientation == LinearLayoutManager.VERTICAL
        )
    }
}
//endregion

//region Verbs
context(MainActivityScenarioTest)
val Sees<User>.gridLayout
    get() = Assertions.gridLayoutVisible()

context(MainActivityScenarioTest)
val Sees<User>.listLayout
    get() = Assertions.listLayoutVisible()
//endregion

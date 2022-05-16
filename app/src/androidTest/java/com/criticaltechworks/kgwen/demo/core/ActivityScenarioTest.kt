/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.core

import android.app.Activity
import androidx.test.ext.junit.rules.ActivityScenarioRule

/**
 * A test that uses an [ActivityScenarioRule] for an [Activity] of type [A].
 */
interface ActivityScenarioTest<A : Activity> {
    // TODO: lint rule to check @get:Rule annotation?
    val scenarioRule: ActivityScenarioRule<A>

    fun onActivity(block: (A) -> Unit) {
        scenarioRule.scenario.onActivity(block)
    }
}

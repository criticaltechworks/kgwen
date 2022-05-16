/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.core

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adevinta.android.barista.internal.failurehandler.BaristaException
import com.criticaltechworks.kgwen.KGwen
import com.criticaltechworks.kgwen.demo.presentation.activities.MainActivity
import com.criticaltechworks.kgwen.demo.rules.DisableAnimationsRule
import org.junit.Rule

/**
 * An instrumented test for components of the KGwen demo app.
 */
abstract class UiTest : MainActivityScenarioTest {
    @get:Rule
    val animationsRule = DisableAnimationsRule()

    @get:Rule
    override val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    init {
        KGwen configuration {
            throwable { BaristaException::class }     // Thrown by Barista framework
        }
    }
}

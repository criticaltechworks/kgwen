/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.rules

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.criticaltechworks.kgwen.demo.ktx.toInt
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.IOException

/**
 * A rule to disable animations during testing.
 */
class DisableAnimationsRule : TestRule {
    override fun apply(base: Statement, description: Description) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            changeAnimationStatus(enabled = false)
            try {
                base.evaluate()
            } finally {
                changeAnimationStatus(enabled = true)
            }
        }
    }

    @Throws(IOException::class)
    private fun changeAnimationStatus(enabled: Boolean = true) {
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand(command(Setting.TRANSITION_ANIMATION_SCALE, enabled))
            executeShellCommand(command(Setting.WINDOW_ANIMATION_SCALE, enabled))
            executeShellCommand(command(Setting.ANIMATOR_DURATION, enabled))
        }
    }

    private fun command(setting: String, enabled: Boolean) = "$setting, ${enabled.toInt()}"

    private object Setting {
        const val TRANSITION_ANIMATION_SCALE = "settings put global transition_animation_scale"
        const val WINDOW_ANIMATION_SCALE = "settings put global window_animation_scale"
        const val ANIMATOR_DURATION = "settings put global animator_duration_scale"
    }
}

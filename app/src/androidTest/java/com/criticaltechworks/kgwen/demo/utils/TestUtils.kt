/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.utils

import android.content.Context
import android.widget.TextView
import androidx.annotation.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.adevinta.android.barista.internal.assertAny
import com.adevinta.android.barista.internal.matcher.TextColorMatcher
import org.hamcrest.Matchers.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object TestUtils {
    private val context: Context = ApplicationProvider.getApplicationContext()

    /** Assert string with identifier [resId] is displayed in highlight color. */
    fun assertTextHighlighted(@StringRes resId: Int) {
        withText(resId).assertAny(TextColorMatcher(android.R.attr.colorPrimary))
    }

    /** Assert parent with identifier [parentId] has a child that displayed text with id [resId]. */
    fun assertChildDisplayed(@StringRes resId: Int, @IdRes parentId: Int) {
        onTextViewWithParent(parentId).check(
            matches(
                allOf(withText(resId), isDisplayed())
            )
        )
    }

    /** Assert parent with identifier [parentId] has a child that displayed the provided [text]. */
    fun assertChildDisplayed(text: String, @IdRes parentId: Int) {
        onTextViewWithParent(parentId).check(
            matches(
                allOf(withText(text), isDisplayed())
            )
        )
    }

    /**
     * Load string resource from test application context.
     * @param resId the string resource identifier
     * @param formatArgs the format arguments
     */
    fun stringFrom(@StringRes resId: Int, vararg formatArgs: String) =
        context.getString(resId, *formatArgs)

    /** Sleeps the provided [sleep] amount, after the flaky action in the [block] is run. */
    fun flakyAction(sleep: Duration, block: () -> Unit) {
        block()
        sleep(sleep.inWholeMilliseconds)
    }

    private fun onTextViewWithParent(@IdRes parentId: Int) =
        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(parentId))
            )
        )

    object Delay {
        val MARK_AS = 500.milliseconds
        val DELETE_NOTIFICATION = 750.milliseconds
        val CLEAR_NOTIFICATION_DRAWER = 1000.milliseconds
    }
}

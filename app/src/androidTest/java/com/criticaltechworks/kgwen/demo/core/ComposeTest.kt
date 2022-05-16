/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.core

import androidx.compose.ui.test.junit4.ComposeTestRule

/**
 * A test that uses a [ComposeTestRule].
 */
interface ComposeTest {
    // TODO: lint rule to check @get:Rule annotation?
    val composeTestRule: ComposeTestRule
}

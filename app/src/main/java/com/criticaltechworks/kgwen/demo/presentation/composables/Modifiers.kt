/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.ui.*
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*

/**
 * Convenience modifier that sets a string resource as this Composable's content description
 * @param id the resource identifier
 * @param formatArgs the format arguments
 */
fun Modifier.withDescription(
    @StringRes id: Int,
    vararg formatArgs: String
) = composed(inspectorInfo = debugInspectorInfo {
    name = "withDescription"
    value = id
}) {
    val description = stringResource(id = id, formatArgs = formatArgs)
    semantics { contentDescription = description }
}

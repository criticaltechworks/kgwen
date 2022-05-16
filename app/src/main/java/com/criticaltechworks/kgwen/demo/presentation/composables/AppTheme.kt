/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

/** Taken from values/colors.xml */
object AppColors {
    val Primary = Color(0xff4dd0e1)
    val PrimaryLight = Color(0xff88ffff)
    val PrimaryDark = Color(0xff009faf)
    val Secondary = Color(0xffbbdefb)
    val SecondaryLight = Color(0xffeeffff)
    val SecondaryDark = Color(0xff8aacc8)
    val PrimaryText = Color(0xFF0F154D)
    val SecondaryText by lazy { Color(0xff1a237e) }
}

private val LightColors = with(AppColors) {
    lightColors(
        primary = Primary,
        primaryVariant = PrimaryDark,
        onPrimary = PrimaryText,
        secondary = Secondary,
        secondaryVariant = SecondaryDark,
        onSecondary = SecondaryText,
    )
}

private val DarkColors = with(AppColors) {
    darkColors(
        primary = PrimaryDark,
        primaryVariant = PrimaryLight,
        onPrimary = PrimaryText,
        secondary = SecondaryDark,
        secondaryVariant = SecondaryLight,
        onSecondary = SecondaryText
    )
}

/** Applies the [MaterialTheme]-customized for this app. */
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = if (isSystemInDarkTheme()) DarkColors else LightColors) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onSurface) {
            content()
        }
    }
}

/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import android.app.AlertDialog
import android.widget.Button

/** This [AlertDialog]'s positive button. */
val AlertDialog.positiveButton: Button get() = getButton(AlertDialog.BUTTON_POSITIVE)

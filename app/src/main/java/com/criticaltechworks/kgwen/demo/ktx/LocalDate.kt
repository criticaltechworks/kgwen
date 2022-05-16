/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/** The first three initials of the month. */
val LocalDate.monthInitials: String get() = format(DateTimeFormatter.ofPattern("MMM"))

/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.model

import java.time.LocalDate

/** A calendar event */
data class Event(
    val title: String,
    val description: String,
    val date: LocalDate,
    val tags: List<String>
)

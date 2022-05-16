/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.viewmodels

import androidx.lifecycle.*
import com.criticaltechworks.kgwen.demo.ktx.asLiveData
import com.criticaltechworks.kgwen.demo.model.*

class EventsViewModel: ViewModel() {
    private val _events = MutableLiveData(mutableListOf<Event>())
    val events = _events.asLiveData()

    // Demo only
    init {
        _events.postValue(SampleData.EVENTS.toMutableList())
    }

    fun add(event: Event) {
        _events.value = _events.value?.apply {
            add(event)
            sortBy { it.date }
        }
    }
}

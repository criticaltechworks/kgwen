/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import androidx.lifecycle.*

/** Convenience function for LiveData casting without the need for explicit type annotation. */
fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

/** Applies [Transformations.distinctUntilChanged] to this [LiveData]. */
fun<T> LiveData<T>.distinctUntilChanged() = Transformations.distinctUntilChanged(this)

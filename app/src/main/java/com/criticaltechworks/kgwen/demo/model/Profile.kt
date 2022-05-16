/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.model

import androidx.lifecycle.*
import com.criticaltechworks.kgwen.demo.ktx.asLiveData

/** The currently logged-in [User]. */
object Profile {
    private val _user = MutableLiveData<User?>(SampleData.Users.ScrumKnight)
    val user = _user.asLiveData()

    /**
     * Simulated logging with [email] and [password] credentials.
     * @return true if login succeeded, false otherwise.
     */
    fun login(email: String, password: String): Boolean {
        val newUser = SampleData.Users.All.firstOrNull { user ->
            user.email == email
        }?.takeIf { password == MASTER_PASSWORD }

        _user.postValue(newUser)
        return newUser != null
    }

    fun logout() {
        _user.postValue(null)
    }

    const val MASTER_PASSWORD = "very_safe123"
}

/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.criticaltechworks.kgwen.demo.model.*

class MessagesViewModel : ViewModel() {
    var conversation: Conversation? by mutableStateOf(null)

    fun send(message: String) {
        conversation
            ?.apply {
                messages.add(
                    Conversation.Message(sender = Profile.user.value!!, content = message)
                )
            }
            ?.also {
                conversation = null
                conversation = it
            }
    }
}

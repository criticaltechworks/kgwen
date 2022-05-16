/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests.messages

import androidx.compose.ui.test.*
import com.criticaltechworks.kgwen.annotation.KGwenObject
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.core.ComposeTest
import com.criticaltechworks.kgwen.demo.model.SampleData
import com.criticaltechworks.kgwen.demo.subjects.User
import com.criticaltechworks.kgwen.demo.utils.TestUtils.stringFrom
import com.criticaltechworks.kgwen.demo.verbs.*
import com.criticaltechworks.kgwen.verbs.*

context(ComposeTest)
@KGwenObject(name = "conversation", subject = User::class, verb = Selects::class)
fun Actions.selectFirstConversation() {
    composeTestRule
        .onNodeWithText(SampleData.CONVERSATIONS.first().user.displayName)
        .performClick()
}

context(ComposeTest)
@KGwenObject(name = "newMessageField", subject = User::class, verb = Selects::class)
fun Actions.selectNewMessageField() {
    composeTestRule
        .onNodeWithText(stringFrom(R.string.new_message))
        .performClick()
}

context(ComposeTest)
@KGwenObject(name = "newMessage", subject = User::class, verb = Types::class)
fun Actions.typeNewMessage() {
    composeTestRule
        .onNodeWithText(stringFrom(R.string.new_message))
        .performTextInput(stringFrom(R.string.sample_message))
}

context(ComposeTest)
@KGwenObject(name = "newMessage", subject = User::class, verb = Sends::class)
fun Actions.sendNewMessage() {
    composeTestRule
        .onNodeWithContentDescription(stringFrom(R.string.send_message))
        .performClick()
}

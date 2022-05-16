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
import com.criticaltechworks.kgwen.demo.utils.TestUtils
import com.criticaltechworks.kgwen.demo.utils.TestUtils.stringFrom
import com.criticaltechworks.kgwen.verbs.*

@KGwenObject(name = "messagesOnToolbar", subject = User::class, verb = Sees::class)
fun Assertions.messagesOnToolbarVisible() {
    TestUtils.assertChildDisplayed(R.string.messages, parentId = R.id.toolbar)
}

context(ComposeTest)
@KGwenObject(name = "conversations", subject = User::class, verb = Sees::class)
fun Assertions.conversationsVisible() {
    with(composeTestRule) {
        onRoot().printToLog("conversationsVisible")
        SampleData.CONVERSATIONS.forEach { conversation ->
            val conversationDescription =
                stringFrom(R.string.conversation_description, conversation.user.displayName)
            onNodeWithContentDescription(conversationDescription)
                .performScrollTo()
                .assertTextEquals(
                    conversation.user.displayName,
                    conversation.messages.last().content
                )
                .assertIsDisplayed()
        }
    }
}

@KGwenObject(name = "conversationTitle", subject = User::class, Sees::class)
fun Assertions.conversationTitleVisible() {
    TestUtils.assertChildDisplayed(
        SampleData.CONVERSATIONS.first().user.displayName,
        parentId = R.id.toolbar
    )
}

context(ComposeTest)
@KGwenObject(name = "conversationBubbles", subject = User::class, verb = Sees::class)
fun Assertions.conversationContentVisible() {
    with(composeTestRule) {
        onRoot().printToLog("conversationContentVisible")
        SampleData.CONVERSATIONS.first().messages.forEach { message ->
            onNodeWithText(message.content)
                .performScrollTo()
                .assertIsDisplayed()
        }
    }

}

context(ComposeTest)
@KGwenObject(name = "newMessageField", subject = User::class, verb = Sees::class)
fun Assertions.newMessageFieldVisible() {
    with(composeTestRule) {
        onRoot().printToLog("messageFieldVisible")
        onNodeWithContentDescription(stringFrom(R.string.message_attachement)).assertIsDisplayed()
        onNodeWithText(stringFrom(R.string.new_message)).assertIsDisplayed()
        onNodeWithContentDescription(stringFrom(R.string.send_message)).assertIsDisplayed()
    }
}

context(ComposeTest)
@KGwenObject(name = "firstConversation", subject = User::class, verb = On::class)
fun Assertions.firstConversationVisible() {
    conversationTitleVisible()
    conversationContentVisible()
    newMessageFieldVisible()
}

context(ComposeTest)
@KGwenObject(name = "messageFieldFocused", subject = User::class, verb = Sees::class)
fun Assertions.messageFieldFocused() {
    with(composeTestRule) {
        onRoot().printToLog("messageFieldFocused")
        onNodeWithText(stringFrom(R.string.new_message))
            .assertIsFocused()
    }
}

context(ComposeTest)
@KGwenObject(name = "newMessage", subject = User::class, verb = Sees::class)
fun Assertions.newMessageVisible() {
    with(composeTestRule) {
        onNodeWithText(stringFrom(R.string.sample_message))
            .performScrollTo()
            .assertIsDisplayed()
    }
}

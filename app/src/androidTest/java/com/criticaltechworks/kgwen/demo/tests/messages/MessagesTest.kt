/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("IllegalIdentifier")

package com.criticaltechworks.kgwen.demo.tests.messages

import androidx.compose.ui.test.junit4.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.criticaltechworks.kgwen.demo.core.*
import com.criticaltechworks.kgwen.demo.subjects.*
import com.criticaltechworks.kgwen.demo.tests.*
import com.criticaltechworks.kgwen.demo.tests.drawer.messages
import com.criticaltechworks.kgwen.demo.verbs.*
import com.criticaltechworks.kgwen.sections.*
import com.criticaltechworks.kgwen.sections.Expectation.then
import com.criticaltechworks.kgwen.sections.Scenario.given
import com.criticaltechworks.kgwen.utils.given
import com.criticaltechworks.kgwen.verbs.*
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MessagesTest : UiTest(), ComposeTest {
    @get:Rule
    override val composeTestRule: ComposeTestRule = createEmptyComposeRule()

    @Before
    fun setup() {
        Preconditions.goToMessages()
    }

    @Test
    fun `Messages shows all conversations`() {
        given {
            user on { messages }
        } immediately then {
            user sees { messagesOnToolbar and conversations }
        }
    }

    @Test
    fun `Conversation displays content and new message field`() {
        given {
            user on { messages }
        } whenever {
            user selects { conversation }
        } then {
            user sees { conversationTitle and conversationBubbles and newMessageField }
        }
    }

    @Test
    fun `When message is sent conversation is updated`() {
        given(User) {
            precondition { goToFirstConversation() }
            on { firstConversation }
        } check {
            whenever { selects { newMessageField } } then { sees { messageFieldFocused } }
            whenever { types { newMessage } sends { newMessage } } then { sees { newMessage } }
        }
    }
}
